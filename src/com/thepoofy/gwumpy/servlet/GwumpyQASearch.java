/**
 * 
 */
package com.thepoofy.gwumpy.servlet;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.thepoofy.ca.dao.foursquare.VenuesDao;
import com.thepoofy.constants.VenueCategoryEnum;
import com.thepoofy.gwumpy.yelp.Yelp;
import com.thepoofy.gwumpy.yelp.YelpMatcher;
import com.thepoofy.gwumpy.yelp.model.Business;
import com.thepoofy.gwumpy.yelp.model.YelpSearchResults;
import com.thepoofy.util.location.Location;
import com.williamvanderhoef.foursquare.model.Venue;
import com.williamvanderhoef.foursquare.responses.VenueSearchResponse;

/**
 * @author Willum
 *
 */
@SuppressWarnings("serial")
public class GwumpyQASearch extends VenueSearch<Map<String, Object>>
{
	private static final Logger log = Logger.getLogger(GwumpyQASearch.class.getName());
	
	private Yelp yelpDao;
	
	
	@Override
	public void init(ServletConfig cfg) throws ServletException
	{
		super.init(cfg);
		
		yelpDao = new Yelp();
	}
	
	@Override
	List<Map<String, Object>> search(Location loc, Integer radius, VenueCategoryEnum category)
			throws Exception 
	{
		List<Map<String, Object>> data = new ArrayList<Map<String, Object>>();
		
		List<Venue> fsqPlaces = find4sq(loc, radius, category);
		List<Business> businessList = findYelp(loc, radius, category);
		if(fsqPlaces == null || fsqPlaces.isEmpty())
		{
			log.info("no results found from foursquare");
			return data;
		}
		for(Venue v : fsqPlaces)
		{
			Map<String, Object> place = new HashMap<String, Object>();
			
			place.put("fsq", v);
			
			YelpMatcher matcher = new YelpMatcher(v);
			Business biz = matcher.findSimilarYelpPlace(businessList);
			place.put("yelp", biz);
			
			data.add(place);
		}
		
		
		return data;
	}

	private List<Venue> find4sq(Location loc, Integer radius, VenueCategoryEnum category) throws Exception
	{
		VenuesDao dao = new VenuesDao(null);
		
		VenueSearchResponse vsr = dao.browse(loc, radius, category.getFsqId());
		
		Iterator<Venue> itr = vsr.getVenues().iterator();
		while(itr.hasNext())
		{
			Venue v = itr.next();
			if( ! (v.getVerified()
					|| v.getStats().getUsersCount() > 5
					|| v.getStats().getCheckinsCount() > 25))
			{
				log.info("Removing from results: "+v.getName());
				itr.remove();
			}
		}
		
		return vsr.getVenues();
	}
	
	private List<Business> findYelp(Location loc, Integer radius, VenueCategoryEnum category) throws Exception
	{
		YelpSearchResults response = yelpDao.search(loc.getLatitude(), loc.getLongitude(), radius, category.getYelpId(), 0);
		List<Business> bizList = new ArrayList<Business>();
		
		bizList.addAll(response.getBusinesses());
		
		if(response.getTotal() > Yelp.SEARCH_LIMIT)
		{
			YelpSearchResults response1 = yelpDao.search(loc.getLatitude(), loc.getLongitude(), radius, category.getYelpId(), 1);

			bizList.addAll(response1.getBusinesses());
		}
		
		
		return response.getBusinesses();
	}
	

}
