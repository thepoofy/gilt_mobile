package com.thepoofy.gwumpy.yelp.model;

import java.util.List;

public class YelpLocation {

	List<String> address;
	String city;
	GeoLocation coordinate;
	String country_code;
	String cross_streets;
	List<String> display_address;
	Integer geo_accuracy;
	List<String> neighborhoods;
	String postal_code;
	String state_code;
	
	
	/**
	 * @return the address
	 */
	public List<String> getAddress() {
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(List<String> address) {
		this.address = address;
	}

	/**
	 * @return the city
	 */
	public String getCity() {
		return city;
	}

	/**
	 * @param city the city to set
	 */
	public void setCity(String city) {
		this.city = city;
	}

	/**
	 * @return the coordinate
	 */
	public GeoLocation getCoordinate() {
		return coordinate;
	}

	/**
	 * @param coordinate the coordinate to set
	 */
	public void setCoordinate(GeoLocation coordinate) {
		this.coordinate = coordinate;
	}

	/**
	 * @return the country_code
	 */
	public String getCountry_code() {
		return country_code;
	}

	/**
	 * @param country_code the country_code to set
	 */
	public void setCountry_code(String country_code) {
		this.country_code = country_code;
	}

	/**
	 * @return the cross_streets
	 */
	public String getCross_streets() {
		return cross_streets;
	}

	/**
	 * @param cross_streets the cross_streets to set
	 */
	public void setCross_streets(String cross_streets) {
		this.cross_streets = cross_streets;
	}

	/**
	 * @return the display_address
	 */
	public List<String> getDisplay_address() {
		return display_address;
	}

	/**
	 * @param display_address the display_address to set
	 */
	public void setDisplay_address(List<String> display_address) {
		this.display_address = display_address;
	}

	/**
	 * @return the geo_accuracy
	 */
	public Integer getGeo_accuracy() {
		return geo_accuracy;
	}

	/**
	 * @param geo_accuracy the geo_accuracy to set
	 */
	public void setGeo_accuracy(Integer geo_accuracy) {
		this.geo_accuracy = geo_accuracy;
	}

	/**
	 * @return the neighborhoods
	 */
	public List<String> getNeighborhoods() {
		return neighborhoods;
	}

	/**
	 * @param neighborhoods the neighborhoods to set
	 */
	public void setNeighborhoods(List<String> neighborhoods) {
		this.neighborhoods = neighborhoods;
	}

	/**
	 * @return the postal_code
	 */
	public String getPostal_code() {
		return postal_code;
	}

	/**
	 * @param postal_code the postal_code to set
	 */
	public void setPostal_code(String postal_code) {
		this.postal_code = postal_code;
	}

	/**
	 * @return the state_code
	 */
	public String getState_code() {
		return state_code;
	}

	/**
	 * @param state_code the state_code to set
	 */
	public void setState_code(String state_code) {
		this.state_code = state_code;
	}
}
