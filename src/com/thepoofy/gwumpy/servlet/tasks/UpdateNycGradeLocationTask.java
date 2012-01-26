/**
 * 
 */
package com.thepoofy.gwumpy.servlet.tasks;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.thepoofy.gwumpy.dao.DatastoreObjectNotFoundException;
import com.thepoofy.gwumpy.geocoder.GeoApi;
import com.thepoofy.gwumpy.model.NycInspectionGrade;
import com.thepoofy.gwumpy.nyc.NycInspectionApi;
import com.thepoofy.gwumpy.servlet.ServletBase;
import com.thepoofy.util.location.Location;

/**
 * @author Willum
 *
 */
@SuppressWarnings("serial")
public class UpdateNycGradeLocationTask extends ServletBase
{
	private static final Logger log = Logger.getLogger(UpdateNycGradeLocationTask.class.getName());
	
	
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
			
			try
			{
				NycInspectionGrade grade = NycInspectionApi.findGrade(inspectionRecord);

				Location loc = GeoApi.geocode(grade);
				
				
				if(loc != null)
				{
					NycInspectionApi.updateLocation(grade, loc);
					
					Queue queue = QueueFactory.getDefaultQueue();
					queue.add(TaskOptions.Builder.withUrl("/tasks/updateNycGradeVenueId").param("camis", grade.getCamis()));
				}
				else
				{
					log.warning("No location info available for "+grade.getDba()+" : "+grade.getCamis());
				}
			}
			catch(DatastoreObjectNotFoundException e)
			{
				e.printStackTrace();
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
