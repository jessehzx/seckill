package top.jessehzx.dao;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import top.jessehzx.entity.Seckill;

import javax.annotation.Resource;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author jessehzx
 * @date 2018/5/12
 */
/* junit与Spring整合 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration("classpath:spring/spring-dao.xml")
public class SeckillDaoTest {

    @Resource
    private SeckillDao seckillDao;

    @Test
    public void queryById() {
        long seckillId = 1000L;
        Seckill seckill = seckillDao.queryById(seckillId);
        System.out.println(seckill);
    }

    @Test
    public void queryAll() {
        // org.apache.ibatis.binding.BindingException: Parameter 'offset' not found. Available parameters are [0, 1, param1, param2]
        // Java没有保存形参的记录。queryAll(int offset, int limit) ==》queryAll(arg0, arg1)
        // 所以需要在Dao接口相应方法中使用@Param("offset")的形式指明
        List<Seckill> seckillList = seckillDao.queryAll(0, 100);
        for (Seckill seckill : seckillList) {
            System.out.println(seckill);
        }
    }

    @Test
    public void reduceNumber() {
        long seckillId = 1000L;
        Date killTime = new Date();
        int updateCount = seckillDao.reduceNumber(seckillId, killTime);
        System.out.println("updateCount=" + updateCount);
    }
}