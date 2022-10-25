package api;

/**
 * Interface HTTPResponse - APICall's response wrapper.
 * @author Martin Rios - Junior Developer
 * @version 6.1
 */
public interface HTTPResponse {
    int getCode();
    String getStatus();
    String getBody();
    boolean isOK();
}