package com.thepoofy.gwumpy.dao;

/**
 * 
 * @author William Vanderhoef william.vanderhoef@gmail.com
 *
 */
public class DatastoreObjectNotFoundException extends Exception {

	/**
	 * 
	 */
	private static final long serialVersionUID = 7857393459784552735L;

	/**
	 * 
	 * @param reason
	 */
	public DatastoreObjectNotFoundException(String reason)
	{
		super(reason);
	}
	
}
