package com.thepoofy.gilt.api;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.codehaus.jackson.map.JsonMappingException;
import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.map.PropertyNamingStrategy;

import com.thepoofy.gilt.GiltProperty;
import com.thepoofy.gilt.constants.Constants;
import com.thepoofy.util.KeyValuePair;
import com.thepoofy.util.URLUtil;
import com.williamvanderhoef.gilt.model.Product;
import com.williamvanderhoef.gilt.responses.SalesResponse;

/**
 *
 * @author William Vanderhoef william.vanderhoef@gmail.com
 *
 */
public class GiltApi {

	private static final Logger log = Logger.getLogger(GiltApi.class.getName());

	/**
	 *
	 * @param prop
	 * @return
	 */
	private static String buildSaleUrl(GiltProperty prop)
	{
		String url = Constants.GILT_API_URL+"sales/"+prop.getDivisionKey()+"/active.json";

		return url;
	}

	/**
	 *
	 * @param productId
	 * @return
	 */
	private static String buildProductDetailsUrl(Number productId)
	{
		String url = Constants.GILT_API_URL+"products/"+productId+"/detail.json";

		return url;
	}



	/**
	 *
	 * @param prop
	 * @return sales
	 * @throws GiltApiException
	 */
	public static SalesResponse fetchSales(GiltProperty prop) throws GiltApiException
	{
		List<KeyValuePair> params = new ArrayList<KeyValuePair>();

		params.add(new KeyValuePair("apikey", Constants.GILT_ACCESS_TOKEN));

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


		return salesResponse;
	}

	/**
	 *
	 * @param productId
	 * @return Product
	 * @throws GiltApiException
	 */
	public static Product fetchProduct(Number productId) throws GiltApiException
	{
		return fetchProduct(buildProductDetailsUrl(productId));
	}

	/**
	 * @param url
	 * @return product
	 * @throws GiltApiException
	 */
	public static Product fetchProduct(String url) throws GiltApiException
	{
		List<KeyValuePair> params = new ArrayList<KeyValuePair>();

		params.add(new KeyValuePair("apikey", Constants.GILT_ACCESS_TOKEN));

		String response = URLUtil.doStandaloneGet(url, params);
		if(response == null)
		{
			log.warning("GiltApi response was null.");

			throw new GiltApiException("Response is null");
		}

		ObjectMapper mapper = new ObjectMapper();

		mapper.setPropertyNamingStrategy(new PropertyNamingStrategy.LowerCaseWithUnderscoresStrategy());

		Product product = null;

		try
		{
			product = mapper.readValue(response, Product.class);
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

		if(product == null)
		{
			log.warning("GiltApi response was empty.");

			throw new GiltApiException("Response was null");
		}


		return product;
	}

}
