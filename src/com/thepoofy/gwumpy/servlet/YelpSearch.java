/**
 * 
 */
package com.thepoofy.gwumpy.servlet;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;

import com.thepoofy.constants.VenueCategoryEnum;
import com.thepoofy.gwumpy.yelp.Yelp;
import com.thepoofy.gwumpy.yelp.model.Business;
import com.thepoofy.gwumpy.yelp.model.YelpSearchResults;
import com.thepoofy.util.location.Location;

/**
 * @author Willum
 *
 */
@SuppressWarnings("serial")
public class YelpSearch extends VenueSearch<Business>
{
	private static final Logger log = Logger.getLogger(YelpSearch.class.getName());
	
	private Yelp yelpDao;
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.GenericServlet#init(javax.servlet.ServletConfig)
	 */
	@Override
	public void init(ServletConfig cfg) throws ServletException
	{
		super.init(cfg);
		
		yelpDao = new Yelp();
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.thepoofy.gwumpy.servlet.VenueSearch#search(com.thepoofy.util.Location, java.lang.Integer, com.thepoofy.constants.VenueCategoryEnum)
	 */
	@Override
	List<Business> search(Location loc, Integer radius, VenueCategoryEnum category)
			throws Exception 
	{
		List<Business> businessList = findYelp(loc, radius, category);
		
		return businessList;
	}

	
	private List<Business> findYelp(Location loc, Integer radius, VenueCategoryEnum category) throws Exception
	{
		YelpSearchResults response = yelpDao.search(loc.getLatitude(), loc.getLongitude(), radius*2, category.getYelpId(), 0);
		List<Business> bizList = new ArrayList<Business>();
		
		bizList.addAll(response.getBusinesses());
		
		if(response.getTotal() > Yelp.SEARCH_LIMIT)
		{
			YelpSearchResults response1 = yelpDao.search(loc.getLatitude(), loc.getLongitude(), radius*2, category.getYelpId(), Yelp.SEARCH_LIMIT);

			bizList.addAll(response1.getBusinesses());
		}
		
		
		return response.getBusinesses();
	}
	

}
