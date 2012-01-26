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

import com.thepoofy.gilt.ClothingCategory;
import com.thepoofy.gilt.GiltProperty;
import com.thepoofy.gilt.api.DataSingleton;
import com.thepoofy.gilt.api.GiltDao;
import com.thepoofy.gilt.api.SaleMemcache;
import com.williamvanderhoef.gilt.model.Sale;

/**
 * @author Willum
 *
 */
@SuppressWarnings("serial")
public class ProductsServlet extends ServletBase
{
	private static final Logger log = Logger.getLogger(ProductsServlet.class.getName());

	protected void handleResponse(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		try
		{
			String cat = getParameter(request, "category", true);

			//guarantees a category
			ClothingCategory category = ClothingCategory.find(cat);

			SaleMemcache cache = DataSingleton.INSTANCE.getCache();
			List<Sale> sales = cache.getLatest(GiltProperty.MEN);

			GiltDao dao = new GiltDao(sales);

			doResponse(dao.getCategoryBuckets(category), response);
		}
		catch(Throwable t)
		{
			doError(t, response);
		}
	}
}
