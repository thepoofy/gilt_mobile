package com.thepoofy.gilt.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.type.TypeReference;

import com.thepoofy.constants.Constants;
import com.thepoofy.gilt.servlet.BrandsServlet;
import com.thepoofy.util.KeyValuePair;
import com.thepoofy.util.URLUtil;
import com.williamvanderhoef.gilt.model.Sale;

/**
 * 
 * @author William Vanderhoef william.vanderhoef@gmail.com
 *
 */
public class GiltApi {

	
	/**
	 * 
	 * @param grade
	 * @return
	 */
	public static List<Sale> fetchSales() throws GiltApiException
	{
		List<KeyValuePair> params = new ArrayList<KeyValuePair>();
		
		params.add(new KeyValuePair("apikey", Constants.GILT_ACCESS_TOKEN));
		params.add(new KeyValuePair("product_detail", "true"));
		
		String response = URLUtil.doGet(Constants.GILT_API_URL, params);
		if(response == null)
		{
			throw new GiltApiException("Response is null");
		}
		
		ObjectMapper mapper = new ObjectMapper();
		
		mapper.setPropertyNamingStrategy(new PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy());
		
		List<Sale> sales;
		
		try
		{
			TypeReference<List<Sale>> typeRef = new TypeReference<List<Sale>>(){};

			sales = mapper.readValue(response, typeRef);
		}
		catch(IOException e)
		{
			System.err.println(response);
			e.printStackTrace();
			
			throw new GiltApiException("Response couldn't be parsed for Gilt Api");
		}
		
		if(sales == null)
		{
			throw new GiltApiException("Response was null");
		}
		
		
		return sales;
	}
	
	
}
