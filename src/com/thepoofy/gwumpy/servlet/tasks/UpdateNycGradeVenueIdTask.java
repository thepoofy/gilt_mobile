/**
 * 
 */
package com.thepoofy.gwumpy.servlet.tasks;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thepoofy.ca.dao.foursquare.VenuesDao;
import com.thepoofy.gwumpy.dao.GwumpyVenueLinkApi;
import com.thepoofy.gwumpy.model.NycInspectionGrade;
import com.thepoofy.gwumpy.nyc.NycInspectionApi;
import com.thepoofy.gwumpy.servlet.ServletBase;
import com.thepoofy.util.location.Location;
import com.williamvanderhoef.foursquare.model.Venue;
import com.williamvanderhoef.foursquare.responses.VenueSearchResponse;

/**
 * @author Willum
 *
 */
@SuppressWarnings("serial")
public class UpdateNycGradeVenueIdTask extends ServletBase
{
	private static final Logger log = Logger.getLogger(UpdateNycGradeVenueIdTask.class.getName());
	
	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	private void handleResponse(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		try
		{
			String inspectionRecord = getParameter(request, "camis", false);
			
			NycInspectionGrade grade = NycInspectionApi.findGrade(inspectionRecord);
			
			VenuesDao vDao = new VenuesDao();
			
			Location loc = new Location();
			loc.setLatitude(grade.getLatitude());
			loc.setLongitude(grade.getLongitude());
			
			VenueSearchResponse searchResponse = vDao.match(loc, grade.getDba());
			
			if(searchResponse== null || searchResponse.getVenues() == null )
			{
				log.info("VenueSearchResponse was null for "+grade.getDba()+" : "+grade.getCamis());

			}
			else if(!searchResponse.getVenues().isEmpty())
			{
				Venue v = searchResponse.getVenues().get(0);
				
				GwumpyVenueLinkApi.updateNycInspectionGrade(v, grade);
			}
			else if(searchResponse.getVenues().isEmpty()){
				log.info("VenueSearchResponse contained no results for "+grade.getDba()+" : "+grade.getCamis());
			}
			
			
			doResponse(null, response);
		}
		catch(Throwable t)
		{
			doError(t, response);
		}	
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
