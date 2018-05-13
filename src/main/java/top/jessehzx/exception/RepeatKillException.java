package top.jessehzx.exception;

/**
 * 重复秒杀异常（运行时异常）
 * 可以是用户无意而为之，也可能是恶意而为之，但重复秒杀都应该提示抛异常了
 * @author jessehzx
 * @date 2018/5/13
 */
public class RepeatKillException extends SeckillException {

    public RepeatKillException(String message) {
        super(message);
    }

    public RepeatKillException(String message, Throwable cause) {
        super(message, cause);
    }
}
