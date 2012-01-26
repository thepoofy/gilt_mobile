/**
 * 
 */
package com.thepoofy.gilt.servlet.tasks;

import java.io.IOException;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thepoofy.gilt.GiltProperty;
import com.thepoofy.gilt.api.SaleMemcache;
import com.thepoofy.gilt.servlet.ServletBase;

/**
 * @author Willum
 *
 */
@SuppressWarnings("serial")
public class UpdateSalesServlet extends ServletBase
{
	private static final Logger log = Logger.getLogger(UpdateSalesServlet.class.getName());
	
	
	/**
	 * @param request
	 * @param response
	 * @throws IOException
	 * @throws ServletException
	 */
	protected void handleResponse(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		try
		{
			SaleMemcache cache = new SaleMemcache();
			cache.forceUpdate(GiltProperty.MEN);
			
			doResponse(null, response);
		}
		catch(Throwable t)
		{
			doError(t, response);
		}	
	}
	
}
