package com.thepoofy.gilt;

import org.codehaus.jackson.map.annotate.JsonSerialize;

@JsonSerialize(using = GiltPropertySerializer.class)
public enum GiltProperty {

	WOMEN("women", "Women"),
	MEN("men", "Men"),
	HOME("home", "Home"),
	KIDS("kids", "Children");
	
	private final String label;
	private final String divisionKey;
	
	private GiltProperty(String divKey, String label)
	{
		this.divisionKey = divKey;
		this.label = label;
	}

	/**
	 * @return the label
	 */
	public String getLabel() {
		return label;
	}

	/**
	 * @return the divisionKey
	 */
	public String getDivisionKey() {
		return divisionKey;
	}
	
}
