package top.jessehzx.dto;

import top.jessehzx.entity.SuccessKilled;

/**
 * 封装秒杀执行结果
 * @author jessehzx
 * @date 2018/5/13
 */
public class SeckillExcution {

    private long seckillId;

    private int state;

    private String stateInfo;

    private SuccessKilled successKilled;

    public SeckillExcution(long seckillId, int state, String stateInfo, SuccessKilled successKilled) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
        this.successKilled = successKilled;
    }

    /**
     * 构造方法。失败的时候，就没有秒杀成功Entity了
     * @param seckillId
     * @param state
     * @param stateInfo
     */
    public SeckillExcution(long seckillId, int state, String stateInfo) {
        this.seckillId = seckillId;
        this.state = state;
        this.stateInfo = stateInfo;
    }

    public long getSeckillId() {
        return seckillId;
    }

    public void setSeckillId(long seckillId) {
        this.seckillId = seckillId;
    }

    public int getState() {
        return state;
    }

    public void setState(int state) {
        this.state = state;
    }

    public String getStateInfo() {
        return stateInfo;
    }

    public void setStateInfo(String stateInfo) {
        this.stateInfo = stateInfo;
    }

    public SuccessKilled getSuccessKilled() {
        return successKilled;
    }

    public void setSuccessKilled(SuccessKilled successKilled) {
        this.successKilled = successKilled;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        SeckillExcution that = (SeckillExcution) o;

        if (seckillId != that.seckillId) return false;
        if (state != that.state) return false;
        if (stateInfo != null ? !stateInfo.equals(that.stateInfo) : that.stateInfo != null) return false;
        return successKilled != null ? successKilled.equals(that.successKilled) : that.successKilled == null;
    }

    @Override
    public int hashCode() {
        int result = (int) (seckillId ^ (seckillId >>> 32));
        result = 31 * result + state;
        result = 31 * result + (stateInfo != null ? stateInfo.hashCode() : 0);
        result = 31 * result + (successKilled != null ? successKilled.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "SeckillExcution{" +
                "seckillId=" + seckillId +
                ", state=" + state +
                ", stateInfo='" + stateInfo + '\'' +
                ", successKilled=" + successKilled +
                '}';
    }
}
