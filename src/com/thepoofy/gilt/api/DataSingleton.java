package com.thepoofy.gilt.api;

/**
 *
 * @author wvanderhoef
 *
 */
public enum DataSingleton {

	/** */INSTANCE;

	private SaleMemcache saleCache;
	private ProductMemcache productCache;

	private DataSingleton(){
		saleCache = new SaleMemcache();
		productCache = new ProductMemcache();
	}

	/**
	 *
	 * @return memcache
	 */
	public SaleMemcache getSaleCache()
	{
		return saleCache;
	}

	/**
	 *
	 * @return memcache
	 */
	public ProductMemcache getProductCache()
	{
		return productCache;
	}
}
