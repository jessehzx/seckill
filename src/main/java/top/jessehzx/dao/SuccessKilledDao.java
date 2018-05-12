package top.jessehzx.dao;

import org.apache.ibatis.annotations.Param;
import top.jessehzx.entity.SuccessKilled;

/**
 * @author jessehzx
 * @date 2018/5/4
 */
public interface SuccessKilledDao {

    /**
     * 插入秒杀成功明细表，可过滤重复
     * @param seckillId
     * @param userPhone
     * @return
     */
    int insertSuccessKilled(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

    /**
     * 根据Id查询秒杀成功记录，并携带秒杀对象实体
     * @param seckillId
     * @return
     */
    SuccessKilled queryByIdWithSeckill(@Param("seckillId") long seckillId, @Param("userPhone") long userPhone);

}
