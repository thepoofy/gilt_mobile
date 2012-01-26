package com.thepoofy.gilt.model;

/**
 *
 * @author William Vanderhoef william.vanderhoef@gmail.com
 *
 */
public class CategoryCount extends Counter{

	private final String img;
	private String minPrice;
	private String maxPrice;

	public CategoryCount(String name, String imageUrl)
	{
		super(name);

		img = imageUrl;
	}

	/**
	 * @return the img
	 */
	public String getImg() {
		return img;
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


}