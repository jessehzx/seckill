package top.jessehzx.service;

import top.jessehzx.dto.Exposer;
import top.jessehzx.dto.SeckillExcution;
import top.jessehzx.entity.Seckill;
import top.jessehzx.exception.RepeatKillException;
import top.jessehzx.exception.SeckillCloseException;
import top.jessehzx.exception.SeckillException;

import java.util.List;

/**
 * 业务接口：站在“使用者”的角度设计接口
 * 三个方面：方法定义粒度，参数，返回类型（return 类型/异常）
 * @author jessehzx
 * @date 2018/5/13
 */
public interface SeckillService {

    /**
     * 查询所有秒杀记录
     * @return
     */
    List<Seckill> getSeckillList();

    /**
     * 查询单个秒杀对象
     * @param seckillId
     * @return
     */
    Seckill getById(long seckillId);

    /**
     * 秒杀开启时输出秒杀接口地址，否则输出秒杀开启时间和系统当前时间
     * 作用：当还没开启时，不开放接口地址，而不是让用户根据规则拼出来我们的秒杀url
     * @param seckillId
     */
    Exposer exportSeckillUrl(long seckillId);

    /**
     * 执行秒杀
     * 通过exportSeckillUrl先暴露md5，这里带上md5值，和我们业务逻辑中计算的不一致，表示用户秒杀url被篡改了，我们会拒绝执行秒杀
     * @param seckill
     * @param userPhone
     * @param md5
     */
    SeckillExcution excuteSeckill(long seckill, long userPhone, String md5)
        throws SeckillException,RepeatKillException,SeckillCloseException;
}
