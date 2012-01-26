package com.thepoofy.gwumpy.model;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Unindexed;
import com.googlecode.objectify.condition.IfNull;

public class GwumpyVenueIdLink {
	
	private @Id String foursquareId;
	
	private @Unindexed(IfNull.class)String nycInspectionGradeId;
	
	private @Unindexed(IfNull.class)String yelpId;
	
	
	/**
	 * @return the foursquareId
	 */
	public String getFoursquareId() {
		return foursquareId;
	}
	/**
	 * @param foursquareId the foursquareId to set
	 */
	public void setFoursquareId(String foursquareId) {
		this.foursquareId = foursquareId;
	}
	/**
	 * @return the yelpId
	 */
	public String getYelpId() {
		return yelpId;
	}
	/**
	 * @param yelpId the yelpId to set
	 */
	public void setYelpId(String yelpId) {
		this.yelpId = yelpId;
	}
	/**
	 * @return the nycInspectionGradeId
	 */
	public String getNycInspectionGradeId() {
		return nycInspectionGradeId;
	}
	/**
	 * @param nycInspectionGradeId the nycInspectionGradeId to set
	 */
	public void setNycInspectionGradeId(String nycInspectionGradeId) {
		this.nycInspectionGradeId = nycInspectionGradeId;
	}
}
