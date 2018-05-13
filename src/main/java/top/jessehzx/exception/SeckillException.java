package top.jessehzx.exception;

/**
 * 秒杀业务相关异常
 * @author jessehzx
 * @date 2018/5/13
 */
public class SeckillException extends RuntimeException {

    public SeckillException(String message) {
        super(message);
    }

    public SeckillException(String message, Throwable cause) {
        super(message, cause);
    }
}
