package network;

/*
	Copyright (C) 2020 Martin Rios
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
 * Class HTTPCodes - RFC 7231 HTTP Codes.
 * @author Martin Rios - SysAdmin and Professional Computer Technician
 * @deprecated Will be put into APICall class.
 * @version 6.0-beta
 */

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
