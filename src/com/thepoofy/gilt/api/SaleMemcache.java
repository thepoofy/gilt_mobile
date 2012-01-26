package com.thepoofy.gilt.api;

import java.util.EnumMap;
import java.util.List;
import java.util.Map;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;
import com.thepoofy.gilt.GiltProperty;
import com.williamvanderhoef.gilt.model.Sale;

public class SaleMemcache
{
	private Map<GiltProperty, List<Sale>> saleMap = new EnumMap<GiltProperty, List<Sale>>(GiltProperty.class);

	public void onSaleUpdate(GiltProperty saleProperty, List<Sale> sales)
	{
		saleMap.put(saleProperty, sales);
		
		//TODO reenable the memcache
		// Using the synchronous cache
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		
		syncCache.put(saleProperty, sales);
	}
	
	/**
	 * 
	 * @param saleProperty
	 * @return
	 */
	public List<Sale> getLatest(GiltProperty saleProperty)
	{
		//save us from a Memcache hit if this instance has already fetched data
		List<Sale> inMemorySales = saleMap.get(saleProperty);
		if(inMemorySales != null)
		{
			return inMemorySales;
		}
		
		
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();
		
		@SuppressWarnings("unchecked")
		List<Sale> cachedData = (List<Sale>)syncCache.get(saleProperty);
		
		if(cachedData == null)
		{
			//get fresh data.
			return forceUpdate(saleProperty);
		}
		else
		{
			return cachedData;
		}
	}
	
	public List<Sale> forceUpdate(GiltProperty saleType)
	{
		try
		{
			List<Sale> freshData = GiltApi.fetchSales();
			
			onSaleUpdate(saleType, freshData);
			
			return freshData;
		}
		catch(Exception e)
		{
			e.printStackTrace();
//			WootLogger.severe(e);
		}
		
		return null;
	}
	
//	
//	public List<Sale> getLatest()
//	{
//		List<WootRssData> allLatestWoots = new ArrayList<WootRssData>();
//		
//		for(WootEnum woot: WootEnum.values())
//		{
//			allLatestWoots.add(getLatest(woot));
//		}
//		
//		return allLatestWoots;
//	}
}