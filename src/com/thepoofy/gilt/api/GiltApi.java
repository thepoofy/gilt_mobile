package com.thepoofy.gilt.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;

import com.thepoofy.constants.Constants;
import com.thepoofy.gilt.GiltProperty;
import com.thepoofy.util.KeyValuePair;
import com.thepoofy.util.URLUtil;
import com.williamvanderhoef.gilt.model.Sale;
import com.williamvanderhoef.gilt.responses.SalesResponse;

/**
 *
 * @author William Vanderhoef william.vanderhoef@gmail.com
 *
 */
public class GiltApi {

	private static final Logger log = Logger.getLogger(GiltApi.class.getName());

	
	private static String buildSaleUrl(GiltProperty prop)
	{
		String url = Constants.GILT_API_URL+"sales/"+prop.getDivisionKey()+"/active.json";
		
		return url;
	}

	/**
	 *
	 * @return
	 * @throws GiltApiException
	 */
	public static List<Sale> fetchSales(GiltProperty prop) throws GiltApiException
	{
		List<KeyValuePair> params = new ArrayList<KeyValuePair>();

		params.add(new KeyValuePair("apikey", Constants.GILT_ACCESS_TOKEN));
		params.add(new KeyValuePair("product_detail", "true"));

		String url = buildSaleUrl(prop);
		String response = URLUtil.doGet(url, params);
		if(response == null)
		{
			log.warning("GiltApi response was null.");

			throw new GiltApiException("Response is null");
		}

		ObjectMapper mapper = new ObjectMapper();

		mapper.setPropertyNamingStrategy(new PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy());

		SalesResponse salesResponse;

		try
		{
			salesResponse = mapper.readValue(response, SalesResponse.class);
		}
		catch(JsonMappingException e)
		{
			log.log(Level.SEVERE, "Failed parsing response: "+response);
			log.log(Level.SEVERE, "Failed calling: "+url+"\t"+e.getMessage(), e);
			throw new GiltApiException("Response couldn't be parsed for Gilt Api", e);
		}
		catch(IOException e)
		{
			log.log(Level.SEVERE, "Failed parsing response: "+response);
			log.log(Level.SEVERE, "Failed calling: "+url);
			log.log(Level.SEVERE, "Failed calling: "+url+"\t"+e.getMessage(), e);

			throw new GiltApiException("Response couldn't be parsed for Gilt Api", e);
		}

		if(salesResponse == null || salesResponse.getSales() == null)
		{
			log.warning("GiltApi response was empty.");

			throw new GiltApiException("Response was null");
		}


		return salesResponse.getSales();
	}


}
