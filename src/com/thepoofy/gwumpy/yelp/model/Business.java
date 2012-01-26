package com.thepoofy.gwumpy.yelp.model;

import java.util.List;
import java.util.Map;

/**
 * 
 * @author William Vanderhoef william.vanderhoef@gmail.com
 *
 */
public class Business {

	String id;
	String name;
	String image_url;
	String url;
	String mobile_url;
	String phone;
	String display_phone;
	
	Integer review_count;
	List<List<String>> categories;
	Double distance;
	
	Double rating;
	String rating_img_url;
	String rating_img_url_large;
	String rating_img_url_small;
	
	String snippet_text;
	String snippet_image_url;
	
	YelpLocation location;
	
	List<Map<String, Object>>deals;
	
	
	
	
	
	/**
	 * @return the categories
	 */
	public List<List<String>> getCategories() {
		return categories;
	}
	/**
	 * @param categories the categories to set
	 */
	public void setCategories(List<List<String>> categories) {
		this.categories = categories;
	}
	/**
	 * @return the display_phone
	 */
	public String getDisplay_phone() {
		return display_phone;
	}
	/**
	 * @param display_phone the display_phone to set
	 */
	public void setDisplay_phone(String display_phone) {
		this.display_phone = display_phone;
	}
	/**
	 * @return the id
	 */
	public String getId() {
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id) {
		this.id = id;
	}
	/**
	 * @return the image_url
	 */
	public String getImage_url() {
		return image_url;
	}
	/**
	 * @param image_url the image_url to set
	 */
	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}
	/**
	 * @return the location
	 */
	public YelpLocation getLocation() {
		return location;
	}
	/**
	 * @param location the location to set
	 */
	public void setLocation(YelpLocation location) {
		this.location = location;
	}
	/**
	 * @return the mobile_url
	 */
	public String getMobile_url() {
		return mobile_url;
	}
	/**
	 * @param mobile_url the mobile_url to set
	 */
	public void setMobile_url(String mobile_url) {
		this.mobile_url = mobile_url;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @param name the name to set
	 */
	public void setName(String name) {
		this.name = name;
	}
	/**
	 * @return the phone
	 */
	public String getPhone() {
		return phone;
	}
	/**
	 * @param phone the phone to set
	 */
	public void setPhone(String phone) {
		this.phone = phone;
	}
	/**
	 * @return the rating_img_url
	 */
	public String getRating_img_url() {
		return rating_img_url;
	}
	/**
	 * @param rating_img_url the rating_img_url to set
	 */
	public void setRating_img_url(String rating_img_url) {
		this.rating_img_url = rating_img_url;
	}
	/**
	 * @return the rating_img_url_large
	 */
	public String getRating_img_url_large() {
		return rating_img_url_large;
	}
	/**
	 * @param rating_img_url_large the rating_img_url_large to set
	 */
	public void setRating_img_url_large(String rating_img_url_large) {
		this.rating_img_url_large = rating_img_url_large;
	}
	/**
	 * @return the rating_img_url_small
	 */
	public String getRating_img_url_small() {
		return rating_img_url_small;
	}
	/**
	 * @param rating_img_url_small the rating_img_url_small to set
	 */
	public void setRating_img_url_small(String rating_img_url_small) {
		this.rating_img_url_small = rating_img_url_small;
	}
	/**
	 * @return the review_count
	 */
	public Integer getReview_count() {
		return review_count;
	}
	/**
	 * @param review_count the review_count to set
	 */
	public void setReview_count(Integer review_count) {
		this.review_count = review_count;
	}
	/**
	 * @return the snippet_image_url
	 */
	public String getSnippet_image_url() {
		return snippet_image_url;
	}
	/**
	 * @param snippet_image_url the snippet_image_url to set
	 */
	public void setSnippet_image_url(String snippet_image_url) {
		this.snippet_image_url = snippet_image_url;
	}
	/**
	 * @return the snippet_text
	 */
	public String getSnippet_text() {
		return snippet_text;
	}
	/**
	 * @param snippet_text the snippet_text to set
	 */
	public void setSnippet_text(String snippet_text) {
		this.snippet_text = snippet_text;
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
	 * @return the distance
	 */
	public Double getDistance() {
		return distance;
	}
	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Double distance) {
		this.distance = distance;
	}
	/**
	 * @return the rating
	 */
	public Double getRating() {
		return rating;
	}
	/**
	 * @param rating the rating to set
	 */
	public void setRating(Double rating) {
		this.rating = rating;
	}
	/**
	 * @return the deals
	 */
	public List<Map<String, Object>> getDeals() {
		return deals;
	}
	/**
	 * @param deals the deals to set
	 */
	public void setDeals(List<Map<String, Object>> deals) {
		this.deals = deals;
	}	
}
