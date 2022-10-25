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

import java.util.HashMap;
import java.util.Map;

/**
 * Enum HTTPCodes - RFC 7231 HTTP Codes.
 * @author Martin Rios - Junior Developer
 * @version 6.1
 */
public enum HTTPCodes {
    /**
     * HTTP Status Codes - See RFC 7231
     */
    OK(200),
    MOVED_PERMANENTLY(301),
    NOT_MODIFIED(304),
    BAD_REQUEST(400),
    UNAUTHORIZED(401),
    FORBIDDEN(403),
    NOT_FOUND(404),
    INTERNAL_SERVER_ERROR(500),
    BAD_GATEWAY(502),
    SERVICE_UNAVAILABLE(503);

    private int code;
    private static Map<Integer, HTTPCodes> map = new HashMap<>();

    HTTPCodes(int code){
        this.code = code;
    }

    static {
        for (HTTPCodes httpCode: map.values()){
            map.put(httpCode.code, httpCode);
        }
    }

    public static HTTPCodes valueOf(int code){
        return map.get(code);
    }

    public int getValue(){
        return code;
    }
}
