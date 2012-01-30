package com.thepoofy.gilt.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;
import org.codehaus.jackson.type.TypeReference;

import com.thepoofy.constants.Constants;
import com.thepoofy.gilt.GiltProperty;
import com.thepoofy.util.KeyValuePair;
import com.thepoofy.util.URLUtil;
import com.williamvanderhoef.gilt.model.Sale;

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

		String response = URLUtil.doGet(buildSaleUrl(prop), params);
		if(response == null)
		{
			log.warning("GiltApi response was null.");

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
			log.log(Level.SEVERE, e.getMessage(), e);

			throw new GiltApiException("Response couldn't be parsed for Gilt Api");
		}

		if(sales == null)
		{
			log.warning("GiltApi response was null.");

			throw new GiltApiException("Response was null");
		}


		return sales;
	}


}
