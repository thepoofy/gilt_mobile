package com.thepoofy.util.location;

public class MetricDistance implements Distance
{
	private int meters;

	/**
	 * 
	 * @param meters
	 */
	public MetricDistance(int meters)
	{
		this.meters = meters;
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.thepoofy.ca.vo.Distance#getDistanceText()
	 */
	@Override
	public String getDistanceText()
	{
		return meters+" m";
	}
	
	/*
	 * (non-Javadoc)
	 * @see com.thepoofy.ca.vo.Distance#getDistance()
	 */
	@Override
	public float getDistance()
	{
		return meters;
	}	
}
