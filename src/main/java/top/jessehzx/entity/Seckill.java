package top.jessehzx.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author jessehzx
 * @date 2018/5/3
 */
public class Seckill implements Serializable {
    private int seckillId;  // 库存商品id
    private String name;    // 商品名称
    private int number;     // 库存数量
    private Date startTime; // 开始时间
    private Date endTime;   // 结束时间
    private Date createTime;// 创建时间

    public int getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(int seckillId) {
        this.seckillId = seckillId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Seckill seckill = (Seckill) o;

        if (seckillId != seckill.seckillId) return false;
        if (number != seckill.number) return false;
        if (name != null ? !name.equals(seckill.name) : seckill.name != null) return false;
        if (startTime != null ? !startTime.equals(seckill.startTime) : seckill.startTime != null) return false;
        if (endTime != null ? !endTime.equals(seckill.endTime) : seckill.endTime != null) return false;
        return createTime != null ? createTime.equals(seckill.createTime) : seckill.createTime == null;
    }

    @Override
    public int hashCode() {
        int result = seckillId;
        result = 31 * result + (name != null ? name.hashCode() : 0);
        result = 31 * result + number;
        result = 31 * result + (startTime != null ? startTime.hashCode() : 0);
        result = 31 * result + (endTime != null ? endTime.hashCode() : 0);
        result = 31 * result + (createTime != null ? createTime.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Seckill{" +
                "seckillId=" + seckillId +
                ", name='" + name + '\'' +
                ", number=" + number +
                ", startTime=" + startTime +
                ", endTime=" + endTime +
                ", createTime=" + createTime +
                '}';
    }
}
