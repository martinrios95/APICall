package network;

import android.os.Bundle;

import java.io.BufferedInputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Map;

import javax.net.ssl.HttpsURLConnection;

/*
	Copyright (C) 2019 Martin Rios
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
 * @version 5.0
 */
public class APICall {
	// Instance variables
	private String buffer;
	private int responseCode;

	// Static PRIVATE variables from class
	private final String HTTP_HEADER = "http://";
	private final String HTTPS_HEADER = "https://";

	private final String MALFORMED_URL_STRING = "Host is unreachable!";
	private final String EMPTY_URL_STRING = "Empty URL String!";
	private final String EMPTY_URL_METHOD = "Empty HTTP-parsing method!";
	private final String NON_EXISTENT_URL_METHOD = "HTTP-parsing method doesn't exist!";
	private final String BAD_RESPONSE_CODE = "Error while processing data: ";

	private final String[] HTTP_METHODS = {"get", "post", "put", "patch", "delete"};

	// Static PUBLIC variables for class, including HTTP response codes defined by RFC 7231
	// See details on https://www.iana.org/assignments/http-status-codes/http-status-codes.xhtml

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

	/**
	 * Send request to server and save it, using GET method.
	 * @param host API's URL.
	 * @throws Exception If HTTP method doesn't exist, host/method are empty, URL is malformed or HTTP's response is not successful
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
	 * @throws Exception If HTTP method doesn't exist, host/method are empty, URL is malformed or HTTP's response is not successful
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

			// POST Header and encoded-data
			if (method.toLowerCase().equals("post")){
				myConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

				String[] hostSegments = host.split("\\?");
				String url = hostSegments[0];
				String uri = hostSegments[1];

				OutputStream output = myConnection.getOutputStream();
				output.write(uri.getBytes());
				output.flush();
				output.close();
			}

			responseCode = myConnection.getResponseCode();

			// Valid responses: 200 OK, 301 Moved Permanently, 304 Not Modified
			boolean success = (responseCode == OK) || (responseCode == MOVED_PERMANENTLY) || (responseCode == NOT_MODIFIED);

			// Set connection and response.
			if (success){
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

				// Avoid memory leaking
				input.close();
				input = null;
			} else {
				throw new Exception(BAD_RESPONSE_CODE + myConnection.getResponseCode() + " " + myConnection.getResponseMessage());
			}

			// Avoid memory leaking
			myConnection.disconnect();
			myConnection = null;
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
	 * @throws Exception If HTTP method doesn't exist, host/method are empty, URL is malformed or HTTP's response is not successful
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

			// POST Header and encoded-data
			if (method.toLowerCase().equals("post")){
				myConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				myConnection.setDoOutput(true);

				String[] hostSegments = host.split("\\?");
				String url = hostSegments[0];
				String uri = hostSegments[1];

				OutputStream output = myConnection.getOutputStream();
				output.write(uri.getBytes());
				output.flush();
				output.close();
			}

			// Set HTTP Headers
			for (String key: headers.keySet()){
				myConnection.setRequestProperty(key, headers.get(key));
			}

			responseCode = myConnection.getResponseCode();

			// Valid responses: 200 OK, 301 Moved Permanently, 304 Not Modified
			boolean success = (responseCode == OK) || (responseCode == MOVED_PERMANENTLY) || (responseCode == NOT_MODIFIED);

			// Set connection and response.
			if (success){
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

				// Avoid memory leaking
				input.close();
				input = null;
			} else {
				throw new Exception(BAD_RESPONSE_CODE + myConnection.getResponseCode() + " " + myConnection.getResponseMessage());
			}

			// Avoid memory leaking
			myConnection.disconnect();
			myConnection = null;
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
	 * @throws Exception If HTTP method doesn't exist, host/method are empty, URL is malformed or HTTP's response is not successful
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

			// POST Header and encoded-data
			if (method.toLowerCase().equals("post")){
				myConnection.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");

				String[] hostSegments = host.split("\\?");
				String url = hostSegments[0];
				String uri = hostSegments[1];

				OutputStream output = myConnection.getOutputStream();
				output.write(uri.getBytes());
				output.flush();
				output.close();
			}

			// Set HTTP Headers
			for (String key: headers.keySet()){
				myConnection.setRequestProperty(key, headers.getString(key));
			}

			responseCode = myConnection.getResponseCode();

			// Valid responses: 200 OK, 301 Moved Permanently, 304 Not Modified
			boolean success = (responseCode == OK) || (responseCode == MOVED_PERMANENTLY) || (responseCode == NOT_MODIFIED);

			// Set connection and response.
			if (success){
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

				// Avoid memory leaking
				input.close();
				input = null;
			} else {
				throw new Exception(BAD_RESPONSE_CODE + myConnection.getResponseCode() + " " + myConnection.getResponseMessage());
			}

			// Avoid memory leaking
			myConnection.disconnect();
			myConnection = null;
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

	private boolean exists(String method){
		boolean found = false;
		if (method.isEmpty()) return false;

		int i = 0;
		while (i < HTTP_METHODS.length && !found){
			found = HTTP_METHODS[i].equals(method.toLowerCase());
			if (!found) i++;
		}

		return found;
	}

	/**
	 * Erase data from API calling.
	 */
	public void eraseBuffer(){
		buffer = null;
	}

	/**
	 * Get current response code from HTTP server on API calling.
	 */
	public int getResponseCode(){
		return responseCode;
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