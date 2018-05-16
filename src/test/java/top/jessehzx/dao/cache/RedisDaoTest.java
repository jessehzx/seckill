package top.jessehzx.dao.cache;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.jessehzx.dao.SeckillDao;
import top.jessehzx.entity.Seckill;

import static org.junit.Assert.*;

/**
 * @author jessehzx
 * @date 2018/5/16
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class RedisDaoTest {

    private long id = 1001L;

    @Autowired
    private RedisDao redisDao;

    @Autowired
    private SeckillDao seckillDao;

    @Test
    public void testSeckill() {
        // put and get
        Seckill seckill = redisDao.getSeckill(id);
        if (null == seckill) {
            seckill = seckillDao.queryById(id);
            if (null != seckill) {
                String result = redisDao.putSeckill(seckill);
                System.out.println(result);
                seckill = redisDao.getSeckill(id);
                System.out.println(seckill);
            }
        }
    }

}