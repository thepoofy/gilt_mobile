package com.thepoofy.ca.dao.foursquare;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.auth.InvalidCredentialsException;

import com.thepoofy.constants.Constants;
import com.thepoofy.util.KeyValuePair;
import com.thepoofy.util.URLUtil;
import com.williamvanderhoef.foursquare.adapters.JsonSyntaxException;
import com.williamvanderhoef.foursquare.model.subtypes.Results;
import com.williamvanderhoef.foursquare.parsers.JsonUtil;
import com.williamvanderhoef.foursquare.parsers.ResultsParser;

public class FoursquareDao
{
	private final String endpointBase;
	private boolean usePost = false;
	private final String accessToken;

	/**
	 * 
	 * @param token
	 * @param endPoint
	 */
	public FoursquareDao(String endPoint, String accessToken)
	{
		if(endPoint == null || endPoint.length()==0)
		{
			throw new IllegalArgumentException("EndPoint must be specified.");
		}
		
//		if(accessToken == null)
//		{
//			throw new IllegalArgumentException("AccessToken cannot be null.");
//		}
		
		this.accessToken = accessToken;
		this.endpointBase = endPoint;
	}

	public void setUsePost(boolean usePost)
	{
		this.usePost = usePost;
	}

	
	/**
	 * Some endpoints require no parameters, so this is for convenience.
	 * @return
	 * @throws IOException
	 * @throws HttpConnectionException
	 * @throws InvalidCredentialsException
	 */
	protected <T> T execute(String targetId, String action, Results<T> results) throws FoursquareException, JsonSyntaxException, IOException
	{
		return this.execute(targetId, action, new HashMap<String, String>(), results);
	}
	
	/**
	 * 
	 * @param parameters
	 * @throws InvalidCredentialsException 
	 * @throws HttpConnectionException 
	 * @throws IOException 
	 */
	protected <T> T  execute(String targetId, String action, Map<String, String>parameters, Results<T> results) throws JsonSyntaxException, FoursquareException, IOException
	{
		if(parameters == null)
		{
			throw new IllegalArgumentException("Parameters to execute cannot be null.");
		}
		
		List<KeyValuePair> params = URLUtil.convertToKeyValuePair(parameters);
		addAuthentication(params);
		addLastTestedVersion(params);
		StringBuilder pathBuilder = new StringBuilder(Constants.FOURSQUARE_API_URL);
		pathBuilder.append(this.endpointBase);
		
		if(targetId != null)
		{
			pathBuilder.append("/");
			pathBuilder.append(targetId);
		}
		if(action != null)
		{
			pathBuilder.append("/");
			pathBuilder.append(action);
		}
		
		String response;
		if(!usePost)
		{
			response = URLUtil.doGet(pathBuilder.toString(), params);
		}	
		else
		{
			response = URLUtil.doPost(pathBuilder.toString(), params);
		}
		
		
		return unwrap(response, results);
	}
	
	private void addLastTestedVersion(List<KeyValuePair> params)
	{
		params.add(new KeyValuePair("v",Constants.FSQ_LAST_TESTED_VERSION));
	}
	
	/**
	 * 
	 * @param params
	 */
	private void addAuthentication(List<KeyValuePair> params)
	{
		//take the oauth_token from the application cache
		if(accessToken != null)
		{
			params.add(new KeyValuePair("oauth_token", accessToken));
		}
		else
		{
//			Log.e(Constants.TAG, "FoursquareDao: access token was null");
			
			
			params.add(new KeyValuePair("client_id",Constants.FSQ_CLIENT_ID));
			params.add(new KeyValuePair("client_secret",Constants.FSQ_CLIENT_SECRET));
		}
	}
	
	
	
	
	/**
	 * 
	 * @param <T>
	 * @param json
	 * @param adapter
	 * @return
	 * @throws IOException
	 */
	private <T> T unwrap(String json, Results<T> adapter) throws FoursquareException, JsonSyntaxException
	{
		if(json == null)
		{
			return null;
		}
		
		ResultsParser<T> parser = JsonUtil.getParser(adapter);
		
		Results<T> response = parser.fromJson(json);
		
		if(response.getMeta().getCode() != 200)
		{
//			Log.e(Constants.TAG, "response came back with error code: "+response.getMeta().getCode());
			
			//TODO evaluate code and throw a meaningful exception
			//TODO make this a Foursquare Exception or something...
			throw new FoursquareException(response.getMeta().getErrorDetail());
		}
		
		return response.getResponse();
	}
}
