/**
 * 
 */
package com.thepoofy.gwumpy.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thepoofy.ca.dao.foursquare.VenuesDao;
import com.thepoofy.gwumpy.dao.GwumpyVenueLinkApi;
import com.thepoofy.gwumpy.model.NycInspectionGrade;
import com.thepoofy.gwumpy.model.VenueSummary;
import com.thepoofy.gwumpy.nyc.NycInspectionApi;
import com.williamvanderhoef.foursquare.model.VenueDetails;
import com.williamvanderhoef.foursquare.model.VenueExternalLink;
import com.williamvanderhoef.foursquare.responses.VenueLinksResponse;
import com.williamvanderhoef.foursquare.responses.VenueResponse;

/**
 * @author Willum
 *
 */
@SuppressWarnings("serial")
public class GwumpyDetails extends ServletBase
{
	/**
	 * 
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleResponse(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		try
		{
			VenuesDao dao = new VenuesDao(null);
			
			String fsqId = getParameter(request, "fsqId", true);
			
			VenueResponse venueResponse = dao.getDetails(fsqId);
			VenueDetails vd = venueResponse.getVenue();
			
			VenueSummary vs = VenueSummary.valueOf(vd);
			
			
			
			NycInspectionGrade restaurantGrade = GwumpyVenueLinkApi.findGrade(vd);
			
			
			vs.setHealthCodeRating(restaurantGrade.getCurrentGrade()+"("+restaurantGrade.getScore()+"*)");
			
			
			VenueLinksResponse linksResponse = dao.links(fsqId);
			
			
			if(linksResponse != null && linksResponse.getLinks() != null)
			{
				for(VenueExternalLink links : linksResponse.getLinks())
				{
					if("nyt".equalsIgnoreCase(links.getProvider().getId()))
					{
						vs.setNytimesReview(links.getUrl());
					}
					else if("allmenu".equalsIgnoreCase(links.getProvider().getId()))
					{
						vs.setAllMenuUrl(links.getUrl()+"menu/");
					}
					else
					{
						System.out.println(links.getProvider().getId());
					}
				}
			}
			else
			{
				System.out.println(linksResponse);
				System.out.println(linksResponse.getLinks().getCount() > 0);
			}
			
			
			
//			List<VenueSummary> venues = VenueSummary.adaptVenueList(vsr);
			
			doResponse(vs, response);
		}
		catch(Throwable t)
		{
			doError(t, response);
		}
	}
	
	public void doGet(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		handleResponse(request, response);
	}
	public void doPost(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		handleResponse(request, response);
	}
}
