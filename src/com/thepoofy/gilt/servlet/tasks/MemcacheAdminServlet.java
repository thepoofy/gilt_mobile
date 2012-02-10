/**
 *
 */
package com.thepoofy.gilt.servlet.tasks;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thepoofy.gilt.api.DataSingleton;
import com.thepoofy.gilt.api.ProductMemcache;
import com.thepoofy.gilt.api.SaleMemcache;
import com.thepoofy.gilt.servlet.ServletBase;

/**
 * @author Willum
 *
 */
@SuppressWarnings("serial")
public class MemcacheAdminServlet extends ServletBase
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
			ProductMemcache productCache = DataSingleton.INSTANCE.getProductCache();
			SaleMemcache saleCache = DataSingleton.INSTANCE.getSaleCache();

			Map mem = new HashMap();

			mem.put("products", productCache.getCache());
			mem.put("sales", saleCache.getCache());
			
			doResponse(mem, response);
		}
		catch(Throwable t)
		{
			doError(t, response);
		}
	}

}
