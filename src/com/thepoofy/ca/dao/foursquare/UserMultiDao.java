package com.thepoofy.ca.dao.foursquare;


/**
 * 
 * @author williamvanderhoef
 *
 */
public abstract class UserMultiDao 
{
//	private final FoursquareDao dao;
//
//	public UserMultiDao(String accessToken)
//	{
//		dao = new FoursquareDao("multi", accessToken);
//	}
//	
//	/**
//	 * 
//	 * @throws HttpConnectionException
//	 * @throws InvalidCredentialsException
//	 * @throws JsonSyntaxException
//	 * @throws IOException
//	 * @throws FoursquareException
//	 */
//	public void loadAppInitData() throws HttpConnectionException, InvalidCredentialsException, JsonSyntaxException, IOException, FoursquareException
//	{
//		//add params for each endpoint
//		Map<String, String> params = new HashMap<String, String>();
//		
////		String requestList = URLEncoder.encode("/users/self", "UTF-8")+","+
////				URLEncoder.encode("/users/self/checkins?limit=100", "UTF-8")+","+
////				URLEncoder.encode("/users/self/mayorships", "UTF-8")+","+
////				URLEncoder.encode("/users/self/lists", "UTF-8");
////		
//		StringBuilder requestBuilder = new StringBuilder();
//		requestBuilder.append("/users/self,")
//				.append("/users/self/checkins?").append(URLEncoder.encode("limit=100", "UTF-8")).append(",")
//				.append("/users/self/mayorships,")
//				.append("/users/self/lists,");
//				
//		params.put("requests", requestBuilder.toString());
//		
//		dao.setUsePost(true);
//		if(!Constants.MOCK_DATA)
//		{
//			Responses res = dao.execute(null, null,params, new Results<Responses>(){});
//			
//			List<String> responseList = res.getResponses();
//			
//			for(String s : responseList)
//			{
//				Log.i(Constants.TAG, s);
//			}
//			
//			
//			int responseNumber = 0;
//			
//			onUserDetailLoaded(getUserDetail(responseList.get(responseNumber++)));
//			onUserCheckinsLoaded(getCheckinHistory(responseList.get(responseNumber++)));
//			onMayorshipsLoaded(getMayorships(responseList.get(responseNumber++)));
//			onUserListsLoaded(getLists(responseList.get(responseNumber++)));
//		}
//		else
//		{
////			onUserDetailLoaded(MockUser.getUserDetail());
////			
//////			Items<VenueHistory> historyList = new Items<VenueHistory>();
//////			onUserVenueHistoryLoaded(historyList);
////			
////			Items<Checkin> checkinList = new Items<Checkin>();
////			
////			onUserCheckinsLoaded(checkinList);
//		}
//	}
//	
//	public abstract void onUserDetailLoaded(UserResponse response);
//	
//	public abstract void onUserCheckinsLoaded(UserCheckinsResponse response);
//	
//	public abstract void onMayorshipsLoaded(UserMayorshipsResponse response);
//	
//	public abstract void onUserListsLoaded(UserListsResponse response);
//	
//	private UserResponse getUserDetail(String res) throws IOException
//	{
//		try
//		{
//			ResultsParser<UserResponse> parser = JsonUtil.getParser(new Results<UserResponse>(){});
//			return parser.fromJson(res).getResponse();
//		}
//		catch(NullPointerException npe)
//		{
//			throw new IOException("An unexpected null pointer was thrown while unwrapping the UserDetail response.", npe);
//		}
//		catch(Exception e)
//		{
//			throw new IOException("A syntax exception was raised while deserializing.", e);
//		}
//	}
////	
////	private Items<VenueHistory> getVenueHistory(String res)throws IOException
////	{
////		try
////		{
////			ResultsParser<UserVenueHistoryResponse> parser = JsonUtil.getParser(new Results<UserVenueHistoryResponse>(){});
////			return parser.fromJson(res).getResponse().getVenues();
////		}
////		catch(NullPointerException npe)
////		{
////			throw new IOException("An unexpected null pointer was thrown while unwrapping the VenueHistory response.", npe);
////		}
////		catch(Exception e)
////		{
////			throw new IOException("A syntax exception was raised while deserializing.", e);
////		}
////	}
////	
//	private UserCheckinsResponse getCheckinHistory(String res)throws IOException
//	{
//		try
//		{
//			ResultsParser<UserCheckinsResponse> parser = JsonUtil.getParser(new Results<UserCheckinsResponse>(){});
//			return parser.fromJson(res).getResponse();
//		}
//		catch(NullPointerException npe)
//		{
//			throw new IOException("An unexpected null pointer was thrown while unwrapping the Checkin History response.");
//		}
//		catch(Exception e)
//		{
//			throw new IOException("A syntax exception was raised while deserializing.");
//		}
//	}
//	
//	private UserMayorshipsResponse getMayorships(String res)throws IOException
//	{
//		try
//		{
//			ResultsParser<UserMayorshipsResponse> parser = JsonUtil.getParser(new Results<UserMayorshipsResponse>(){});
//			return parser.fromJson(res).getResponse();
//		}
//		catch(NullPointerException npe)
//		{
//			throw new IOException("An unexpected null pointer was thrown while unwrapping the Checkin History response.");
//		}
//		catch(Exception e)
//		{
//			throw new IOException("A syntax exception was raised while deserializing.");
//		}
//	}
//	
//	private UserListsResponse getLists(String res)throws IOException
//	{
//		try
//		{
//			ResultsParser<UserListsResponse> parser = JsonUtil.getParser(new Results<UserListsResponse>(){});
//			return parser.fromJson(res).getResponse();
//		}
//		catch(NullPointerException npe)
//		{
//			throw new IOException("An unexpected null pointer was thrown while unwrapping the Checkin History response.");
//		}
//		catch(Exception e)
//		{
//			throw new IOException("A syntax exception was raised while deserializing.");
//		}
//	}
}