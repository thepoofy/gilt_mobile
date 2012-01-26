/**
 * 
 */
package com.thepoofy.gwumpy.servlet;

import java.util.List;

import com.thepoofy.ca.dao.foursquare.VenuesDao;
import com.thepoofy.constants.VenueCategoryEnum;
import com.thepoofy.util.location.Location;
import com.williamvanderhoef.foursquare.model.Venue;
import com.williamvanderhoef.foursquare.responses.VenueSearchResponse;

/**
 * @author Willum
 *
 */
@SuppressWarnings("serial")
public class FoursquareSearch extends VenueSearch<Venue>
{
	@Override
	List<Venue> search(Location loc, Integer radius, VenueCategoryEnum category)
			throws Exception 
	{
		VenuesDao dao = new VenuesDao(null);
		
		VenueSearchResponse vsr = dao.browse(loc, radius, category.getFsqId());
		
		return vsr.getVenues();
	}

	
}
