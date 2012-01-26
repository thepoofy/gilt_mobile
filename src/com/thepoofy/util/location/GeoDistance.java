package com.thepoofy.util.location;

import com.williamvanderhoef.foursquare.model.Venue;

/**
 * Adapted from the answer found here:
 * http://stackoverflow.com/questions/837872/calculate-distance-in-meters-when-you-know-longitude-and-latitude-in-java
 * 
 * @author Willum
 * 
 */
public class GeoDistance
{
	//according to http://www.world-mysteries.com/sci_7cos.htm
	private static final double EARTH_RADIUS = 3958.73926185;
	
	//Meter_Conversion is according to google
	private static final float METER_CONVERSION = 1609.344f;

	
	/**
	 * 
	 * @param lat1
	 * @param lng1
	 * @param lat2
	 * @param lng2
	 * @return
	 */
	public static int metersBetween(double lat1, double lng1, double lat2, double lng2)
	{
		double dLat = Math.toRadians(lat2 - lat1);
		double dLng = Math.toRadians(lng2 - lng1);
		double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) + Math.cos(Math.toRadians(lat1)) * Math.cos(Math.toRadians(lat2)) * Math.sin(dLng / 2) * Math.sin(dLng / 2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));
		double dist = EARTH_RADIUS * c;

		return (int)(dist * METER_CONVERSION);
	}
	
	/**
	 * 
	 * @param loc
	 * @param v
	 * @return
	 */
	public static Distance distBetween(Location loc, Venue v)
	{
		return DistanceFactory.create(
				metersBetween(loc.getLatitude(), 
						loc.getLongitude(), 
						v.getLocation().getLat(), 
						v.getLocation().getLng()));
	}
	
	/**
	 * 
	 * @param loc
	 * @param v
	 * @param ctx
	 * @return
	 */
	public static Distance distBetween(Location loc, Location loc2)
	{
		return DistanceFactory.create(
				metersBetween(loc.getLatitude(), 
						loc.getLongitude(), 
						loc2.getLatitude(), 
						loc2.getLongitude()));
	}
	
	/**
	 * 
	 * @param v
	 * @param lat
	 * @param lng
	 * @param distance
	 * @return
	 */
	public static boolean isInRange(Venue v, double lat, double lng, double distance)
	{
		if(v == null 
				|| v.getLocation() == null
				|| v.getLocation().getLat() == null
				|| v.getLocation().getLng() == null)
		{
			return false;
		}
		
		double miles = metersBetween( v.getLocation().getLat(), v.getLocation().getLng(), lat, lng);
		
		return (miles <= distance);
	}
	
	public static boolean isInRange(Venue v, Location l, double distance)
	{
		double miles = metersBetween( v.getLocation().getLat(), v.getLocation().getLng(), l.getLatitude(), l.getLongitude());
		
		return (miles <= distance);
	}
	
	
	
}
