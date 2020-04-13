package network;

public class HTTPCodes {
    // Avoid instancing
    private HTTPCodes() {}

    /**
     * 200 OK - See RFC 7231
     */
    public static int OK = 200;

    /**
     * 301 Moved Permanently - See RFC 7231
     */
    public static int MOVED_PERMANENTLY = 301;

    /**
     * 304 Not Modified - See RFC 7231
     */
    public static int NOT_MODIFIED = 304;

    /**
     * 400 Bad Request - See RFC 7231
     */
    public static int BAD_REQUEST = 400;

    /**
     * 403 Forbidden - See RFC 7231
     */
    public static int FORBIDDEN = 403;

    /**
     * 404 Not Found - See RFC 7231
     */
    public static int NOT_FOUND = 404;

    /**
     * 500 Internal Server Error - See RFC 7231
     */
    public static int INTERNAL_SERVER_ERROR = 500;

    /**
     * 502 Bad Gateway - See RFC 7231
     */
    public static int BAD_GATEWAY = 502;

    /**
     * 503 Service Unavailable - See RFC 7231
     */
    public static int SERVICE_UNAVAILABLE = 503;
}
