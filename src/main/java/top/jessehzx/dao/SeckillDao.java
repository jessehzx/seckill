package top.jessehzx.dao;

import org.apache.ibatis.annotations.Param;
import top.jessehzx.entity.Seckill;

import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * @author jessehzx
 * @date 2018/5/4
 */
public interface SeckillDao {

    /**
     * 减库存
     * @param seckillId
     * @param killTime  当前秒杀的时间
     * @return 返回值>0，表示更新的行数
     */
    int reduceNumber(@Param("seckillId") long seckillId, @Param("killTime") Date killTime);

    /**
     * 根据Id查询秒杀对象
     * @param seckillId
     * @return
     */
    Seckill queryById(long seckillId);

    /**
     * 根据偏移量查询秒杀商品列表
     * @param offset
     * @param limit
     * @return
     */
    List<Seckill> queryAll(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 使用存储过程执行秒杀
     * @param paramMap
     */
    void killByProcedure(Map<String, Object> paramMap);

}
