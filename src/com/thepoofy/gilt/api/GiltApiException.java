package com.thepoofy.gilt.api;


public class GiltApiException extends Exception {
			
	/**
	 * 
	 */
	private static final long serialVersionUID = 8978693827658381509L;

	/**
	 * 
	 * @param reason
	 */
	public GiltApiException(String reason)
	{
		super(reason);
	}
	
	public GiltApiException(String reason, Exception e)
	{
		super(reason, e);
	}
}
