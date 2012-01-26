package com.thepoofy.gwumpy.yelp;

import java.io.IOException;
import java.util.logging.Logger;

import org.codehaus.jackson.JsonParseException;
import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.scribe.builder.ServiceBuilder;
import org.scribe.model.OAuthRequest;
import org.scribe.model.Response;
import org.scribe.model.Token;
import org.scribe.model.Verb;
import org.scribe.oauth.OAuthService;

import com.thepoofy.constants.Constants;
import com.thepoofy.gwumpy.yelp.model.YelpSearchResults;

/**
 * Example code based on code from Nicholas Smith at
 * http://imnes.blogspot.com/2011/01/how-to-use-yelp-v2-from-java-including.html
 * For a more complete example (how to integrate with GSON, etc) see the blog
 * post above.
 * 
 * Example for accessing the Yelp API.
 */
public class Yelp 
{
	private static final Logger log = Logger.getLogger(Yelp.class.getName());

	private OAuthService service;
	private Token accessToken;

	/**
	 * Setup the Yelp API OAuth credentials.
	 * 
	 * OAuth credentials are available from the developer site, under Manage API access (version 2 API).
	 * 
	 */
	public Yelp() 
	{
		this.service = new ServiceBuilder()
				.provider(YelpApi2.class)
				.apiKey(Constants.YELP_CONSUMER_KEY)
				.apiSecret(Constants.YELP_CONSUMER_SECRET)
				.build();
		
		this.accessToken = new Token(Constants.YELP_TOKEN, Constants.YELP_TOKEN_SECRET);
	}

	public static final Integer SEARCH_LIMIT = 20;
	
	/**
	 * Search with term and location.
	 * 
	 * @param term
	 * @param latitude
	 * @param longitude
	 * 
	 * @return JSON string response
	 * @throws IOException 
	 * @throws JsonMappingException 
	 * @throws JsonParseException 
	 */
	public YelpSearchResults search(double latitude, double longitude, int radius, String category, int page) 
			throws JsonParseException, JsonMappingException, IOException {
		OAuthRequest request = new OAuthRequest(Verb.GET,
				"http://api.yelp.com/v2/search");
		
		//request.addQuerystringParameter("sort", "1");
		request.addQuerystringParameter("ll", latitude + "," + longitude);
		request.addQuerystringParameter("radius_filter", ""+radius);
		request.addQuerystringParameter("category_filter", category);
		request.addQuerystringParameter("limit", ""+SEARCH_LIMIT);
		request.addQuerystringParameter("offset", ""+(page*SEARCH_LIMIT));
		
		this.service.signRequest(this.accessToken, request);
		Response response = request.send();
		
		String res = response.getBody();
		log.info(res);
		System.out.println(res);
		ObjectMapper mapper = new ObjectMapper();
		return mapper.readValue(res, YelpSearchResults.class);
	}
}