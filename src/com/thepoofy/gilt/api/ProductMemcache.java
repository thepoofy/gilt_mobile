package com.thepoofy.gilt.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.williamvanderhoef.gilt.model.Product;

/**
 *
 * @author wvanderhoef
 *
 */
public class ProductMemcache
{
	private Map<String, Product> cacheMap = new ConcurrentHashMap<String, Product>();

	private static final Logger log = Logger.getLogger(ProductMemcache.class.getName());

	public Map<String, Product> getCache()
	{
		return cacheMap;
	}

	/**
	 *
	 * @param productUrl
	 * @param deets
	 */
	public void onUpdate(String productUrl, Product deets)
	{
		cacheMap.put(productUrl, deets);

		// Using the synchronous cache
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();

		syncCache.put(productUrl, deets);
	}

	/**
	 *
	 * @param productUrl
	 * @return Product
	 */
	public Product getLatest(String productUrl)
	{
		//save us from a Memcache hit if this instance has already fetched data
		Product deets = cacheMap.get(productUrl);
		if(deets != null)
		{
			return deets;
		}


		try
		{
			MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();

			Product cachedData = (Product)syncCache.get(productUrl);

			if(cachedData == null)
			{
				//get fresh data.
				return forceUpdate(productUrl);
			}
			else
			{
				return cachedData;
			}
		}
		catch(GiltApiException e)
		{
			log.log(Level.WARNING, e.getMessage(), e);
			e.printStackTrace();
			return null;
		}
	}

	/**
	 *
	 * @param productId
	 * @return Product
	 * @throws GiltApiException
	 */
	public Product forceUpdate(Number productId) throws GiltApiException
	{
		Product freshData = GiltApi.fetchProduct(productId);

		onUpdate(freshData.getProduct(), freshData);

		return freshData;

	}

	/**
	 *
	 * @param productUrl
	 * @return Product
	 * @throws GiltApiException
	 */
	public Product forceUpdate(String productUrl) throws GiltApiException
	{
		Product freshData = GiltApi.fetchProduct(productUrl);

		onUpdate(productUrl, freshData);

		return freshData;

	}

}
