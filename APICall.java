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

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;
/**
 * Class APICall - Overrides HTTP API method calling.
 * @author Martin Rios - Technical Leader
 * @version 7.0
 */

public class APICall {
    private Charset charset;

    public APICall(){
        this(Charset.defaultCharset());
    }

    public APICall(Charset charset){
        this.charset = charset;
    }

    protected HttpURLConnection getURLConnection(URL url) throws APICallException {
        try {
            return (HttpURLConnection) url.openConnection();
        } catch (Exception error){
            throw new APICallException(error);
        }
    }

    private HttpURLConnection checkRedirection(HttpURLConnection http) throws IOException, APICallException {
        if (isRedirection(http.getResponseCode())) {
            String location = http.getHeaderField("Location");
            http = getURLConnection(new URL(location));
        }
        return http;
    }

    public HTTPResponse get(String url) throws APICallException {
        try {
            return get(url, new HashMap<>());
        } catch (APICallException error){
            throw new APICallException(error);
        }
    }

    public HTTPResponse get(String url, Map<?, ?> headers) throws APICallException {
        try {
            URL urlObject = new URL(url.trim());

            HttpURLConnection http = getURLConnection(urlObject);
            http.setRequestMethod("" + HTTPMethods.GET);

            return setGetResponse(http, headers);
        } catch (IOException error){
            throw new APICallException(error);
        }
    }

    public HTTPResponse post(String url) throws APICallException {
        try {
            return post(url, new HashMap<>(), new HashMap<>());
        } catch (APICallException error){
            throw new APICallException(error);
        }
    }

    public HTTPResponse post(String url, Map<?, ?> params) throws APICallException {
        try {
            return post(url, params, new HashMap<>());
        } catch (APICallException error){
            throw new APICallException(error);
        }
    }

    public HTTPResponse post(String url, Map<?, ?> params, Map<?, ?> headers) throws APICallException {
        try {
            URL urlObject = new URL(url.trim());

            HttpURLConnection http = getURLConnection(urlObject);
            http.setRequestMethod("" + HTTPMethods.POST);

            return setPostResponse(http, headers, params);
        } catch (IOException error){
            throw new APICallException(error);
        }
    }

    public HTTPResponse post(String url, byte[] rawData, Map<?, ?> headers) throws APICallException {
        try {
            URL urlObject = new URL(url.trim());

            HttpURLConnection http = getURLConnection(urlObject);
            http.setRequestMethod("" + HTTPMethods.POST);

            return setPostResponse(http, headers, rawData);
        } catch (IOException error){
            throw new APICallException(error);
        }
    }

    private static String getURIFromParams(Map<?, ?> params, String encoding) throws UnsupportedEncodingException {
        StringBuilder uriParams = new StringBuilder();

        for (Map.Entry<?, ?> entry: params.entrySet()){
            String key = URLEncoder.encode(entry.getKey().toString(), encoding);
            String value = URLEncoder.encode(entry.getValue().toString(), encoding);

            uriParams.append(key).append("=").append(value).append("&");
        }

        return uriParams.toString();
    }

    public static String getURLParams(Map<?, ?> params){
        return getURLParams(params, StandardCharsets.UTF_8);
    }
    public static String getURLParams(Map<?, ?> params, String encoding) {
        try {
            return getURIFromParams(params, encoding);
        } catch (UnsupportedEncodingException error) {
            throw new IllegalArgumentException(error);
        }
    }

    public static String getURLParams(Map<?, ?> params, Charset encoding) {
        try {
            return getURIFromParams(params, encoding.toString());
        } catch (UnsupportedEncodingException error) {
            throw new IllegalArgumentException(error);
        }
    }
    public boolean isOK(int code){
        return (code == HttpURLConnection.HTTP_OK || code == HttpURLConnection.HTTP_NOT_MODIFIED);
    }

    public boolean isRedirection(int code){
        return (code == HttpURLConnection.HTTP_MOVED_TEMP || code == HttpURLConnection.HTTP_MOVED_PERM);
    }

    private InputStream getInputStream(HttpURLConnection http) throws IOException {
        try {
            InputStream inputStream;

            if (http.getResponseCode() >= 200 && http.getResponseCode() <= 300){
                inputStream = http.getInputStream();
            } else {
                inputStream = http.getErrorStream();
            }

            return inputStream;
        } catch (IOException error){
            throw new IOException(error);
        }
    }

    protected HTTPResponse setGetResponse(HttpURLConnection http, Map<?, ?> headers) throws IOException{
        try {
            for (Map.Entry<?, ?> entry: headers.entrySet()){
                http.setRequestProperty(entry.getKey().toString().trim(), entry.getValue().toString().trim());
            }

            http.setInstanceFollowRedirects(true);
            http = checkRedirection(http);

            int code = http.getResponseCode();
            String status = http.getResponseMessage();

            InputStream inputStream = getInputStream(http);

            return setOutput(code, status, inputStream);
        } catch (Exception error){
            error.printStackTrace();
            throw new IOException(error);
        }
    }

    private void writePostBytes(HttpURLConnection http, byte[] data) throws IOException {
        OutputStream output = http.getOutputStream();
        output.write(data);
        output.flush();
        output.close();
    }

    protected HTTPResponse setOutput(int code, String status, InputStream input) throws IOException {
        // TODO: Avoid hardcoding buffer-size.
        byte[] bytes = new byte[4096];
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        int bytesRead = -1;

        while ((bytesRead = input.read(bytes)) != -1) {
            output.write(bytes, 0, bytesRead);
        }

        byte[] outputBytes = output.toByteArray();

        output.close();

        return new APIResponse(code, status, outputBytes);
    }

    protected HTTPResponse setPostResponse(HttpURLConnection http, Map<?, ?> headers, byte[] rawData) throws IOException{
        try {
            for (Map.Entry<?, ?> entry: headers.entrySet()){
                http.setRequestProperty(entry.getKey().toString().trim(), entry.getValue().toString().trim());
            }

            http.setDoOutput(true);

            if (rawData.length != 0){
                writePostBytes(http, rawData);
            }

            http.setInstanceFollowRedirects(true);
            http = checkRedirection(http);

            int code = http.getResponseCode();
            String status = http.getResponseMessage();

            InputStream inputStream = getInputStream(http);

            return setOutput(code, status, inputStream);
        } catch (Exception error){
            error.printStackTrace();
            throw new IOException(error);
        }
    }

    protected HTTPResponse setPostResponse(HttpURLConnection http, Map<?, ?> headers, Map<?, ?> params) throws IOException{
        try {
            for (Map.Entry<?, ?> entry: headers.entrySet()){
                http.setRequestProperty(entry.getKey().toString().trim(), entry.getValue().toString().trim());
            }

            http.setDoOutput(true);

            if (!params.isEmpty()){
                byte[] rawData = getURLParams(params).getBytes();
                writePostBytes(http, rawData);
            }

            http.setInstanceFollowRedirects(true);
            http = checkRedirection(http);

            int code = http.getResponseCode();
            String status = http.getResponseMessage();

            InputStream inputStream = getInputStream(http);

            return setOutput(code, status, inputStream);
        } catch (Exception error){
            error.printStackTrace();
            throw new IOException(error);
        }
    }
}
