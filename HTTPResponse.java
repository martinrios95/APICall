package network;

public interface HTTPResponse {
    int getCode();
    String getStatus();
    String getBody();
}