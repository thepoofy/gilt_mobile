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
import com.williamvanderhoef.gilt.responses.SalesResponse;

/**
 *
 * @author wvanderhoef
 *
 */
public class SaleMemcache
{
	private Map<GiltProperty, SalesResponse> saleMap = new EnumMap<GiltProperty, SalesResponse>(GiltProperty.class);

	private static final Logger log = Logger.getLogger(SaleMemcache.class.getName());



	/**
	 *
	 * @param saleProperty
	 * @param sales
	 */
	public void onSaleUpdate(GiltProperty saleProperty, SalesResponse sales)
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
		List<Sale> inMemorySales = saleMap.get(saleProperty).getSales();
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
		SalesResponse freshData = GiltApi.fetchSales(saleType);

		onSaleUpdate(saleType, freshData);

		return freshData.getSales();

	}
}
