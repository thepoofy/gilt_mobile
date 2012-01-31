package com.thepoofy.gilt.api;


public enum DataSingleton {

	INSTANCE;

	private SaleMemcache cache;

	private DataSingleton(){
		cache = new SaleMemcache();
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
