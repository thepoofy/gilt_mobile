package com.thepoofy.gilt.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;

import com.williamvanderhoef.gilt.model.Attribute;
import com.williamvanderhoef.gilt.model.Product;
import com.williamvanderhoef.gilt.model.Sku;

/**
 *
 * @author wvanderhoef
 *
 */
public class ProductDetails implements Comparable<ProductDetails>
{

	private Number id;
	private String name;
	private String imageUrl;
	private String brand;

	private String url;
	private String minPrice;
	private String maxPrice;

	private List<String> colors;
	private List<String> sizes;

	private boolean isSoldOut;


	/**
	 *
	 * @param p
	 * @return details
	 */
	public static ProductDetails valueOf(Product p)
	{
		ProductDetails pd = new ProductDetails();

		pd.setId(p.getId());
		pd.setBrand(p.getBrand());
		pd.setName(p.getName());

		pd.setImageUrl(findProductImage(p));

		pd.setUrl(p.getUrl());

		setSkuLevelStuff(p, pd);

		return pd;
	}

	private static void setSkuLevelStuff(Product p, ProductDetails pd)
	{
		boolean isSoldOut = true;
		List<String> colors = new ArrayList<String>();
		List<String> sizes = new ArrayList<String>();

		for(Sku sku : p.getSkus())
		{
			try
			{
				NumberFormat format = NumberFormat.getInstance();
				Number skuPrice = NumberFormat.getInstance().parse(sku.getSalePrice());

				//minimum price logic
				if(pd.getMinPrice() != null)
				{
					Number minPrice = format.parse(pd.getMinPrice());

					if(skuPrice.doubleValue() < minPrice.doubleValue())
					{
						pd.setMinPrice(sku.getSalePrice());
					}
				}
				else
				{
					pd.setMinPrice(sku.getSalePrice());
				}

				//maximum price logic
				if(pd.getMaxPrice() != null)
				{
					Number maxPrice = format.parse(pd.getMaxPrice());

					if(skuPrice.doubleValue() > maxPrice.doubleValue())
					{
						pd.setMaxPrice(sku.getSalePrice());
					}
				}
				else
				{
					pd.setMaxPrice(sku.getSalePrice());
				}
			}
			catch(ParseException e)
			{
				e.printStackTrace(System.err);
			}

			if("for sale".equalsIgnoreCase(sku.getInventoryStatus()))
			{
				isSoldOut = false;

				String color = getAttribute(sku.getAttributes(), "color");
				if(color != null)
				{
					colors.add(color);
				}
				String size = getAttribute(sku.getAttributes(), "size");
				if(size != null)
				{
					sizes.add(size);
				}
			}
			if("reserved".equalsIgnoreCase(sku.getInventoryStatus()))
			{
				isSoldOut = false;
			}
		}

		pd.setSoldOut(isSoldOut);
		pd.setSizes(sizes);
		pd.setColors(colors);
	}

	private static String getAttribute(List<Attribute> attributes, String attributeName)
	{
		if(attributes == null)
		{
			return null;
		}

		for(Attribute att : attributes)
		{
			if(attributeName.equalsIgnoreCase(att.getName()))
			{
				return att.getValue();
			}
		}

		return null;
	}

	private static final String SMALL_IMAGE_SIZE = "91x121";

	private static String findProductImage(Product p)
	{
		return p.getImageUrls().get(SMALL_IMAGE_SIZE).get(0).getUrl();
	}


	/**
	 * @return the imageUrl
	 */
	public String getImageUrl() {
		return imageUrl;
	}
	/**
	 * @param imageUrl the imageUrl to set
	 */
	public void setImageUrl(String imageUrl) {
		this.imageUrl = imageUrl;
	}
	/**
	 * @return the brandName
	 */
	public String getBrand() {
		return brand;
	}
	/**
	 * @param brandName the brandName to set
	 */
	public void setBrand(String brandName) {
		this.brand = brandName;
	}
	/**
	 * @return the productName
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setName(String productName) {
		this.name = productName;
	}
	/**
	 * @return the url
	 */
	public String getUrl() {
		return url;
	}
	/**
	 * @param url the url to set
	 */
	public void setUrl(String url) {
		this.url = url;
	}
	/**
	 * @return the minPrice
	 */
	public String getMinPrice() {
		return minPrice;
	}
	/**
	 * @param minPrice the minPrice to set
	 */
	public void setMinPrice(String minPrice) {
		this.minPrice = minPrice;
	}
	/**
	 * @return the maxPrice
	 */
	public String getMaxPrice() {
		return maxPrice;
	}
	/**
	 * @param maxPrice the maxPrice to set
	 */
	public void setMaxPrice(String maxPrice) {
		this.maxPrice = maxPrice;
	}
	/**
	 * @return the colors
	 */
	public List<String> getColors() {
		return colors;
	}
	/**
	 * @param colors the colors to set
	 */
	public void setColors(List<String> colors) {
		this.colors = colors;
	}
	/**
	 * @return the sizes
	 */
	public List<String> getSizes() {
		return sizes;
	}
	/**
	 * @param sizes the sizes to set
	 */
	public void setSizes(List<String> sizes) {
		this.sizes = sizes;
	}


	/**
	 * @return the id
	 */
	public Number getId() {
		return id;
	}


	/**
	 * @param id the id to set
	 */
	public void setId(Number id) {
		this.id = id;
	}

	/**
	 * @return the isSoldOut
	 */
	public boolean isSoldOut() {
		return isSoldOut;
	}

	/**
	 * @param isSoldOut the isSoldOut to set
	 */
	public void setSoldOut(boolean isSoldOut) {
		this.isSoldOut = isSoldOut;
	}


	/**
	 *
	 * @param other
	 * @return evaluation
	 */
	@Override
	public int compareTo(ProductDetails other) {

		if(other == null)
		{
			return -1;
		}

		if(other.isSoldOut() && !this.isSoldOut)
		{
			return -1;
		}

		Double minPrice = Double.parseDouble(this.minPrice);
		Double otherMinPrice = Double.parseDouble(other.getMinPrice());

		int diff = minPrice.compareTo(otherMinPrice);
		if(diff != 0){
			return diff;
		}

		return this.brand.compareTo(other.getBrand());
	}
}
