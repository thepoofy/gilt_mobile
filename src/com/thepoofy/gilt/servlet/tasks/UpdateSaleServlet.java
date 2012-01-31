/**
 * 
 */
package com.thepoofy.gilt.servlet.tasks;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thepoofy.gilt.GiltProperty;
import com.thepoofy.gilt.api.DataSingleton;
import com.thepoofy.gilt.api.SaleMemcache;
import com.thepoofy.gilt.servlet.ServletBase;

/**
 * @author Willum
 *
 */
@SuppressWarnings("serial")
public class UpdateSaleServlet extends ServletBase
{
//	private static final Logger log = Logger.getLogger(UpdateSalesServlet.class.getName());
	
	
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
			String propertyName = getParameter(request, "prop", true);
			
			SaleMemcache cache = DataSingleton.INSTANCE.getCache();
			
			cache.forceUpdate(GiltProperty.valueOf(propertyName));
			
			doResponse(true, response);
		}
		catch(Throwable t)
		{
			doError(t, response);
		}	
	}
	
}
