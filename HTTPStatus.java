package api;

/**
 * Class HTTPCodes - RFC 7231 HTTP Statuses.
 * @author Martin Rios - Junior Developer
 * @version 6.1
 */
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
    public static String NOT_FOUND = "Not Found";

    /**
     * 500 Internal Server Error - See RFC 7231
     */
    public static String INTERNAL_SERVER_ERROR = "Internal Server Error";

    /**
     * 502 Bad Gateway - See RFC 7231
     */
    public static String BAD_GATEWAY = "Bad Gateway";

    /**
     * 503 Service Unavailable - See RFC 7231
     */
    public static String SERVICE_UNAVAILABLE = "Service Unavailable";



}
