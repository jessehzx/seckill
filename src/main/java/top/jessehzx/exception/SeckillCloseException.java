package top.jessehzx.exception;

/**
 * 秒杀关闭异常
 * 比如秒杀时间到了，库存没了，那用户就不该继续执行秒杀操作了
 * @author jessehzx
 * @date 2018/5/13
 */
public class SeckillCloseException extends SeckillException{

    public SeckillCloseException(String message) {
        super(message);
    }

    public SeckillCloseException(String message, Throwable cause) {
        super(message, cause);
    }
}
