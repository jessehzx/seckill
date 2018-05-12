package top.jessehzx.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jessehzx
 * @date 2018/5/4
 */
public class SuccessKilled implements Serializable {

    private long seckillId; // 秒杀商品id
    private long userPhone; // 用户手机号
    private short state;    // 状态标示：-1：无效 0：成功 1：已付款 2：已发货
    private Date createTime;// 创建时间
    private Seckill seckill;// 秒杀商品库存实体。多对一的复合属性

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public long getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(long userPhone) {
        this.userPhone = userPhone;
    }

    public short getState() {
        return state;
    }

    public void setState(short state) {
        this.state = state;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Seckill getSeckill() {
        return seckill;
    }

    public void setSeckill(Seckill seckill) {
        this.seckill = seckill;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SuccessKilled that = (SuccessKilled) o;

        if (seckillId != that.seckillId) return false;
        if (userPhone != that.userPhone) return false;
        if (state != that.state) return false;
        if (createTime != null ? !createTime.equals(that.createTime) : that.createTime != null) return false;
        return seckill != null ? seckill.equals(that.seckill) : that.seckill == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (seckillId ^ (seckillId >>> 32));
        result = 31 * result + (int) (userPhone ^ (userPhone >>> 32));
        result = 31 * result + (int) state;
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        result = 31 * result + (seckill != null ? seckill.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SuccessKilled{" +
                "seckillId=" + seckillId +
                ", userPhone=" + userPhone +
                ", state=" + state +
                ", createTime=" + createTime +
                ", seckill=" + seckill +
                '}';
    }
}
