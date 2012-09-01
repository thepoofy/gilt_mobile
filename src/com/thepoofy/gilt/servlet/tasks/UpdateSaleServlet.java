/**
 *
 */
package com.thepoofy.gilt.servlet.tasks;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.google.appengine.api.taskqueue.Queue;
import com.google.appengine.api.taskqueue.QueueFactory;
import com.google.appengine.api.taskqueue.TaskOptions;
import com.thepoofy.gilt.GiltProperty;
import com.thepoofy.gilt.api.DataSingleton;
import com.thepoofy.gilt.api.SaleMemcache;
import com.thepoofy.gilt.servlet.ServletBase;
import com.williamvanderhoef.gilt.model.Sale;

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

			SaleMemcache cache = DataSingleton.INSTANCE.getSaleCache();

			List<Sale> sales = cache.forceUpdate(GiltProperty.valueOf(propertyName));

			if(sales != null)
			{
				for(Sale sale : sales)
				{
					if(sale.getProducts() != null)
					{
						for(String productUrl : sale.getProducts())
						{
							Queue queue = QueueFactory.getDefaultQueue();
							queue.add(TaskOptions.Builder.withUrl("/tasks/update_product").param("product_url", productUrl));
						}
					}
				}
			}

			doResponse(true, response);
		}
		catch(Throwable t)
		{
			doError(t, response);
		}
	}

}
