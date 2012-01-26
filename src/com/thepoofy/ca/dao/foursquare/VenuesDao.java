package com.thepoofy.ca.dao.foursquare;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.auth.InvalidCredentialsException;

import com.thepoofy.constants.Constants;
import com.thepoofy.util.location.Location;
import com.williamvanderhoef.foursquare.model.subtypes.Results;
import com.williamvanderhoef.foursquare.responses.VenueLinksResponse;
import com.williamvanderhoef.foursquare.responses.VenueResponse;
import com.williamvanderhoef.foursquare.responses.VenueSearchResponse;

/**
 * 
 * @author <a href="mailto://william.vanderhoef@gmail.com">William Vanderhoef</a>
 *
 */
public class VenuesDao
{
	private final FoursquareDao dao;
	
	/**
	 * 
	 * @param accessToken
	 */
	public VenuesDao(String accessToken)
	{
		dao = new FoursquareDao("venues", accessToken);
	}
	
	/**
	 * Used for making Userless requests against the foursquare venues api.
	 * 
	 * @param accessToken
	 */
	public VenuesDao()
	{
		dao = new FoursquareDao("venues", null);
	}
	
	/**
	 * Venue Detail
	 * http://developer.foursquare.com/docs/venues/venues.html
	 * 
	 * @param venueId
	 * @return
	 * @throws IOException
	 * @throws HttpConnectionException
	 * @throws InvalidCredentialsException
	 */
	public VenueResponse getDetails(String venueId) throws IOException, InvalidCredentialsException, Exception
	{
		Map<String, String>params = new HashMap<String,String>();
		
		if(venueId == null)
		{
			throw new Exception("Venue ID is required for Venue Details");
		}
		
		if(!Constants.MOCK_DATA)
		{
			return dao.execute(venueId,null,params, new Results<VenueResponse>(){});
		}
		else
		{
			return new VenueResponse();
		}
	}
	
	public VenueSearchResponse match(Location loc, String query) throws IOException, InvalidCredentialsException, Exception
	{
		return search(loc, null,  query, null, "match", null);
	}
	
	public VenueSearchResponse browse(Location loc, Integer radius, String category) throws IOException, InvalidCredentialsException, Exception
	{
		return search(loc, radius,  null, 100, "browse", category);
	}
	
	/**
	 * Search Venues
	 * http://developer.foursquare.com/docs/venues/search.html
	 * 
	 * 
	 * @throws InvalidCredentialsException 
	 * @throws HttpConnectionException 
	 * @throws IOException 
	 * @throws Exception
	 */
	public VenueSearchResponse search(Location loc, Integer radius, 
			String query, 
			Integer limit, 
			String intent,
			String category
			) 
	
		throws IOException, InvalidCredentialsException, Exception
	{
		Map<String, String>params = new HashMap<String,String>();
		
		
		if(loc != null)
		{
			params.put("ll", ""+loc.getLatitude()+","+loc.getLongitude());
			
			if(loc.getAccuracy() != null)
			{
				params.put("llAcc", ""+loc.getAccuracy());
			}
		}
		else
		{
			throw new Exception("Latitude and Longitude are required for Venue Search");
		}
		
		if(query != null)
		{
			params.put("query", query);
		}
		if(radius != null)
		{
			params.put("radius", ""+radius);
		}
		if(limit != null)
		{
			params.put("limit", limit.toString());
		}
		if(intent != null)
		{
			params.put("intent", intent);
		}
		if(category != null)
		{
			params.put("categoryId", category);
		}
		
		if(!Constants.MOCK_DATA)
		{
			return dao.execute(null,"search",params, new Results<VenueSearchResponse>(){});
		}
		else
		{
			return new VenueSearchResponse();
		}
	}
	
	
	/**
	 * http://developer.foursquare.com/docs/venues/links.html
	 * 
	 * 
	 * @throws InvalidCredentialsException 
	 * @throws HttpConnectionException 
	 * @throws IOException 
	 * @throws Exception
	 */
	public VenueLinksResponse links(String venueId) throws IOException, InvalidCredentialsException, Exception
	{
		Map<String, String>params = new HashMap<String,String>();
		
		
		if(venueId == null)
		{
			throw new Exception("Venue ID is required for Venue Links");
		}
		
		if(!Constants.MOCK_DATA)
		{
			return dao.execute(venueId,"links",params, new Results<VenueLinksResponse>(){});
		}
		else
		{
			return new VenueLinksResponse();
		}
	}
}
