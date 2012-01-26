package com.thepoofy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Logger;

import org.apache.commons.codec.binary.Base64;

import com.google.appengine.api.urlfetch.HTTPHeader;
import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

public class URLUtil
{
	private static final Logger log = Logger.getLogger(URLUtil.class.getName());
	
	/**
	 * 
	 * @param query
	 */
	public static String callAuthenticated(URL query, String username, String password) throws IOException
	{
		HttpURLConnection conn = (HttpURLConnection)query.openConnection();
		
		try
		{
			setBasicAuth(conn, username, password);
			
			return processStream(conn.getInputStream());
		}
		catch(IOException ioe)
		{
			ioe.printStackTrace(System.err);
			
			return processStream(conn.getErrorStream());
		}
		
	}
	
	private static String processStream(InputStream is) throws IOException
	{
		BufferedReader reader = new BufferedReader(new InputStreamReader(is));
		
		
		StringBuilder sb = new StringBuilder();
		while (reader.ready())
		{
			sb.append(reader.readLine());
		}

		reader.close();
		
		return sb.toString();
	}
	
	
	
    /**
     * adapted from: http://stackoverflow.com/questions/1341081/using-http-basic-auth-with-google-app-engine-urlfetch-service
     * 
     * Preemptively set the Authorization header to use Basic Auth.
     * @param connection The HTTP connection
     * @param username Username
     * @param password Password
     */
    private static void setBasicAuth(HttpURLConnection connection, String username, String password) 
    {
        StringBuilder buf = new StringBuilder(username);
        buf.append(':');
        buf.append(password);
        byte[] bytes = null;
        try 
        {
        	bytes = buf.toString().getBytes("ISO-8859-1");
        } 
        catch (java.io.UnsupportedEncodingException uee) 
        {
        	assert false;
        }

        String header = "Basic " + Base64.encodeBase64(bytes);
        connection.setRequestProperty("Authorization", header);
        
        connection.setRequestProperty("User-Agent", "gwumpy");
    }

	
	/**
	 * Takes advantage of the Googe App Engine Fetch Service
	 * @param uri
	 * @return NULL if a problem occurs
	 */
	public static String loadPage(String uri, HTTPMethod method)
	{
		URLFetchService fetcher = URLFetchServiceFactory.getURLFetchService();
		
		String results = null;
		try
		{
			log.info("Loading: "+uri);
			
			HTTPRequest request = new HTTPRequest(new URL(uri), method);
			request.setHeader(new HTTPHeader("User-Agent", "gwumpy.com beta"));
			
			request.getFetchOptions().setDeadline(10.0);
			
			HTTPResponse res = fetcher.fetch(request);
			
			
//			Future<HTTPResponse> future = fetcher.fetchAsync(request);
//			future.
			
			if (res.getResponseCode() == 200)
			{
				results = new String(res.getContent(),"UTF-8"); 
			}
			else
			{
				log.warning("Unable to load page: " + uri + "\nResponse Code:" + res.getResponseCode());
			}
		}
		catch(Exception e)
		{
			e.printStackTrace(System.err);
		}
		return results;
	}
	
	public static String doPost(String address, List<KeyValuePair>params){
		try 
		{	
			return loadPage(address+"?"+createParamString(params), HTTPMethod.POST);
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	public static String doStandalonePost(String address, List<KeyValuePair>params) 
	{
		try 
		{
			// Send data
			URL url = new URL(address);
			URLConnection conn = url.openConnection();
			conn.setDoOutput(true);
			OutputStreamWriter outputStreamWriter = new OutputStreamWriter(conn.getOutputStream());
			outputStreamWriter.write(createParamString(params));
			outputStreamWriter.flush();

			// Get the response
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			StringBuilder sb = new StringBuilder();
			
			while(br.ready())
			{
				sb.append(br.readLine());
			}
			
			outputStreamWriter.close();
			br.close();
			
			return sb.toString();
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	public static String doGet(String address, List<KeyValuePair>params) {
		try 
		{
			return loadPage(address+"?"+createParamString(params), HTTPMethod.GET);
		}
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 
	 * @param address
	 * @param params
	 * @return
	 */
	public static String doStandaloneGet(String address, List<KeyValuePair>params) 
	{
		try
		{
			// Send data
			URL url = new URL(address+"?"+createParamString(params));
			System.out.println(url.toExternalForm());
			URLConnection conn = url.openConnection();

			// Get the response
			BufferedReader br = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			
			StringBuilder sb = new StringBuilder();
			
			while(br.ready())
			{
				sb.append(br.readLine());
			}
			
			br.close();
			
			return sb.toString();
			
		} 
		catch (Exception e) 
		{
			e.printStackTrace();
		}
		return null;
	}
	
	
	private static String createParamString(List<KeyValuePair> params)
	{
		
		StringBuilder data = new StringBuilder();
		for(KeyValuePair param : params)
		{
			try{
				data.append(URLEncoder.encode(param.key, "UTF-8"))
				.append("=")
				.append(URLEncoder.encode(param.value, "UTF-8"))
				.append("&");	
			}
			catch(UnsupportedEncodingException uee)
			{
				assert false;
			}
		}
		return data.toString();
	}
	

	/**
	 * 
	 * @param parameters
	 * @return
	 */
	public static List<KeyValuePair> convertToKeyValuePair(Map<String, String>parameters)
	{
		List<KeyValuePair> pairsList = new ArrayList<KeyValuePair>();
		
		Set<String>keys = parameters.keySet();
		for(String key: keys)
		{
			pairsList.add(new KeyValuePair(key, parameters.get(key)));
		}
		
		return pairsList;
	}
}
