package network;

public class HTTPStatus {
    // Avoid instancing
    private HTTPStatus(){}

    /**
     * 200 OK - See RFC 7231
     */
    public static String OK = "OK";

    /**
     * 301 Moved Permanently - See RFC 7231
     */
    public static String MOVED_PERMANENTLY = "Moved Permanently";

    /**
     * 304 Not Modified - See RFC 7231
     */
    public static String NOT_MODIFIED = "Not Modified";

    /**
     * 400 Bad Request - See RFC 7231
     */
    public static String BAD_REQUEST = "Bad Request";

    /**
     * 403 Forbidden - See RFC 7231
     */
    public static String FORBIDDEN = "Forbidden";

    /**
     * 404 Not Found - See RFC 7231
     */
    public static String NOT_FOUND = "File not found";

    /**
     * 500 Stringernal Server Error - See RFC 7231
     */
    public static String StringERNAL_SERVER_ERROR = "Internal Server Error";

    /**
     * 502 Bad Gateway - See RFC 7231
     */
    public static String BAD_GATEWAY = "Bad Gateway";

    /**
     * 503 Service Unavailable - See RFC 7231
     */
    public static String SERVICE_UNAVAILABLE = "Service Unavailable";

    

}
