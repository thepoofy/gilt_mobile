/**
 * 
 */
package com.thepoofy.gwumpy.servlet;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thepoofy.ca.dao.foursquare.VenuesDao;
import com.thepoofy.constants.VenueCategoryEnum;
import com.thepoofy.gwumpy.model.VenueSummary;
import com.thepoofy.util.location.Location;
import com.williamvanderhoef.foursquare.responses.VenueSearchResponse;

/**
 * @author Willum
 *
 */
@SuppressWarnings("serial")
public class GwumpySearch extends ServletBase
{
	private void handleResponse(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		try
		{
			VenuesDao dao = new VenuesDao(null);
			
			Double lat = getParameterDouble(request, "lat", true);
			Double lng = getParameterDouble(request, "lng", true); //Double.parseDouble(request.getParameter("lng"));
			Double llAcc = getParameterDouble(request, "llAcc", false);//Integer.parseInt(request.getParameter("llAcc"));
			String cat = getParameter(request, "category", false);
			String standards = getParameter(request, "standard", false);
			String searchTerm = getParameter(request, "searchTerm", false);
			
			Location loc = new Location();
			loc.setLatitude(lat);
			loc.setLongitude(lng);
			if(llAcc == null)
			{
				llAcc = 100.0;
			}
			loc.setAccuracy(Math.round(Math.round(llAcc)));
			
			Integer radius = 400;
			
			//guarantees a category
			VenueCategoryEnum category = VenueCategoryEnum.find(cat);
			
			VenueSearchResponse vsr;
			if(searchTerm != null && !searchTerm.isEmpty())
			{
				vsr = dao.search(loc, radius, searchTerm, 100, null, null);//browse(loc, radius, category.getFsqId());
			}
			else
			{
				vsr = dao.browse(loc, radius, category.getFsqId());	
			}
			
			
			List<VenueSummary> venues = VenueSummary.adaptVenueList(vsr);
			
			
			venues = cleanupVenues(venues, standards);
			
			
			
			doResponse(venues, response);
		}
		catch(Throwable t)
		{
			doError(t, response);
		}
		
		
	}
	
	/**
	 * It's a pun. This method removes venues that have too many healthCodeViolations
	 * 
	 * @param venues
	 * @param standards
	 * @return
	 */
	private static List<VenueSummary> cleanupVenues(List<VenueSummary> venues, String standards)
	{
//		log.info("Standards: "+standards);
		
		List<VenueSummary> cleanVenues = new ArrayList<VenueSummary>();
		
		if("3".equalsIgnoreCase(standards) || "2".equalsIgnoreCase(standards))
		{
//			log.info("User has Standards: "+standards);
				
			for(VenueSummary vs : venues)
			{
				if(vs.getHealthCodeViolations() == null)
				{
//					log.info("Adding ("+vs.getHealthCodeViolations()+"): "+vs.getName());
					
					cleanVenues.add(vs);
				}
				else if("3".equalsIgnoreCase(standards) && vs.getHealthCodeViolations() < 7 )
				{
//					log.info("Adding ("+vs.getHealthCodeViolations()+"): "+vs.getName());
					
					cleanVenues.add(vs);
				}
				else if("2".equalsIgnoreCase(standards) && vs.getHealthCodeViolations() < 18)
				{
//					log.info("Adding ("+vs.getHealthCodeViolations()+"): "+vs.getName());
					
					cleanVenues.add(vs);
				}
			}
		}
		else{
			return venues;
		}
		
		return cleanVenues;
	}
	
	
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		handleResponse(request, response);
	}
	/*
	 * (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doPost(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	@Override
	protected void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		handleResponse(request, response);
	}


	
	
}
