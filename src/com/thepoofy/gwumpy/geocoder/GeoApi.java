package com.thepoofy.gwumpy.geocoder;

import java.util.ArrayList;
import java.util.List;

import org.codehaus.jackson.annotate.JsonProperty;
import org.codehaus.jackson.map.ObjectMapper;

import com.thepoofy.gwumpy.model.NycInspectionGrade;
import com.thepoofy.util.KeyValuePair;
import com.thepoofy.util.URLUtil;
import com.thepoofy.util.location.Location;

public class GeoApi {

	private static final String ENDPOINT = "http://where.yahooapis.com/geocode";
	
	/**
	 * 
	 * @param grade
	 * @return
	 */
	public static Location geocode(NycInspectionGrade grade) throws GeoApiException
	{
		List<KeyValuePair> params = new ArrayList<KeyValuePair>();
		
		params.add(new KeyValuePair("flags", "CEJ"));
		params.add(new KeyValuePair("q", grade.getBuilding()+" "+grade.getStreet()+", "+grade.getZipCode()));
		
		String response = URLUtil.doStandaloneGet(ENDPOINT, params);
		if(response == null)
		{
			throw new GeoApiException("Response is null");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		
		GeoResults results;
		
		try
		{
			results = mapper.readValue(response, GeoResults.class);
		}
		catch(Exception e)
		{
			System.err.println(response);
			e.printStackTrace();
			
			throw new GeoApiException("Response couldn't be parsed for "+grade.getCamis());
		}
		
		if(results == null)
		{
			throw new GeoApiException("Response was null");
		}
		
		YResultSet resultSet = results.resultSet;
		if(resultSet.error != 0)
		{
			throw new GeoApiException(results.resultSet.errorMessage);
		}
		else if(resultSet.error != 0)
		{
			throw new GeoApiException("Results not of acceptable quality");
		}
		else if(resultSet.found == 0)
		{
			return null;
		}
		
		
		YResult location = resultSet.results.get(0);
		
		Location loc = new Location(location.latitude, location.longitude);
		
		return loc;
	}
	
	
	
	public static class GeoResults{
		@JsonProperty("ResultSet")
		YResultSet resultSet;

		/**
		 * @return the resultSet
		 */
		public YResultSet getResultSet() {
			return resultSet;
		}

		/**
		 * @param resultSet the resultSet to set
		 */
		public void setResultSet(YResultSet resultSet) {
			this.resultSet = resultSet;
		}
		
	}
	
	public static class YResultSet{
		
		String version;
		Integer error;
		String errorMessage;
		String locale;
		Integer quality;
		Integer found;
		List<YResult> results;
		/**
		 * @return the version
		 */
		public String getVersion() {
			return version;
		}
		/**
		 * @param version the version to set
		 */
		public void setVersion(String version) {
			this.version = version;
		}
		/**
		 * @return the error
		 */
		@JsonProperty("Error")
		public Integer getError() {
			return error;
		}
		/**
		 * @param error the error to set
		 */
		@JsonProperty("Error")
		public void setError(Integer error) {
			this.error = error;
		}
		/**
		 * @return the errorMessage
		 */
		@JsonProperty("ErrorMessage")
		public String getErrorMessage() {
			return errorMessage;
		}
		/**
		 * @param errorMessage the errorMessage to set
		 */
		@JsonProperty("ErrorMessage")
		public void setErrorMessage(String errorMessage) {
			this.errorMessage = errorMessage;
		}
		/**
		 * @return the locale
		 */
		@JsonProperty("Locale")
		public String getLocale() {
			return locale;
		}
		/**
		 * @param locale the locale to set
		 */
		@JsonProperty("Locale")
		public void setLocale(String locale) {
			this.locale = locale;
		}
		/**
		 * @return the quality
		 */
		@JsonProperty("Quality")
		public Integer getQuality() {
			return quality;
		}
		/**
		 * @param quality the quality to set
		 */
		@JsonProperty("Quality")
		public void setQuality(Integer quality) {
			this.quality = quality;
		}
		/**
		 * @return the found
		 */
		@JsonProperty("Found")
		public Integer getFound() {
			return found;
		}
		/**
		 * @param found the found to set
		 */
		@JsonProperty("Found")
		public void setFound(Integer found) {
			this.found = found;
		}
		/**
		 * @return the results
		 */
		@JsonProperty("Results")
		public List<YResult> getResults() {
			return results;
		}
		/**
		 * @param results the results to set
		 */
		@JsonProperty("Results")
		public void setResults(List<YResult> results) {
			this.results = results;
		}
		
		
	}
	
	public static class YResult{
		Integer quality;
		Double latitude;
		Double longitude;
		Double offsetlat;
		Double offsetlon;
		Integer radius;
		String line1;
		String line2;
		String line3;
		String line4;
		/**
		 * @return the quality
		 */
		public Integer getQuality() {
			return quality;
		}
		/**
		 * @param quality the quality to set
		 */
		public void setQuality(Integer quality) {
			this.quality = quality;
		}
		/**
		 * @return the latitude
		 */
		public Double getLatitude() {
			return latitude;
		}
		/**
		 * @param latitude the latitude to set
		 */
		public void setLatitude(Double latitude) {
			this.latitude = latitude;
		}
		/**
		 * @return the longitude
		 */
		public Double getLongitude() {
			return longitude;
		}
		/**
		 * @param longitude the longitude to set
		 */
		public void setLongitude(Double longitude) {
			this.longitude = longitude;
		}
		/**
		 * @return the radius
		 */
		public Integer getRadius() {
			return radius;
		}
		/**
		 * @param radius the radius to set
		 */
		public void setRadius(Integer radius) {
			this.radius = radius;
		}
		/**
		 * @return the offsetlat
		 */
		public Double getOffsetlat() {
			return offsetlat;
		}
		/**
		 * @param offsetlat the offsetlat to set
		 */
		public void setOffsetlat(Double offsetlat) {
			this.offsetlat = offsetlat;
		}
		/**
		 * @return the offsetlon
		 */
		public Double getOffsetlon() {
			return offsetlon;
		}
		/**
		 * @param offsetlon the offsetlon to set
		 */
		public void setOffsetlon(Double offsetlon) {
			this.offsetlon = offsetlon;
		}
		/**
		 * @return the line1
		 */
		public String getLine1() {
			return line1;
		}
		/**
		 * @param line1 the line1 to set
		 */
		public void setLine1(String line1) {
			this.line1 = line1;
		}
		/**
		 * @return the line2
		 */
		public String getLine2() {
			return line2;
		}
		/**
		 * @param line2 the line2 to set
		 */
		public void setLine2(String line2) {
			this.line2 = line2;
		}
		/**
		 * @return the line3
		 */
		public String getLine3() {
			return line3;
		}
		/**
		 * @param line3 the line3 to set
		 */
		public void setLine3(String line3) {
			this.line3 = line3;
		}
		/**
		 * @return the line4
		 */
		public String getLine4() {
			return line4;
		}
		/**
		 * @param line4 the line4 to set
		 */
		public void setLine4(String line4) {
			this.line4 = line4;
		}
	}
}
