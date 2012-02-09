package com.thepoofy.gilt.api;

import java.util.Collections;
import java.util.EnumMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.thepoofy.gilt.GiltProperty;
import com.williamvanderhoef.gilt.model.Sale;

/**
 *
 * @author wvanderhoef
 *
 */
public class SaleMemcache
{
	private Map<GiltProperty, List<Sale>> saleMap = new EnumMap<GiltProperty, List<Sale>>(GiltProperty.class);

	private static final Logger log = Logger.getLogger(SaleMemcache.class.getName());



	/**
	 *
	 * @param saleProperty
	 * @param sales
	 */
	public void onSaleUpdate(GiltProperty saleProperty, List<Sale> sales)
	{
		saleMap.put(saleProperty, sales);

		// Using the synchronous cache
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();

		syncCache.put(saleProperty, sales);
	}

	/**
	 *
	 * @param saleProperty
	 * @return sales
	 */
	public List<Sale> getLatest(GiltProperty saleProperty)
	{
		//save us from a Memcache hit if this instance has already fetched data
		List<Sale> inMemorySales = saleMap.get(saleProperty);
		if(inMemorySales != null && !inMemorySales.isEmpty())
		{
			return inMemorySales;
		}


		try
		{
			MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();

			@SuppressWarnings("unchecked")
			List<Sale> cachedData = (List<Sale>)syncCache.get(saleProperty);

			if(cachedData == null || cachedData.isEmpty())
			{
				//get fresh data.
				return forceUpdate(saleProperty);
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
			return Collections.emptyList();
		}
	}

	/**
	 *
	 * @param saleType
	 * @return sales
	 * @throws GiltApiException
	 */
	public List<Sale> forceUpdate(GiltProperty saleType) throws GiltApiException
	{
		List<Sale> freshData = GiltApi.fetchSales(saleType);

		onSaleUpdate(saleType, freshData);

		return freshData;

	}
}
