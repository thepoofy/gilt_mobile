package com.thepoofy.gilt.model;

/**
 * 
 * @author William Vanderhoef william.vanderhoef@gmail.com
 *
 */
public class CategoryCount extends Counter{
	
	private final String img;
	
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
	
	
}