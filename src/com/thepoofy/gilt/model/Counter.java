package com.thepoofy.gilt.model;

/**
 * 
 * @author William Vanderhoef william.vanderhoef@gmail.com
 *
 */
public class Counter implements Comparable<Counter>
{
	public final String name;
	public int count = 0;
	public Counter(String name)
	{
		this.name = name;
	}
	/**
	 * @return the name
	 */
	public String getName() {
		return name;
	}
	/**
	 * @return the count
	 */
	public int getCount() {
		return count;
	}
	
	
	@Override
	public int compareTo(Counter o) {
		
		if(o == null)
		{
			return -1;
		}
		
		
		int diff = new Integer(this.count).compareTo(o.getCount());
		if(diff != 0)
		{
			return diff*-1;
		}
		
		return this.name.compareTo(o.getName());
	}
	
	
}