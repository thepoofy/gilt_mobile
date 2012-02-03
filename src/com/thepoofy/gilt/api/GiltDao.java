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

import com.thepoofy.gilt.ClothingCategory;
import com.thepoofy.gilt.model.BrandCount;
import com.thepoofy.gilt.model.CategoryCount;
import com.williamvanderhoef.gilt.model.Product;
import com.williamvanderhoef.gilt.model.Sale;
import com.williamvanderhoef.gilt.model.Sku;

public class GiltDao {

	private List<BrandCount> brandsCount;
	private List<CategoryCount> categoriesCount;
	private final Map<ClothingCategory, List<Product>> categoryBuckets;

	public GiltDao(List<Sale> sales)
	{
		categoryBuckets = new EnumMap<ClothingCategory, List<Product>>(ClothingCategory.class);

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
			List<Product> products = s.getProducts();

			if(products != null)
			{
				for(Product p : products)
				{
					brandsLogic(p, brands);

					categoriesLogic(p, categories);
				}
			}
		}

		brandsCount = new ArrayList<BrandCount>();
		brandsCount.addAll(brands.values());

		categoriesCount = new ArrayList<CategoryCount>();
		categoriesCount.addAll(categories.values());

	}

	private void brandsLogic(Product p, Map<String, BrandCount> brands)
	{
		String brand = p.getBrand();

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

	private void categoriesLogic(Product p, Map<String, CategoryCount> categories)
	{
		for(ClothingCategory cat : ClothingCategory.values())
		{
			if((matches(cat.searchText, p.getName())))
//				|| (p.getContent() != null
//					&& matches(cat.searchText, p.getContent().getDescription())))
			{
				CategoryCount bc = null;

				if(!categories.containsKey(cat.name))
				{
					bc = new CategoryCount(cat.name, p.getImageUrls().get("91x121").get(0));

				}
				else if(categories.containsKey(cat.name))
				{
					bc = categories.get(cat.name);
				}

				++bc.count;

				addProductToBucket(cat, p);


				for(Sku sku : p.getSkus())
				{
					try
					{
						NumberFormat format = NumberFormat.getInstance();
						Number skuPrice = NumberFormat.getInstance().parse(sku.getSalePrice());

						if(bc.getMinPrice() != null)
						{
							Number minPrice = format.parse(bc.getMinPrice());

							if(skuPrice.doubleValue() < minPrice.doubleValue())
							{
								bc.setMinPrice(sku.getSalePrice());
							}
						}
						else
						{
							bc.setMinPrice(sku.getSalePrice());
						}
					}
					catch(ParseException e)
					{
						e.printStackTrace(System.err);
					}
				}

				categories.put(cat.name, bc);

				//return here to prevent a product from falling into a catch-all category
				return;
			}
		}
	}


	private void addProductToBucket(ClothingCategory cat, Product p)
	{
		List<Product> products ;
		if(categoryBuckets.containsKey(cat))
		{
			products = categoryBuckets.get(cat);
		}
		else
		{
			products = new ArrayList<Product>();
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
					System.out.println("Word: "+key+" found in '"+text+"'");
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
	 * @return the categoryBuckets
	 */
	public List<Product> getCategoryBuckets(ClothingCategory category) {
		return categoryBuckets.get(category);
	}
}
