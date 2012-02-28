package com.thepoofy.gilt.api;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.google.appengine.api.memcache.MemcacheService;
import com.google.appengine.api.memcache.MemcacheServiceFactory;

/**
 *
 * @author wvanderhoef
 *
 * @param <K>
 * @param <V>
 */
public abstract class AbstractMemcache<K,V>
{
	private Map<K, V> cacheMap = new ConcurrentHashMap<K, V>();

	private static final Logger log = Logger.getLogger(AbstractMemcache.class.getName());

	/**
	 *
	 * @return the product cache
	 */
	public Map<K, V> getCache()
	{
		return cacheMap;
	}

	/**
	 *
	 * @param key
	 * @param value
	 */
	public void onUpdate(K key, V value)
	{
		cacheMap.put(key, value);

		// Using the synchronous cache
		MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();

		syncCache.put(key, value);
	}

	/**
	 *
	 * @param key
	 * @return Product
	 */
	public V getLatest(K key)
	{
		//save us from a Memcache hit if this instance has already fetched data
		V deets = cacheMap.get(key);
		if(deets != null)
		{
			return deets;
		}


		try
		{
			MemcacheService syncCache = MemcacheServiceFactory.getMemcacheService();

			V cachedData = (V)syncCache.get(key);

			if(cachedData == null)
			{
				//get fresh data.
				return forceUpdate(key);
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
	 * @param key
	 * @return Value
	 * @throws GiltApiException
	 */
	public abstract V forceUpdate(K key) throws GiltApiException;
//	{
//		V freshData = GiltApi.fetchProduct(key);
//
//		onUpdate(freshData.getProduct(), freshData);
//
//		return freshData;
//
//	}

//	/**
//	 *
//	 * @param productUrl
//	 * @return Product
//	 * @throws GiltApiException
//	 */
//	public Product forceUpdate(String productUrl) throws GiltApiException
//	{
//		Product freshData = GiltApi.fetchProduct(productUrl);
//
//		onUpdate(productUrl, freshData);
//
//		return freshData;
//
//	}

}
