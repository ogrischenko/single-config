package config;

/**
 * Created by Oleg on 20.11.2016.
 */
public class InvalidOperationException extends RuntimeException {
    public InvalidOperationException(String msg) {
        super(msg);
    }
}
