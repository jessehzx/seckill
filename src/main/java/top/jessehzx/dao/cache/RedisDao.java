package top.jessehzx.dao.cache;

import top.jessehzx.entity.Seckill;

/**
 * @author jessehzx
 * @date 2018/5/15
 */
public class RedisDao {

    private final JedisPool jedisPool;

    public RedisDao(String ip, int port) {
        jedisPool = new JedisPool(ip, port);
    }

    public Seckill getSeckill(long seckillId) {

        return null;
    }

    public Seckill putSeckill(Seckill seckill) {

        return null;
    }

}
