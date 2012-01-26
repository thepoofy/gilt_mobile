package com.thepoofy.util.location;

public class ImperialDistance implements Distance
{
	private int meters;
	
	/**
	 * 
	 * @param meters
	 */
	public ImperialDistance(int meters)
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
		return ""+(Math.round(this.meters*3.28084f))+" ft";
	}

	/*
	 * (non-Javadoc)
	 * @see com.thepoofy.ca.vo.Distance#getDistance()
	 */
	@Override
	public float getDistance()
	{
		return this.meters;
	}
}
