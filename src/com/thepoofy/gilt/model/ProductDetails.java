package com.thepoofy.gilt.model;

import java.text.NumberFormat;
import java.text.ParseException;
import java.util.List;

import com.williamvanderhoef.gilt.model.Product;
import com.williamvanderhoef.gilt.model.Sku;

/**
 *
 * @author wvanderhoef
 *
 */
public class ProductDetails {

	private String imageUrl;
	private String brandName;
	private String productName;
	private String url;
	private String minPrice;
	private String maxPrice;

	private List<String> colors;
	private List<String> sizes;

	/**
	 *
	 * @param p
	 * @return details
	 */
	public static ProductDetails valueOf(Product p)
	{
		ProductDetails pd = new ProductDetails();

		pd.setBrandName(p.getBrand());
		pd.setProductName(p.getName());

		pd.setImageUrl(findProductImage(p));
//
//		for(Sku sku : p.getSkus())
//		{
//			try
//			{
//				NumberFormat format = NumberFormat.getInstance();
//				Number skuPrice = NumberFormat.getInstance().parse(sku.getSalePrice());
//
//				//minimum price logic
//				if(pd.getMinPrice() != null)
//				{
//					Number minPrice = format.parse(pd.getMinPrice());
//
//					if(skuPrice.doubleValue() < minPrice.doubleValue())
//					{
//						pd.setMinPrice(sku.getSalePrice());
//					}
//				}
//				else
//				{
//					pd.setMinPrice(sku.getSalePrice());
//				}
//
//				//maximum price logic
//				if(pd.getMaxPrice() != null)
//				{
//					Number maxPrice = format.parse(pd.getMaxPrice());
//
//					if(skuPrice.doubleValue() > maxPrice.doubleValue())
//					{
//						pd.setMaxPrice(sku.getSalePrice());
//					}
//				}
//				else
//				{
//					pd.setMaxPrice(sku.getSalePrice());
//				}
//			}
//			catch(ParseException e)
//			{
//				e.printStackTrace(System.err);
//			}
//		}

		return pd;
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
	public String getBrandName() {
		return brandName;
	}
	/**
	 * @param brandName the brandName to set
	 */
	public void setBrandName(String brandName) {
		this.brandName = brandName;
	}
	/**
	 * @return the productName
	 */
	public String getProductName() {
		return productName;
	}
	/**
	 * @param productName the productName to set
	 */
	public void setProductName(String productName) {
		this.productName = productName;
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


}
