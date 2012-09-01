package com.thepoofy.gilt.api;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.thepoofy.gilt.model.BrandCount;
import com.thepoofy.gilt.model.CategoryCount;
import com.thepoofy.gilt.model.ProductDetails;
import com.williamvanderhoef.gilt.model.Product;
import com.williamvanderhoef.gilt.model.Sale;

/**
 *
 * @author wvanderhoef
 *
 */
public class GiltDao {

	private static final Logger log = Logger.getLogger(GiltDao.class.getName());

//	private List<BrandCount> brandsCount;
	private List<CategoryCount> categoriesCount;
	private final Map<String, List<ProductDetails>> categoryBuckets;

	/**
	 *
	 * @param sales
	 */
	public GiltDao(List<Sale> sales)
	{
		categoryBuckets = new HashMap<String, List<ProductDetails>>();

		init(sales);

//		Collections.sort(brandsCount);
		Collections.sort(categoriesCount);
	}



	private void init(List<Sale> sales){

		//Map<String, BrandCount> brands = new LinkedHashMap<String, BrandCount>();
		Map<String, CategoryCount> categories = new LinkedHashMap<String, CategoryCount>();


		if(sales == null)
		{
//			brandsCount = Collections.emptyList();
			categoriesCount = Collections.emptyList();
		}

		if(sales == null)
		{
			return;
		}

		log.info("Found "+sales.size()+ " sales");

		for(Sale s : sales)
		{
			List<String> productUrls = s.getProducts();


			if(productUrls != null)
			{
				log.info("Sale: "+s.getName()+ " has "+productUrls.size()+" products.");

				for(String productUrl : productUrls)
				{
					Product p = DataSingleton.INSTANCE.getProductCache().getLatest(productUrl);

					if(p!=null)
					{
						ProductDetails pd = ProductDetails.valueOf(p);
						
						if(pd != null)
						{
							//brandsLogic(pd, brands);
						    
							categoriesLogic(pd, p.getCategories(), categories);
						}
					}
				}
			}
			else
			{
				log.info("Sale: "+s.getName()+ " has no products.");
			}

		}

//		brandsCount = new ArrayList<BrandCount>();
//		brandsCount.addAll(brands.values());

		categoriesCount = new ArrayList<CategoryCount>();
		categoriesCount.addAll(categories.values());

	}

//	private void brandsLogic(ProductDetails p, Map<String, BrandCount> brands)
//	{
//		String brand = p.getBrandName();
//
//		if(brand != null && !brands.containsKey(brand))
//		{
//			BrandCount bc = new BrandCount(brand);
//			++bc.count;
//
//			brands.put(brand, bc);
//		}
//		else if(brand != null && brands.containsKey(brand))
//		{
//			BrandCount bc = brands.get(brand);
//			++bc.count;
//
//			brands.put(brand, bc);
//		}
//	}

	private void categoriesLogic(ProductDetails productDetails, List<String> categories, Map<String, CategoryCount> categoryCount)
	{
	    for(String cat : categories)
		{
	        //TODO add logic to restrict certain categories here.  
	        //duplicate and generic categories are annoying.
	        
			addToCategory(categoryCount, cat, productDetails);			
		}
	}


	private void addToCategory(Map<String, CategoryCount> categories, String category, ProductDetails productDetails)
	{
		CategoryCount categoryCount = null;

		if(!categories.containsKey(category))
		{
			categoryCount = new CategoryCount(category, productDetails.getImageUrl());
		}
		else if(categories.containsKey(category))
		{
			categoryCount = categories.get(category);
		}

		++categoryCount.count;

		addProductToBucket(category, productDetails);
//
		if(categoryCount.getMinPrice() == null && productDetails.getMinPrice() != null)
		{
			categoryCount.setMinPrice(productDetails.getMinPrice());
		}

		try
		{
			NumberFormat format = NumberFormat.getInstance();
			Number minPrice = NumberFormat.getInstance().parse(productDetails.getMinPrice());

			//minimum price logic
			if(categoryCount.getMinPrice() != null)
			{
				Number categoryMinPrice = format.parse(categoryCount.getMinPrice());

				if(minPrice.doubleValue() < categoryMinPrice.doubleValue())
				{
					categoryCount.setMinPrice(productDetails.getMinPrice());
				}
			}
		}
		catch(ParseException e)
		{
			log.log(Level.WARNING, e.getMessage(), e);
		}

		categories.put(category, categoryCount);
	}


	private void addProductToBucket(String category, ProductDetails p)
	{
		List<ProductDetails> products ;
		if(categoryBuckets.containsKey(category))
		{
			products = categoryBuckets.get(category);
		}
		else
		{
			products = new ArrayList<ProductDetails>();
		}

		products.add(p);

		categoryBuckets.put(category, products);
	}


	private static boolean matches(List<String> keys, String text)
	{

		for(String k : keys)
		{
			if(text.toLowerCase().contains(k))
			{
				return true;
			}
		}

//		String[] words = text.split("\\s|\\.");
//
//		for(String word : words)
//		{
//			for(String key : keys)
//			{
//				if(key.equals(word.toLowerCase()))
//				{
//					return true;
//				}
//			}
//		}

		return false;
	}

//	/**
//	 * @return the brandsCount
//	 */
//	public Collection<BrandCount> getBrandsCount() {
//		return brandsCount;
//	}
	/**
	 * @return the categoriesCount
	 */
	public Collection<CategoryCount> getCategoriesCount() {
		return categoriesCount;
	}
	/**
	 *
	 * @param category
	 * @return list of products
	 */
	public List<ProductDetails> getCategoryBuckets(String category) {
		return categoryBuckets.get(category);
	}
}
