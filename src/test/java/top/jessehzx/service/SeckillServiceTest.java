package top.jessehzx.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.jessehzx.dto.Exposer;
import top.jessehzx.dto.SeckillExcution;
import top.jessehzx.entity.Seckill;
import top.jessehzx.exception.RepeatKillException;
import top.jessehzx.exception.SeckillCloseException;
import top.jessehzx.exception.SeckillException;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author jessehzx
 * @date 2018/5/14
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration({"classpath:spring/spring-dao.xml",
                    "classpath:spring/spring-service.xml"})
public class SeckillServiceTest {

    private static final Logger LOGGER = LoggerFactory.getLogger(SeckillServiceTest.class);

    @Autowired
    private SeckillService seckillService;

    @Test
    public void getSeckillList() {
        List<Seckill> seckillList = seckillService.getSeckillList();
        LOGGER.info("list={}", seckillList);
    }

    @Test
    public void getById() {
        long seckillId = 1000L;
        Seckill seckill = seckillService.getById(seckillId);
        LOGGER.info("seckill={}", seckill);
    }

    /**
     * 测试代码完整逻辑，注意可重复执行
     */
    @Test
    public void testSeckillLogic() {
        long seckillId = 1001L;
        Exposer exposer =seckillService.exportSeckillUrl(seckillId);
        LOGGER.info("exposer={}", exposer);
        if (exposer.isExposed()) {
            // 秒杀
            long userPhone = 13512340001L;
            String md5 = exposer.getMd5();
            try {
                SeckillExcution seckillExcution = seckillService.excuteSeckill(seckillId, userPhone, md5);
                LOGGER.info("seckillExcution={}" + seckillExcution);
            } catch (RepeatKillException e1) {
                LOGGER.error(e1.getMessage());
            } catch (SeckillCloseException e2) {
                LOGGER.error(e2.getMessage());
            }
        } else {
            // 秒杀未开启或重复秒杀
            LOGGER.warn("exposer={}", exposer);
        }
    }

    @Test
    public void testSeckillByProcedure() {
        long seckillId = 1001L;
        long phone = 13569991111L;
        Exposer exposer = seckillService.exportSeckillUrl(seckillId);
        if (exposer.isExposed()) {
            String md5 = exposer.getMd5();
            SeckillExcution seckillExcution = seckillService.excuteSeckillProcedure(seckillId, phone, md5);
            LOGGER.info(seckillExcution.getStateInfo());
        }
    }

}