/**
 *
 */
package com.thepoofy.gilt.servlet;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.thepoofy.gilt.ClothingCategory;
import com.thepoofy.gilt.GiltProperty;
import com.thepoofy.gilt.api.DataSingleton;
import com.thepoofy.gilt.api.GiltDao;
import com.thepoofy.gilt.api.SaleMemcache;
import com.thepoofy.gilt.model.ProductDetails;
import com.williamvanderhoef.gilt.model.Sale;

/**
 * @author Willum
 *
 */
@SuppressWarnings("serial")
public class ProductsServlet extends ServletBase
{
//	private static final Logger log = Logger.getLogger(ProductsServlet.class.getName());

	protected void handleResponse(HttpServletRequest request,HttpServletResponse response) throws IOException, ServletException
	{
		try
		{
			String siteName = getParameter(request, "site", true);
			String cat = getParameter(request, "category", true);

			//guarantees a category
			ClothingCategory category = ClothingCategory.find(cat);

			SaleMemcache cache = DataSingleton.INSTANCE.getSaleCache();
			List<Sale> sales = cache.getLatest(GiltProperty.valueOf(siteName));

			GiltDao dao = new GiltDao(sales);

			List<ProductDetails> products = dao.getCategoryBuckets(category);

			Collections.sort(products);

			doResponse(products, response);
		}
		catch(Throwable t)
		{
			doError(t, response);
		}
	}
}
