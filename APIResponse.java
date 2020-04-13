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
 * Class APIResponse - Wrapper for APICall's response.
 * @author Martin Rios - SysAdmin and Professional Computer Technician
 * @version 6.0-beta
 */

public class APIResponse implements HTTPResponse {
    protected int code;
    protected String body;
    protected String status;

    public APIResponse(){
        this(HTTPCodes.OK, HTTPStatus.OK, "");
    }

    public APIResponse(String response){
        this(HTTPCodes.OK, HTTPStatus.OK, response);
    }

    public APIResponse(APIResponse network){
        this(network.getCode(), network.getStatus(), network.getBody());
    }

    public APIResponse(int code, String status, String body){
        setCode(code);
        setStatus(status);
        setBody(body);
    }

    public void setCode(int code){
        this.code = code;
    }

    public void setStatus(String status){
        this.status = status;
    }

    public void setBody(String body){
        this.body = body;
    }

    @Override
    public String toString(){
        return this.code + " " + this.status;
    }

    @Override
    public int getCode(){
        return code;
    }

    @Override
    public String getStatus(){
        return status;
    }

    @Override
    public String getBody(){
        return body;
    }

    @Override
    public APIResponse clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("HTTP responses are not allowed to clone!");
    }
}
