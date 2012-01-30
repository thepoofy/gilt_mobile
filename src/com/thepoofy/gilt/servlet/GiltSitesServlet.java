/**
 *
 */
package com.thepoofy.gilt.servlet;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thepoofy.gilt.GiltProperty;

/**
 * @author Willum
 *
 */
@SuppressWarnings("serial")
public class GiltSitesServlet extends ServletBase
{
//	private static final Logger log = Logger.getLogger(GiltPropertiesServlet.class.getName());

	/*
	 * (non-Javadoc)
	 * @see com.thepoofy.gilt.servlet.ServletBase#handleResponse(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	protected void handleResponse(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		try
		{
			doResponse(GiltProperty.values(), response);
		}
		catch(Throwable t)
		{
			doError(t, response);
		}
	}
}
