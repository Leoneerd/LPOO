package lpoo.exception;

public class ErrorException extends Exception {
    public ErrorException(String msg) {
        super(msg);
    }

    public ErrorException(String msg, Throwable cause) {
        super(msg, cause);
    }
}