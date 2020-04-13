package network;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

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
 * Class APICall - Overrides HTTP API method calling.
 * @author Martin Rios - SysAdmin and Professional Computer Technician
 * @version 6.0-beta
 */

public class APICall {
    private enum HTTPMethods {
        GET, POST, PUT, PATCH, DELETE;
    }

    public HTTPResponse get(String url) throws APICallException {
        try {
            return this.get(url, new HashMap<>());
        } catch (APICallException error){
            throw new APICallException(error.getMessage());
        }
    }

    public HTTPResponse get(String url, Map<?, ?> headers) throws APICallException {
        try {
            url = url.toLowerCase();

            URL urlObject = new URL(url);

            HttpURLConnection http = (HttpURLConnection) urlObject.openConnection();

            http.setRequestMethod("" + HTTPMethods.GET);

            return setResponse(http, headers);
        } catch (IOException error){
            throw new APICallException(error.getMessage());
        }
    }

    public HTTPResponse post(String url) throws APICallException {
        try {
            return post(url, new HashMap<>(), new HashMap<>());
        } catch (APICallException error){
            throw new APICallException(error.getMessage());
        }
    }

    public HTTPResponse post(String url, Map<?, ?> params) throws APICallException {
        try {
            return post(url, params, new HashMap<>());
        } catch (APICallException error){
            throw new APICallException(error.getMessage());
        }
    }

    public HTTPResponse post(String url, Map<?, ?> params, Map<?, ?> headers) throws APICallException {
        try {
            url = url.toLowerCase();

            URL urlObject = new URL(url);

            HttpURLConnection http = (HttpURLConnection) urlObject.openConnection();

            http.setRequestMethod("" + HTTPMethods.POST);

            http.setDoOutput(true);
            OutputStream output = http.getOutputStream();
            output.write(getURLParams(params).getBytes());
            output.flush();
            output.close();

            return setResponse(http, headers);
        } catch (IOException error){
            throw new APICallException(error.getMessage());
        }
    }

    public static String getURLParams(Map<?, ?> params){
        return getURLParams(params, StandardCharsets.UTF_8);
    }

    public static String getURLParams(Map<?, ?> params, String encoding) {
        StringBuilder urlParams = new StringBuilder();

        try {
            for (Map.Entry<?, ?> entry: params.entrySet()){
                String key = URLEncoder.encode(entry.getKey().toString(), encoding);
                String value = URLEncoder.encode(entry.getValue().toString(), encoding);

                urlParams.append(key).append("=").append(value).append("&");
            }

            return urlParams.toString();
        } catch (UnsupportedEncodingException error) {
            throw new IllegalArgumentException(error.getMessage());
        }
    }

    public static String getURLParams(Map<?, ?> params, Charset encoding) {
        StringBuilder urlParams = new StringBuilder();

        try {
            for (Map.Entry<?, ?> entry: params.entrySet()){
                String key = URLEncoder.encode(entry.getKey().toString(), encoding.toString());
                String value = URLEncoder.encode(entry.getValue().toString(), encoding.toString());

                urlParams.append(key).append("=").append(value).append("&");
            }

            return urlParams.toString();
        } catch (UnsupportedEncodingException error) {
            throw new IllegalArgumentException(error.getMessage());
        }
    }

    public boolean isOK(int code){
        return (code == HTTPCodes.OK || code == HTTPCodes.NOT_MODIFIED || code == HTTPCodes.MOVED_PERMANENTLY);
    }

    private HTTPResponse setResponse(HttpURLConnection http, Map<?, ?> headers) throws IOException{
        try {
            for (Map.Entry<?, ?> entry: headers.entrySet()){
                http.setRequestProperty(entry.getKey().toString().trim(), entry.getValue().toString().trim());
            }

            int code = http.getResponseCode();
            String status = http.getResponseMessage();

            InputStreamReader input;

            if (isOK(code)){
                input = new InputStreamReader(http.getInputStream());
            } else {
                input = new InputStreamReader(http.getErrorStream());
            }

            BufferedReader buffer = new BufferedReader(input);

            String inputLine = buffer.readLine();
            StringBuilder response = new StringBuilder();

            while (inputLine != null){
                response.append(inputLine);
                inputLine = buffer.readLine();
            }

            buffer.close();

            return new APIResponse(code, status, response.toString());
        } catch (IOException error){
            throw new IOException(error.getMessage());
        }
    }
}
