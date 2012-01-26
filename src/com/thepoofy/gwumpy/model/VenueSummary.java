package com.thepoofy.gwumpy.model;

import java.util.ArrayList;
import java.util.List;

import com.thepoofy.gwumpy.dao.DatastoreObjectNotFoundException;
import com.thepoofy.gwumpy.dao.GwumpyVenueLinkApi;
import com.thepoofy.gwumpy.yelp.model.Business;
import com.williamvanderhoef.foursquare.model.ImageDefinition;
import com.williamvanderhoef.foursquare.model.Venue;
import com.williamvanderhoef.foursquare.model.subtypes.Category;
import com.williamvanderhoef.foursquare.responses.VenueSearchResponse;

public class VenueSummary
{
	//id
	private String id;
		//name
	private String name;
	//distance formatted for the user
	private Double distance;
	//address
	private String address;

	//category
	private String category;
	private String categoryImgUrl;

	//people count
	private Integer hereNowCount;

	//star rating
	private Double starRating;
	private String starRatingImgUrl;
	private Integer numRatings;
	
	//DETAILS
	//$$$
	private String costRating;
	
	//ny times review
	private String nytimesReview;
	
	private String allMenuUrl;
	
	//health code rating
	private String healthCodeRating;
	private Integer healthCodeViolations;
	
	
	public static VenueSummary valueOf(Venue v)
	{
		VenueSummary vs = new VenueSummary();
		
		vs.setId(v.getId());
		vs.setName(v.getName());
		
		if(v.getLocation() != null)
		{
			vs.setAddress(v.getLocation().getAddress());
			vs.setDistance(v.getLocation().getDistance());
		}
		
		if(v.getCategories() != null && !v.getCategories().isEmpty())
		{
			Category cat = v.getCategories().get(0);
			ImageDefinition imgDef = cat.getIcon();
			String img = imgDef.getPrefix()+imgDef.getSizes().get(2)+imgDef.getName();
			vs.setCategory(cat.getShortName());
			vs.setCategoryImgUrl(img);
		}
		
		vs.setHereNowCount(v.getHereNow().getCount());
		
		return vs;
	}
	
	public void addYelpInfo(Business b)
	{
		this.setStarRating(b.getRating());
		this.setStarRatingImgUrl(b.getRating_img_url_small());
		
		this.setNumRatings(b.getReview_count());
	}
	
	
	
	/**
	 * @return the name
	 */
	public String getName()
	{
		return name;
	}

	/**
	 * @param name the name to set
	 */
	public void setName(String name)
	{
		this.name = name;
	}

	/**
	 * @return the distance
	 */
	public Double getDistance()
	{
		return distance;
	}

	/**
	 * @param distance the distance to set
	 */
	public void setDistance(Double distance)
	{
		this.distance = distance;
	}

	/**
	 * @return the address
	 */
	public String getAddress()
	{
		return address;
	}

	/**
	 * @param address the address to set
	 */
	public void setAddress(String address)
	{
		this.address = address;
	}

	/**
	 * @return the category
	 */
	public String getCategory()
	{
		return category;
	}

	/**
	 * @param category the category to set
	 */
	public void setCategory(String category)
	{
		this.category = category;
	}

	/**
	 * @return the categoryImgUrl
	 */
	public String getCategoryImgUrl()
	{
		return categoryImgUrl;
	}

	/**
	 * @param categoryImgUrl the categoryImgUrl to set
	 */
	public void setCategoryImgUrl(String categoryImgUrl)
	{
		this.categoryImgUrl = categoryImgUrl;
	}

	/**
	 * @return the hereNowCount
	 */
	public Integer getHereNowCount()
	{
		return hereNowCount;
	}

	/**
	 * @param hereNowCount the hereNowCount to set
	 */
	public void setHereNowCount(Integer hereNowCount)
	{
		this.hereNowCount = hereNowCount;
	}

	/**
	 * @return the starRating
	 */
	public Double getStarRating()
	{
		return starRating;
	}

	/**
	 * @param starRating the starRating to set
	 */
	public void setStarRating(Double starRating)
	{
		this.starRating = starRating;
	}

	/**
	 * @return the starRatingImgUrl
	 */
	public String getStarRatingImgUrl()
	{
		return starRatingImgUrl;
	}

	/**
	 * @param starRatingImgUrl the starRatingImgUrl to set
	 */
	public void setStarRatingImgUrl(String starRatingImgUrl)
	{
		this.starRatingImgUrl = starRatingImgUrl;
	}

	/**
	 * @return the costRating
	 */
	public String getCostRating()
	{
		return costRating;
	}

	/**
	 * @param costRating the costRating to set
	 */
	public void setCostRating(String costRating)
	{
		this.costRating = costRating;
	}

	/**
	 * @return the nytimesReview
	 */
	public String getNytimesReview()
	{
		return nytimesReview;
	}

	/**
	 * @param nytimesReview the nytimesReview to set
	 */
	public void setNytimesReview(String nytimesReview)
	{
		this.nytimesReview = nytimesReview;
	}

	/**
	 * @return the healthCodeRating
	 */
	public String getHealthCodeRating()
	{
		return healthCodeRating;
	}

	/**
	 * @param healthCodeRating the healthCodeRating to set
	 */
	public void setHealthCodeRating(String healthCodeRating)
	{
		this.healthCodeRating = healthCodeRating;
	}
	/**
	 * @return the id
	 */
	public String getId()
	{
		return id;
	}
	/**
	 * @param id the id to set
	 */
	public void setId(String id)
	{
		this.id = id;
	}

	
	public static List<VenueSummary> adaptVenueList(VenueSearchResponse vsr)
	{
		List<VenueSummary> venues = new ArrayList<VenueSummary>();
		if(vsr == null || vsr.getVenues() == null || vsr.getVenues().isEmpty())
		{
			return venues;
		}
		
		for(Venue v : vsr.getVenues())
		{
			VenueSummary vs = valueOf(v);
			
			try
			{
				NycInspectionGrade grade = GwumpyVenueLinkApi.findGrade(v);
				
				if("Z".equalsIgnoreCase(grade.getCurrentGrade()))
				{
					vs.setHealthCodeRating("-");
				}
				else
				{
					vs.setHealthCodeRating(grade.getCurrentGrade());
				}
				
				vs.setHealthCodeViolations(grade.getScore());
			}
			catch(DatastoreObjectNotFoundException e)
			{
				//ignore this
//				vs.setHealthCodeRating("A");
//				vs.setHealthCodeViolations(11);
			}
			
			venues.add(vs);
		}
		
		return venues;
	}



	/**
	 * @return the allMenuUrl
	 */
	public String getAllMenuUrl()
	{
		return allMenuUrl;
	}



	/**
	 * @param allMenuUrl the allMenuUrl to set
	 */
	public void setAllMenuUrl(String allMenuUrl)
	{
		this.allMenuUrl = allMenuUrl;
	}

	/**
	 * @return the numRatings
	 */
	public Integer getNumRatings()
	{
		return numRatings;
	}

	/**
	 * @param numRatings the numRatings to set
	 */
	public void setNumRatings(Integer numRatings)
	{
		this.numRatings = numRatings;
	}

	/**
	 * @return the healthCodeViolations
	 */
	public Integer getHealthCodeViolations() {
		return healthCodeViolations;
	}

	/**
	 * @param healthCodeViolations the healthCodeViolations to set
	 */
	public void setHealthCodeViolations(Integer healthCodeViolations) {
		this.healthCodeViolations = healthCodeViolations;
	}
	
}
