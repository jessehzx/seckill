package top.jessehzx.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.jessehzx.entity.SuccessKilled;

import javax.annotation.Resource;

import static org.junit.Assert.*;

/**
 * @author jessehzx
 * @date 2018/5/12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SuccessKilledDaoTest {

    @Resource
    private SuccessKilledDao successKilledDao;

    @Test
    public void insertSuccessKilled() {
        /*
        第一次：insertCount=1
        第二次：insertCount=0
        因为使用了联合主键idx(seckill_id,user_phone)，而且sql语句使用了insert ignore into
         */
        long seckillId = 1000L;
        long userPhone = 13412340000L;
        int insertCount = successKilledDao.insertSuccessKilled(seckillId, userPhone);
        System.out.println("insertCount=" + insertCount);
    }

    @Test
    public void queryByIdWithSeckill() {
        long seckillId = 1000L;
        long userPhone = 13412340000L;
        SuccessKilled sk = successKilledDao.queryByIdWithSeckill(seckillId, userPhone);
        System.out.println(sk);
        System.out.println(sk.getSeckill());
    }
}