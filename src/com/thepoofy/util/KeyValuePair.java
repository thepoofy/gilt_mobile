package com.thepoofy.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

public class KeyValuePair
{
	public String key;
	public String value;

	public KeyValuePair(String key, String value)
	{
		this.key = key;
		this.value = value;
	}

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
}
