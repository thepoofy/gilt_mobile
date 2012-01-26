package com.thepoofy.gwumpy.model;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.persistence.Id;

import com.googlecode.objectify.annotation.Unindexed;

/**
 * 
 * @author William Vanderhoef william.vanderhoef@gmail.com
 *
 */
public class NycInspectionGrade {
	//"CAMIS","DBA","BORO","BUILDING","STREET","ZIPCODE","PHONE","CUISINECODE","INSPDATE","ACTION","VIOLCODE","SCORE","CURRENTGRADE","GRADEDATE","RECORDDATE"
	
	private @Id String camis;
	private String dba;
	private @Unindexed String boro;
	private String building;
	private String street;
	private String zipCode;
	private String phone;
	private @Unindexed String cuisineCode;
	private Date inspectionDate;
	private @Unindexed String action;
	private @Unindexed String violationCode;
	private @Unindexed Integer score;
	private String currentGrade;
	private @Unindexed Date gradeDate;
	private @Unindexed Date recordDate;
	
	private Double latitude;
	private Double longitude;
		
	
	/**
	 * @return the camis
	 */
	public String getCamis() {
		return camis;
	}
	/**
	 * @param camis the camis to set
	 */
	public void setCamis(String camis) {
		this.camis = camis;
	}
	/**
	 * @return the dba
	 */
	public String getDba() {
		return dba;
	}
	/**
	 * @param dba the dba to set
	 */
	public void setDba(String dba) {
		this.dba = dba;
	}
	/**
	 * @return the boro
	 */
	public String getBoro() {
		return boro;
	}
	/**
	 * @param boro the boro to set
	 */
	public void setBoro(String boro) {
		this.boro = boro;
	}
	/**
	 * @return the building
	 */
	public String getBuilding() {
		return building;
	}
	/**
	 * @param building the building to set
	 */
	public void setBuilding(String building) {
		if(building != null)
		{
			this.building = building.trim();
		}
		else
		{
			this.building = building;
		}
		
	}
	/**
	 * @return the street
	 */
	public String getStreet() {
		return street;
	}
	/**
	 * @param street the street to set
	 */
	public void setStreet(String street) {
		if(street != null)
		{
			this.street = street.trim();
		}
		else
		{
			this.street = street;
		}
	}
	/**
	 * @return the zipCode
	 */
	public String getZipCode() {
		return zipCode;
	}
	/**
	 * @param zipCode the zipCode to set
	 */
	public void setZipCode(String zipCode) {
		this.zipCode = zipCode;
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
	 * @return the cuisineCode
	 */
	public String getCuisineCode() {
		return cuisineCode;
	}
	/**
	 * @param cuisineCode the cuisineCode to set
	 */
	public void setCuisineCode(String cuisineCode) {
		this.cuisineCode = cuisineCode;
	}
	/**
	 * @return the inspectionDate
	 */
	public Date getInspectionDate() {
		return inspectionDate;
	}
	/**
	 * @param inspectionDate the inspectionDate to set
	 */
	public void setInspectionDate(Date inspectionDate) {
		this.inspectionDate = inspectionDate;
	}
	/**
	 * @param inspectionDate
	 */
	public void setInspectionDateString(String inspectionDate) {
		
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.inspectionDate = sdf.parse(inspectionDate);
		}
		catch(Exception e)
		{
			this.inspectionDate = null;
		}
	}
	
	/**
	 * @return the action
	 */
	public String getAction() {
		return action;
	}
	/**
	 * @param action the action to set
	 */
	public void setAction(String action) {
		this.action = action;
	}
	/**
	 * @return the violationCode
	 */
	public String getViolationCode() {
		return violationCode;
	}
	/**
	 * @param violationCode the violationCode to set
	 */
	public void setViolationCode(String violationCode) {
		this.violationCode = violationCode;
	}
	/**
	 * @return the score
	 */
	public Integer getScore() {
		return score;
	}
	/**
	 * @param score the score to set
	 */
	public void setScore(Integer score) {
		if(score == null)
		{
			score = 0;
		}
		else
		{
			this.score = score;
		}
	}
	
	public void setScoreString(String score)
	{
		try
		{
			this.score = Integer.parseInt(score);	
		}
		catch(NumberFormatException nfe)
		{
			this.score = 0;
		}
		
	}
	/**
	 * @return the currentGrade
	 */
	public String getCurrentGrade() {
		return currentGrade;
	}
	/**
	 * @param currentGrade the currentGrade to set
	 */
	public void setCurrentGrade(String currentGrade) {
		this.currentGrade = currentGrade;
	}
	/**
	 * @return the gradeDate
	 */
	public Date getGradeDate() {
		return gradeDate;
	}
	/**
	 * @param gradeDate the gradeDate to set
	 */
	public void setGradeDate(Date gradeDate) {
		this.gradeDate = gradeDate;
	}
	
	/**
	 * @param gradeDate
	 */
	public void setGradeDateString(String gradeDate)
	{
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.gradeDate = sdf.parse(gradeDate);
		}
		catch(Exception e)
		{
			this.gradeDate = null;
		}	
	}
	
	
	/**
	 * @return the recordDate
	 */
	public Date getRecordDate() {
		return recordDate;
	}
	/**
	 * @param recordDate the recordDate to set
	 */
	public void setRecordDate(Date recordDate) {
		this.recordDate = recordDate;
	}
	/**
	 * @param recordDate
	 */
	public void setRecordDateString(String recordDate) {
		try
		{
			SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			this.recordDate = sdf.parse(recordDate);
		}
		catch(Exception e)
		{
			this.recordDate = null;
		}	
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
}
