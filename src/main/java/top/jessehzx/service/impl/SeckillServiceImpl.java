package top.jessehzx.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import top.jessehzx.dao.SeckillDao;
import top.jessehzx.dao.SuccessKilledDao;
import top.jessehzx.dao.cache.RedisDao;
import top.jessehzx.dto.Exposer;
import top.jessehzx.dto.SeckillExcution;
import top.jessehzx.entity.Seckill;
import top.jessehzx.entity.SuccessKilled;
import top.jessehzx.enums.SeckillStatEnum;
import top.jessehzx.exception.RepeatKillException;
import top.jessehzx.exception.SeckillCloseException;
import top.jessehzx.exception.SeckillException;
import top.jessehzx.service.SeckillService;

import java.util.Date;
import java.util.List;

/**
 * @author jessehzx
 * @date 2018/5/13
 */
@Service
public class SeckillServiceImpl implements SeckillService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeckillServiceImpl.class);

    private static final String slat = "jgoeijgrj@Q$%%U(JGoewh9t";

    @Autowired
    private SeckillDao seckillDao;

    @Autowired
    private SuccessKilledDao successKilledDao;

    @Autowired
    private RedisDao redisDao;

    public List<Seckill> getSeckillList() {
        return seckillDao.queryAll(0, 4);
    }

    public Seckill getById(long seckillId) {
        return seckillDao.queryById(seckillId);
    }

    public Exposer exportSeckillUrl(long seckillId) {
        // 优化点：缓存优化：超时的基础上维护一致性
        // 1:先去访问redis
        Seckill seckill = redisDao.getSeckill(seckillId);
        if (null == seckill) {
            // 2:访问数据库
            seckill = seckillDao.queryById(seckillId);
            if (null == seckill) {
                return new Exposer(false, seckillId);
            } else {
                // 3:放进redis
                redisDao.putSeckill(seckill);
            }
        }

        Date startTime = seckill.getStartTime();
        Date endTime = seckill.getEndTime();
        Date nowTime = new Date();
        // 还未到秒杀开启时间，或秒杀已结束
        if (nowTime.getTime() < startTime.getTime()
                || nowTime.getTime() > endTime.getTime()) {
            return new Exposer(false, seckillId, nowTime.getTime(), startTime.getTime(), endTime.getTime());
        }
        String md5 = getMD5(seckillId);
        return new Exposer(true, md5, seckillId);
    }

    private String getMD5(long seckillId) {
        String base = seckillId + "/" + slat;
        String md5 = DigestUtils.md5DigestAsHex(base.getBytes());
        return md5;
    }

    public SeckillExcution excuteSeckill(long seckillId, long userPhone, String md5) throws SeckillException, RepeatKillException, SeckillCloseException {

        if (null == md5 || md5.equals(getMD5(seckillId))) {
            throw new SeckillException("seckill data rewrite");
        }

        // 执行秒杀逻辑：减库存+记录购买行为
        Date nowTime = new Date();

        /**
         * 简单优化：
         * 把顺序调整之后，可以把网络延迟和GC对性能的干扰减少一半。
         * 因为干扰主要出现在：减库存（热点商品竞争），等待行级锁释放+干扰，对性能的影响。
         * 记录购买行为，因为使用了联合主键（手机号+商品ID）和insert ignore into ...会帮我们挡住一大部分的重复秒杀。
         */
        try {
            // 记录购买行为
            int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
            if (insertCount <= 0) {
                // 重复秒杀
                throw new RepeatKillException("repeat seckill");
            } else {
                // 减库存
                int updateCount = seckillDao.reduceNumber(seckillId, nowTime);
                if (updateCount <= 0) {
                    // 减库存失败，说明秒杀已经结束或没有库存了。rollback
                    throw new SeckillCloseException("seckill is end");
                } else {
                    // 秒杀成功。commit
                    SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
                    if (null != sk) {
                        return new SeckillExcution(seckillId, SeckillStatEnum.SUCCESS, sk);
                    }
                }
            }

        } catch (SeckillCloseException e1) {
            throw e1;
        } catch (RepeatKillException e2) {
            throw e2;
        } catch (SeckillException e) {
            LOGGER.error(e.getMessage(), e);
            // 所有编译器异常 转化为运行期异常
            throw new SeckillException("seckill inner error." + e.getMessage());
        }

        return null;
    }

    public SeckillExcution excuteSeckillProcedure(long seckillId, long userPhone, String md5) {
        if (null == md5 && !md5.equals(getMD5(seckillId))) {
            return new SeckillExcution(seckillId, SeckillStatEnum.DATA_REWRITE);
        }
        Date killTime = new Date();

        return null;
    }
}
