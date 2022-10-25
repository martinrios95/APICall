package api;
/*
	Copyright (C) 2022 Martin Rios
	All rights reserved.

	Redistribution and use in source and binary forms, with or without modification, are
	permitted provided that the following conditions are met:

	 1. Redistributions of source code must retain the above copyright notice, this list of
		conditions and the following disclaimer.

	 2. Redistributions in binary form must reproduce the above copyright notice, this list
		of conditions and the following disclaimer in the documentation and/or other materials
		provided with the distribution.

	THIS SOFTWARE IS PROVIDED BY MARTIN RIOS "AS IS" AND ANY EXPRESS OR IMPLIED
	WARRANTIES, INCLUDING, BUT NOT LIMITED TO, THE IMPLIED WARRANTIES OF MERCHANTABILITY AND
	FITNESS FOR A PARTICULAR PURPOSE ARE DISCLAIMED. IN NO EVENT SHALL MARTIN RIOS OR
	CONTRIBUTORS BE LIABLE FOR ANY DIRECT, INDIRECT, INCIDENTAL, SPECIAL, EXEMPLARY, OR
	CONSEQUENTIAL DAMAGES (INCLUDING, BUT NOT LIMITED TO, PROCUREMENT OF SUBSTITUTE GOODS OR
	SERVICES; LOSS OF USE, DATA, OR PROFITS; OR BUSINESS INTERRUPTION) HOWEVER CAUSED AND ON
	ANY THEORY OF LIABILITY, WHETHER IN CONTRACT, STRICT LIABILITY, OR TORT (INCLUDING
	NEGLIGENCE OR OTHERWISE) ARISING IN ANY WAY OUT OF THE USE OF THIS SOFTWARE, EVEN IF
	ADVISED OF THE POSSIBILITY OF SUCH DAMAGE.

	The views and conclusions contained in the software and documentation are those of the
	authors and should not be interpreted as representing official policies, either expressed
	or implied, of Martin Rios.
*/

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
