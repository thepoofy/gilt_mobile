package com.thepoofy.gilt.api;

import com.thepoofy.gilt.model.ProductDetails;
import com.williamvanderhoef.gilt.model.Product;

/**
 *
 * @author wvanderhoef
 *
 */
public class ProductDetailsMemcache extends AbstractMemcache<Number, ProductDetails>
{
//	private static final Logger log = Logger.getLogger(ProductDetailsMemcache.class.getName());

	/**
	 *
	 * @param productId
	 * @return Product
	 * @throws GiltApiException
	 */
	public ProductDetails forceUpdate(Number productId) throws GiltApiException
	{
		Product freshData = GiltApi.fetchProduct(productId);

		ProductDetails pd = ProductDetails.valueOf(freshData);

		this.onUpdate(pd.getId(), pd);

		return pd;
	}
}
