package com.thepoofy.gwumpy.yelp.model;

import java.util.List;

public class YelpSearchResults {

	private List<Business> businesses;
	private SearchRegion region;
	private Integer total;
	
	public static class SearchRegion
	{
		GeoLocation center;
		YelpLocationBounds span;
		
		/**
		 * @return the center
		 */
		public GeoLocation getCenter() {
			return center;
		}
		/**
		 * @param center the center to set
		 */
		public void setCenter(GeoLocation center) {
			this.center = center;
		}
		/**
		 * @return the span
		 */
		public YelpLocationBounds getSpan() {
			return span;
		}
		/**
		 * @param span the span to set
		 */
		public void setSpan(YelpLocationBounds span) {
			this.span = span;
		}
	}
	
	public static class YelpLocationBounds
	{
		Double latitude_delta;
		Double longitude_delta;
		/**
		 * @return the latitude_delta
		 */
		public Double getLatitude_delta() {
			return latitude_delta;
		}
		/**
		 * @param latitude_delta the latitude_delta to set
		 */
		public void setLatitude_delta(Double latitude_delta) {
			this.latitude_delta = latitude_delta;
		}
		/**
		 * @return the longitude_delta
		 */
		public Double getLongitude_delta() {
			return longitude_delta;
		}
		/**
		 * @param longitude_delta the longitude_delta to set
		 */
		public void setLongitude_delta(Double longitude_delta) {
			this.longitude_delta = longitude_delta;
		}
		
		
	}

	/**
	 * @return the businesses
	 */
	public List<Business> getBusinesses() {
		return businesses;
	}

	/**
	 * @param businesses the businesses to set
	 */
	public void setBusinesses(List<Business> businesses) {
		this.businesses = businesses;
	}

	/**
	 * @return the region
	 */
	public SearchRegion getRegion() {
		return region;
	}

	/**
	 * @param region the region to set
	 */
	public void setRegion(SearchRegion region) {
		this.region = region;
	}

	/**
	 * @return the total
	 */
	public Integer getTotal() {
		return total;
	}

	/**
	 * @param total the total to set
	 */
	public void setTotal(Integer total) {
		this.total = total;
	}
}
