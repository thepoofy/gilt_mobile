package com.thepoofy.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 *
 * @author wvanderhoef
 *
 */
public class KeyValuePair
{
	private final String key;
	private final String value;

	/**
	 *
	 * @param key
	 * @param value
	 */
	public KeyValuePair(String key, String value)
	{
		this.key = key;
		this.value = value;
	}

	/**
	 *
	 * @param key
	 * @param pairs
	 */
	public KeyValuePair(String key, KeyValuePair... pairs)
	{
		this.key = key;

		StringBuilder sb = new StringBuilder();
		for (KeyValuePair pair : pairs)
		{
			try
			{
				sb.append(pair.key);
				sb.append(URLEncoder.encode(pair.value, "UTF-8"));
				sb.append(",");
			} catch (UnsupportedEncodingException uee)
			{
				uee.printStackTrace();
			}
		}
		this.value = sb.toString();
	}

	/**
	 * @return the key
	 */
	public String getKey() {
		return key;
	}

	/**
	 * @return the value
	 */
	public String getValue() {
		return value;
	}


}
