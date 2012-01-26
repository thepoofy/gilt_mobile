/**
 * 
 */
package com.thepoofy.gilt.servlet;

import java.io.IOException;
import java.util.List;
import java.util.logging.Logger;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thepoofy.gilt.api.GiltDao;
import com.thepoofy.gilt.api.GiltProperty;
import com.thepoofy.gilt.api.SaleMemcache;
import com.williamvanderhoef.gilt.model.Sale;

/**
 * @author Willum
 *
 */
@SuppressWarnings("serial")
public class BrandsServlet<T> extends ServletBase
{
	private static final Logger log = Logger.getLogger(BrandsServlet.class.getName());
	
	protected void handleResponse(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		try
		{
			SaleMemcache cache = new SaleMemcache();
			List<Sale> sales = cache.getLatest(GiltProperty.MEN);
			
			GiltDao dao = new GiltDao(sales);
			
			doResponse(dao.getBrandsCount(), response);
		}
		catch(Throwable t)
		{
			doError(t, response);
		}
	}
}
