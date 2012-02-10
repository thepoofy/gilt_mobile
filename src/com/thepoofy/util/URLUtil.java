package com.thepoofy.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.urlfetch.HTTPMethod;
import com.google.appengine.api.urlfetch.HTTPRequest;
import com.google.appengine.api.urlfetch.HTTPResponse;
import com.google.appengine.api.urlfetch.URLFetchService;
import com.google.appengine.api.urlfetch.URLFetchServiceFactory;

/**
 *
 * @author wvanderhoef
 *
 */
public class URLUtil
{
	private static final Logger log = Logger.getLogger(URLUtil.class.getName());



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
			HTTPRequest request = new HTTPRequest(new URL(uri), method);

			request.getFetchOptions().setDeadline(20.0);

			log.info(request.getURL().toExternalForm());

			HTTPResponse res = fetcher.fetch(request);

			if (res.getResponseCode() == 200)
			{
				results = new String(res.getContent(),"UTF-8");
			}
			else
			{
				log.warning("Unable to load page: " + uri + "\nResponse Code:" + res.getResponseCode());
			}
		}
		catch(IOException e)
		{
			log.log(Level.WARNING, e.getMessage(), e);
		}
		return results;
	}

	/**
	 *
	 * @param address
	 * @param params
	 * @return
	 */
	public static String doPost(String address, List<KeyValuePair>params){
		try
		{
			return loadPage(address+"?"+createParamString(params), HTTPMethod.POST);
		}
		catch (Exception e)
		{
			log.log(Level.WARNING, e.getMessage(), e);
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
		catch (IOException e)
		{
			log.log(Level.SEVERE, e.getMessage(), e);
		}
		return null;
	}

	/**
	 *
	 * @param address
	 * @param params
	 * @return
	 */
	public static synchronized String doGet(String address, List<KeyValuePair>params) {
		try
		{
			return loadPage(address+"?"+createParamString(params), HTTPMethod.GET);
		}
		catch (Exception e)
		{
			log.log(Level.WARNING, e.getMessage(), e);
//			e.printStackTrace();
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
			log.log(Level.SEVERE, e.getMessage(), e);
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
