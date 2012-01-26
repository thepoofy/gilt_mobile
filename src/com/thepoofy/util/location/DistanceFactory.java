package com.thepoofy.util.location;


public class DistanceFactory
{
	
	public static Distance create(int meters)
	{
		//TODO use context to find user preference for Metric/Imperial
		
		return new MetricDistance(meters);
	}
	

}
