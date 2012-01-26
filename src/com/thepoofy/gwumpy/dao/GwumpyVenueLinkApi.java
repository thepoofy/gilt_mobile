package com.thepoofy.gwumpy.dao;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.thepoofy.gwumpy.model.GwumpyVenueIdLink;
import com.thepoofy.gwumpy.model.NycInspectionGrade;
import com.thepoofy.gwumpy.nyc.NycInspectionApi;
import com.thepoofy.gwumpy.yelp.model.Business;
import com.williamvanderhoef.foursquare.model.Venue;

public class GwumpyVenueLinkApi
{
	static{
		ObjectifyService.register(GwumpyVenueIdLink.class);
	}
	
//	private static final Logger log = Logger.getLogger(GwumpyVenueLinkApi.class.getName());
	

	/**
	 * 
	 * @param v
	 * @return
	 * @throws Exception
	 */
	public static GwumpyVenueIdLink findVenueLink(Venue v) throws DatastoreObjectNotFoundException
	{
		if(v == null)
		{
			throw new NullPointerException("Venue cannot be null.");
		}
		
		Objectify ofy = ObjectifyService.begin();
		
		GwumpyVenueIdLink link = lookup(ofy, v.getId());
		
		if(link == null){
			throw new DatastoreObjectNotFoundException("No foursquare venue information found.");
		}
		
		return link;
	}
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public static void updateNycInspectionGrade(Venue v, NycInspectionGrade grade)
	{
		Objectify ofy = ObjectifyService.begin();
		
		GwumpyVenueIdLink link;

		try
		{
			link = findVenueLink(v);
			
			link.setNycInspectionGradeId(grade.getCamis());
		}
		catch(DatastoreObjectNotFoundException e)
		{
			link = new GwumpyVenueIdLink();
			link.setFoursquareId(v.getId());
			link.setNycInspectionGradeId(grade.getCamis());
		}
		
		ofy.put(link);
	}
	
	public static void updateYelp(Venue v, Business yelpBusiness)
	{
		Objectify ofy = ObjectifyService.begin();
		
		GwumpyVenueIdLink link;
		try
		{
			link = findVenueLink(v);
			
			link.setYelpId(yelpBusiness.getId());
		}
		catch(DatastoreObjectNotFoundException e)
		{
			link = new GwumpyVenueIdLink();
			link.setFoursquareId(v.getId());
			link.setYelpId(yelpBusiness.getId());
		}
		
		ofy.put(link);
	}
	

	/**
	 * Convenience method 
	 * 
	 * @param foursquareId
	 * @return
	 * @throws DatastoreObjectNotFoundException
	 */
	public static NycInspectionGrade findGrade(Venue v) throws DatastoreObjectNotFoundException
	{
		GwumpyVenueIdLink link = findVenueLink(v);
		
		return NycInspectionApi.findGrade(link.getNycInspectionGradeId());
	}
	
	
	/**
	 * The optimal way to get a GwumpyVenueIdLink
	 * 
	 * @param ofy
	 * @param foursquareId
	 * @return
	 */
	private static GwumpyVenueIdLink lookup(Objectify ofy, String foursquareId)
	{
		try
		{
			return ofy.get(new Key<GwumpyVenueIdLink>(GwumpyVenueIdLink.class, foursquareId));	
		}
		catch(NotFoundException e)
		{
			return null;
		}
	}
//	
//	
//	/**
//	 * 
//	 * @param grade
//	 */
//	public static void saveGrade(NycInspectionGrade grade)
//	{
//		Objectify ofy = ObjectifyService.begin();
//		
//		NycInspectionGrade latestGrade = ofy.query(NycInspectionGrade.class).filter("camis", grade.getCamis()).order("inspectionDate").limit(1).get();
//		
//		if(latestGrade == null)
//		{
//			ofy.put(grade);
//			log.info("Inserted grade for "+grade.getDba()+" is "+grade.getCurrentGrade()+" on "+grade.getInspectionDate());
//		}
//		else
//		{
//			log.info("No action taken for "+grade.getDba());
//		}
//	}
	
}
