package network;

/**
 * Interface HTTPResponse - APICall's response wrapper.
 * @author Martin Rios - SysAdmin and Professional Computer Technician
 * @version 6.0-beta
 */
public interface HTTPResponse {
    int getCode();
    String getStatus();
    String getBody();
    boolean isOK();
}