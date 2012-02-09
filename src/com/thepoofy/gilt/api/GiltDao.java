package com.thepoofy.gilt.api;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

import com.thepoofy.gilt.ClothingCategory;
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

	private List<BrandCount> brandsCount;
	private List<CategoryCount> categoriesCount;
	private final Map<ClothingCategory, List<ProductDetails>> categoryBuckets;

	/**
	 *
	 * @param sales
	 */
	public GiltDao(List<Sale> sales)
	{
		categoryBuckets = new EnumMap<ClothingCategory, List<ProductDetails>>(ClothingCategory.class);

		init(sales);

		Collections.sort(brandsCount);
		Collections.sort(categoriesCount);
	}



	private void init(List<Sale> sales){

		Map<String, BrandCount> brands = new LinkedHashMap<String, BrandCount>();
		Map<String, CategoryCount> categories = new LinkedHashMap<String, CategoryCount>();


		if(sales == null)
		{
			brandsCount = Collections.emptyList();
			categoriesCount = Collections.emptyList();
		}

		if(sales == null)
		{
			return;
		}

		for(Sale s : sales)
		{
			List<String> productUrls = s.getProducts();

			if(productUrls != null)
			{
				for(String productUrl : productUrls)
				{
					Product p = DataSingleton.INSTANCE.getProductCache().getLatest(productUrl);

					ProductDetails pd = ProductDetails.valueOf(p);

					if(pd != null)
					{
						brandsLogic(pd, brands);

						categoriesLogic(pd, categories);
					}
				}
			}
		}

		brandsCount = new ArrayList<BrandCount>();
		brandsCount.addAll(brands.values());

		categoriesCount = new ArrayList<CategoryCount>();
		categoriesCount.addAll(categories.values());

	}

	private void brandsLogic(ProductDetails p, Map<String, BrandCount> brands)
	{
		String brand = p.getBrandName();

		if(brand != null && !brands.containsKey(brand))
		{
			BrandCount bc = new BrandCount(brand);
			++bc.count;

			brands.put(brand, bc);
		}
		else if(brand != null && brands.containsKey(brand))
		{
			BrandCount bc = brands.get(brand);
			++bc.count;

			brands.put(brand, bc);
		}
	}

	private void categoriesLogic(ProductDetails p, Map<String, CategoryCount> categories)
	{
		for(ClothingCategory cat : ClothingCategory.values())
		{
			if((matches(cat.searchText, p.getProductName())))
			{
				CategoryCount bc = null;

				if(!categories.containsKey(cat.name))
				{
					bc = new CategoryCount(cat.name, p.getImageUrl());
				}
				else if(categories.containsKey(cat.name))
				{
					bc = categories.get(cat.name);
				}

				++bc.count;

				addProductToBucket(cat, p);

				if(bc.getMinPrice() == null && p.getMinPrice() != null)
				{
					bc.setMinPrice(p.getMinPrice());
				}

				try
				{
					NumberFormat format = NumberFormat.getInstance();
					Number minPrice = NumberFormat.getInstance().parse(p.getMinPrice());

					//minimum price logic
					if(bc.getMinPrice() != null)
					{
						Number categoryMinPrice = format.parse(bc.getMinPrice());

						if(minPrice.doubleValue() < categoryMinPrice.doubleValue())
						{
							bc.setMinPrice(p.getMinPrice());
						}
					}
				}
				catch(ParseException e)
				{
					log.log(Level.WARNING, e.getMessage(), e);
				}

				categories.put(cat.name, bc);

				//return here to prevent a product from falling into a catch-all category
				return;
			}
		}
	}


	private void addProductToBucket(ClothingCategory cat, ProductDetails p)
	{
		List<ProductDetails> products ;
		if(categoryBuckets.containsKey(cat))
		{
			products = categoryBuckets.get(cat);
		}
		else
		{
			products = new ArrayList<ProductDetails>();
		}

		products.add(p);

		categoryBuckets.put(cat, products);
	}


	private static boolean matches(List<String> keys, String text)
	{
		String[] words = text.split("\\s|\\.");

		for(String word : words)
		{
			for(String key : keys)
			{
				if(key.equals(word.toLowerCase()))
				{
					return true;
				}
			}
		}

		return false;
	}

	/**
	 * @return the brandsCount
	 */
	public Collection<BrandCount> getBrandsCount() {
		return brandsCount;
	}
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
	public List<ProductDetails> getCategoryBuckets(ClothingCategory category) {
		return categoryBuckets.get(category);
	}
}
