package com.thepoofy.gwumpy.nyc;

import java.util.logging.Logger;

import com.googlecode.objectify.Key;
import com.googlecode.objectify.NotFoundException;
import com.googlecode.objectify.Objectify;
import com.googlecode.objectify.ObjectifyService;
import com.thepoofy.gwumpy.dao.DatastoreObjectNotFoundException;
import com.thepoofy.gwumpy.model.NycInspectionGrade;
import com.thepoofy.util.location.Location;

public class NycInspectionApi
{
	private static final Logger log = Logger.getLogger(NycInspectionApi.class.getName());
	
	static{
		ObjectifyService.register(NycInspectionGrade.class);
	}
	
	
	
	
	/**
	 * 
	 * @param v
	 * @return
	 */
	public static void updateLocation(NycInspectionGrade grade, Location loc)
	{
		Objectify ofy = ObjectifyService.begin();
		
		grade.setLatitude(loc.getLatitude());
		grade.setLongitude(loc.getLongitude());
		
		ofy.put(grade);
	}
	
	/**
	 * 
	 * @param ofy
	 * @param camis
	 * @return
	 */
	private static NycInspectionGrade lookup(Objectify ofy, String camis)
	{
		try
		{
			return ofy.get(new Key<NycInspectionGrade>(NycInspectionGrade.class, camis));	
		}
		catch(NotFoundException e)
		{
			return null;
		}
	}
	
	/**
	 * 
	 * @param camis
	 * @return
	 * @throws DatastoreObjectNotFoundException
	 */
	public static NycInspectionGrade findGrade(String camis) throws DatastoreObjectNotFoundException
	{
		if(camis == null)
		{
			throw new NullPointerException("Camis is null, can't find a grade.");
		}
		
		Objectify ofy = ObjectifyService.begin();
		
		NycInspectionGrade grade = lookup(ofy, camis);
		
		if(grade == null){
			throw new DatastoreObjectNotFoundException("No inspections found for establishment: "+camis);
		}
		
		return grade; 
	}
	
	
	/**
	 * 
	 * @param grade
	 */
	public static void saveGrade(NycInspectionGrade grade)
	{
		Objectify ofy = ObjectifyService.begin();
		
		NycInspectionGrade latestGrade = lookup(ofy, grade.getCamis());
		
		if(latestGrade == null)
		{
			ofy.put(grade);
			log.info("Inserted grade for "+grade.getDba()+" is "+grade.getCurrentGrade()+" on "+grade.getInspectionDate());
		}
		else
		{
			log.info("Not saving duplicate grade data for "+grade.getDba());
		}
	}
	
}
