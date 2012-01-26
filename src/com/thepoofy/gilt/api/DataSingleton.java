package com.thepoofy.gilt.api;

import com.thepoofy.gilt.GiltProperty;

public enum DataSingleton {

	INSTANCE;

	private SaleMemcache cache;

	private DataSingleton(){
		cache = new SaleMemcache();

		cache.forceUpdate(GiltProperty.MEN);
	}

	/**
	 *
	 * @return
	 */
	public SaleMemcache getCache()
	{
		return cache;
	}
}
