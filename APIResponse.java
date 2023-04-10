package api;

/*
	Copyright (C) 2023 Martin Rios
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

import java.io.IOException;
import java.net.HttpURLConnection;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * Class APIResponse - Wrapper for APICall's response.
 * @author Martin Rios - Technical Leader
 * @version 7.0
 */
// TODO: Refactor response to be a byte array instead of strings.
public class APIResponse implements HTTPResponse {
    protected int code;
    protected byte[] body;
    protected String status;

    public APIResponse(){
        this(HttpURLConnection.HTTP_OK, HTTPStatus.OK, new byte[0]);
    }

    public APIResponse(byte[] body){
        this(HttpURLConnection.HTTP_OK, HTTPStatus.OK, body);
    }

    public APIResponse(APIResponse network){
        this(network.getCode(), network.getStatus(), network.getBody());
    }

    public APIResponse(int code, String status, byte[] body){
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

    public void setBody(byte[] body){
        this.body = body;
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
    public byte[] getBody(){
        return body;
    }

    @Override
    public String toString(){
        return new String(body, Charset.defaultCharset());
    }

    public String toString(String strCharset){
        return new String(body, Charset.forName(strCharset));
    }

    public String toString(Charset charset){
        return new String(body, charset);
    }

    public Path download(String path) throws IOException {
        return Files.write(Paths.get(path), body);
    }

    @Override
    public APIResponse clone() throws CloneNotSupportedException {
        throw new CloneNotSupportedException("HTTP responses are not allowed to clone!");
    }

    @Override
    public boolean isOK(){
        return (code == HttpURLConnection.HTTP_OK || code == HttpURLConnection.HTTP_NOT_MODIFIED);
    }
}
