package com.thepoofy.gilt.api;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.EnumMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import com.thepoofy.gilt.model.BrandCount;
import com.thepoofy.gilt.model.CategoryCount;
import com.thepoofy.gilt.model.ClothingCategory;
import com.williamvanderhoef.gilt.model.Product;
import com.williamvanderhoef.gilt.model.Sale;

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
			if((p.getName() != null 
					&& p.getName().contains(cat.searchText))
				||
				(p.getContent() != null
					&& p.getContent().getDescription() != null
					&& p.getContent().getDescription().contains(cat.searchText)))
			{
				if(!categories.containsKey(cat.searchText))
				{
					CategoryCount bc = new CategoryCount(cat.searchText, p.getImageUrls().get(0));
					++bc.count;
					
					addProductToBucket(cat, p);
					
					categories.put(cat.searchText, bc);
				}
				else if(categories.containsKey(cat.searchText))
				{
					CategoryCount bc = categories.get(cat.searchText);
					++bc.count;
					
					addProductToBucket(cat, p);
					
					categories.put(cat.searchText, bc);
				}
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
