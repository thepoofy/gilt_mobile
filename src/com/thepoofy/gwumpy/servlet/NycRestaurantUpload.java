/**
 * 
 */
package com.thepoofy.gwumpy.servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.type.TypeReference;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.thepoofy.gwumpy.model.NycInspectionGrade;
import com.thepoofy.gwumpy.nyc.NycInspectionApi;

/**
 * @author Willum
 *
 */
@SuppressWarnings("serial")
public class NycRestaurantUpload extends ServletBase
{
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
			String inspectionRecord = getParameter(request, "inspection", false);
			
			ObjectMapper mapper = new ObjectMapper();
			
			TypeReference<List<NycInspectionGrade>> typeRef = new TypeReference<List<NycInspectionGrade>>() {}; 
	               
			List<NycInspectionGrade> grades = mapper.readValue(inspectionRecord, typeRef);
			
			if(grades != null)
			{
				for(NycInspectionGrade grade : grades)
				{
					NycInspectionApi.saveGrade(grade);
					
					//queue up the task to update the lat/lng info.
					Queue queue = QueueFactory.getDefaultQueue();
					queue.add(TaskOptions.Builder.withUrl("/tasks/updateNycGradeLocation").param("camis", grade.getCamis()));
					
				}
			}
			
			doResponse(grades, response);
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
