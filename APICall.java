package api;

import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.Exception;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/*
    Copyright (C) 2018 Martin Rios
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
 * @version 3.0beta
 */
public class APICall {
    // Instance variables
    private String buffer;

    // Static variables from class
    private static final String HTTP_HEADER = "http://";
    private static final String HTTPS_HEADER = "https://";

    private static final String MALFORMED_URL_STRING = "Host is unreachable!";
    private static final String EMPTY_URL_STRING = "Empty URL String!";
    private static final String EMPTY_URL_METHOD = "Empty HTTP-parsing method!";
    private static final String NON_EXISTENT_URL_METHOD = "HTTP-parsing method doesn't exist!";
    private static final String BAD_RESPONSE_CODE = "Error while processing data: ";

    private static final String[] HTTP_METHODS = {"get", "post", "put", "patch", "delete"};

    /**
     * Send request to server and save it, using GET method.
     * @param host API's URL.
     * @throws Exception If HTTP method doesn't exist, host/method are empty, URL is malformed or HTTP's response is not 200 (OK)
     */
    public void send(final String host) throws Exception {
        try {
            send(host, "get");
        } catch (Exception error){
            throw new Exception(error.getMessage());
        }
    }

    /**
     * Send request to server and save it, using any method.
     * @param host API's URL.
     * @param method HTTP's method for API's calling.
     * @throws Exception If HTTP method doesn't exist, host/method are empty, URL is malformed or HTTP's response is not 200 (OK)
     */
    public void send(final String host, final String method) throws Exception {
        try {
            if (host.trim().isEmpty()) throw new Exception(EMPTY_URL_STRING);
            if (method.trim().isEmpty()) throw new Exception(EMPTY_URL_METHOD);

            if (!exists(method)) throw new Exception(NON_EXISTENT_URL_METHOD);

            // Set URL and HTTP-connection classes.
            URL urlHost = new URL(host.trim());

            HttpURLConnection myConnection;

            // Create connection
            if (host.toLowerCase().contains(HTTP_HEADER.toLowerCase())){
                myConnection = (HttpURLConnection) urlHost.openConnection();
            } else if (host.toLowerCase().contains(HTTPS_HEADER.toLowerCase())){
                myConnection = (HttpsURLConnection) urlHost.openConnection();
            } else throw new Exception(MALFORMED_URL_STRING);

            myConnection.setRequestMethod(method.toUpperCase());

            // POST Header
            if (method.toLowerCase().equals("post")){
                myConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }

            // Set connection and response.
            if (myConnection.getResponseCode() == 200){
                StringBuilder out = new StringBuilder();
                InputStream input = new BufferedInputStream(myConnection.getInputStream());

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    String inputLine = "";
                    while ((inputLine = reader.readLine()) != null) {
                        out.append(inputLine);
                    }
                } catch (Exception error) {
                    throw new Exception(error.getMessage());
                } finally {
                    buffer = out.toString();
                }
            } else {
                throw new Exception(BAD_RESPONSE_CODE + myConnection.getResponseMessage());
            }
        } catch (Exception error){
            throw new Exception(error.getMessage());
        }
    }

    /**
     * Send request to server and save it, using methods and headers.
     * Headers are presented as Map. You can use any subclass which implements that interface on this method.
     * @see java.util.Map
     * @param host API's URL.
     * @param method HTTP's method for API's calling.
     * @throws Exception If HTTP method doesn't exist, host/method are empty, URL is malformed or HTTP's response is not 200 (OK)
     */
    public void send(final String host, final String method, final Map<String, String> headers) throws Exception {
        try {
            if (host.trim().isEmpty()) throw new Exception(EMPTY_URL_STRING);
            if (method.trim().isEmpty()) throw new Exception(EMPTY_URL_METHOD);

            if (!exists(method)) throw new Exception(NON_EXISTENT_URL_METHOD);

            // Set URL and HTTP-connection classes.
            URL urlHost = new URL(host.trim());

            HttpURLConnection myConnection;

            // Create connection
            if (host.toLowerCase().contains(HTTP_HEADER.toLowerCase())){
                myConnection = (HttpURLConnection) urlHost.openConnection();
            } else if (host.toLowerCase().contains(HTTPS_HEADER.toLowerCase())){
                myConnection = (HttpsURLConnection) urlHost.openConnection();
            } else throw new Exception(MALFORMED_URL_STRING);

            myConnection.setRequestMethod(method.toUpperCase());

            // POST Header
            if (method.toLowerCase().equals("post")){
                myConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }

            // Set HTTP Headers
            for (String key: headers.keySet()){
                myConnection.setRequestProperty(key, headers.get(key));
            }

            // Set connection and response.
            if (myConnection.getResponseCode() == 200){
                StringBuilder out = new StringBuilder();
                InputStream input = new BufferedInputStream(myConnection.getInputStream());

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    String inputLine = "";
                    while ((inputLine = reader.readLine()) != null) {
                        out.append(inputLine);
                    }
                } catch (Exception error) {
                    throw new Exception(error.getMessage());
                } finally {
                    buffer = out.toString();
                }
            } else {
                throw new Exception(BAD_RESPONSE_CODE + myConnection.getResponseMessage());
            }
        } catch (Exception error){
            throw new Exception(error.getMessage());
        }
    }

    /**
     * <i>Only for Android developers.</i>
     * Send request to server and save it, using methods and headers.
     * Headers are presented <i>as Bundle</i>. Bundle is <b>an Android OS' class</i> for using key/value pairs.</b>
     * @see <a href="https://developer.android.com/reference/android/os/Bundle" target="_blank">Bundle</a>
     * @param host API's URL.
     * @param method HTTP's method for API's calling.
     * @throws Exception If HTTP method doesn't exist, host/method are empty, URL is malformed or HTTP's response is not 200 (OK)
     */
    public void send(final String host, final String method, final Bundle headers) throws Exception {
        try {
            if (host.trim().isEmpty()) throw new Exception(EMPTY_URL_STRING);
            if (method.trim().isEmpty()) throw new Exception(EMPTY_URL_METHOD);

            if (!exists(method)) throw new Exception(NON_EXISTENT_URL_METHOD);

            // Set URL and HTTP-connection classes.
            URL urlHost = new URL(host.trim());

            HttpURLConnection myConnection;

            // Create connection
            if (host.toLowerCase().contains(HTTP_HEADER.toLowerCase())){
                myConnection = (HttpURLConnection) urlHost.openConnection();
            } else if (host.toLowerCase().contains(HTTPS_HEADER.toLowerCase())){
                myConnection = (HttpsURLConnection) urlHost.openConnection();
            } else throw new Exception(MALFORMED_URL_STRING);

            myConnection.setRequestMethod(method.toUpperCase());

            // POST Header
            if (method.toLowerCase().equals("post")){
                myConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
            }

            // Set HTTP Headers
            for (String key: headers.keySet()){
                myConnection.setRequestProperty(key, headers.getString(key));
            }

            // Set connection and response.
            if (myConnection.getResponseCode() == 200){
                StringBuilder out = new StringBuilder();
                InputStream input = new BufferedInputStream(myConnection.getInputStream());

                try {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(input));
                    String inputLine = "";
                    while ((inputLine = reader.readLine()) != null) {
                        out.append(inputLine);
                    }
                } catch (Exception error) {
                    throw new Exception(error.getMessage());
                } finally {
                    buffer = out.toString();
                }
            } else {
                throw new Exception(BAD_RESPONSE_CODE + myConnection.getResponseMessage());
            }
        } catch (Exception error){
            throw new Exception(error.getMessage());
        }
    }

    /**
     * Get saved response from API-calling.
     * @return Server's response from API.
     */
    public String receive(){
        return buffer;
    }

    // Sources:
    // http://sawchenko.net/blog/android/2014/02/28/Reading-an-httpurlconnection-response/
    // https://gist.github.com/ssawchenko/9282300

    private boolean exists(String method){
        boolean found = false;
        if (method.isEmpty()) return false;
        for (String currentMethod: HTTP_METHODS){
            found = currentMethod.equals(method.toLowerCase());
            if (found) break;
        }
        return found;
    }

    /**
     * Get current data from API.
     * @return A string containing data received from API called previously by send(), or null if there's no API called.
     */
    @Override
    public String toString(){
        return "data --> " + buffer;
    }
}
