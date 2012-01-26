package com.thepoofy.ca.dao.foursquare;



public class UsersDao 
{
//	private final FoursquareDao dao;
//	
//	/**
//	 * 
//	 * @param accessToken
//	 */
//	public UsersDao(String accessToken)
//	{
//		dao = new FoursquareDao("users", accessToken);
//	}
//	
//	/**
//	 * http://developer.foursquare.com/docs/users/users.html
//	 * 
//	 * @return
//	 * @throws InvalidCredentialsException 
//	 * @throws HttpConnectionException 
//	 * @throws IOException 
//	 */
//	public UserResponse getUser() throws IOException, HttpConnectionException, InvalidCredentialsException, FoursquareException, JsonSyntaxException, IOException
//	{
//		return dao.execute("self", null, new Results<UserResponse>(){});
//	}
//	
//	
//	/**
//	 * http://developer.foursquare.com/docs/users/checkins.html
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	public UserCheckinsResponse getCheckins(Integer limit, Integer offset, Long afterTimestamp, Long beforeTimestamp) throws Exception
//	{
//		Map<String, String>params = new HashMap<String,String>();
//		if(limit != null)
//		{
//			params.put("limit", limit.toString());
//		}
//		if(offset != null)
//		{
//			params.put("offset", offset.toString());
//		}
//		if(afterTimestamp != null)
//		{
//			params.put("afterTimestamp", afterTimestamp.toString());
//		}
//		if(beforeTimestamp != null)
//		{
//			params.put("beforeTimestamp", beforeTimestamp.toString());
//		}
//		
//		if(!Constants.MOCK_DATA)
//		{
//			return dao.execute("self","checkins",params, new Results<UserCheckinsResponse>(){});
//		}
//		else
//		{
//			UserCheckinsResponse res = new UserCheckinsResponse();
//			
//			MockCheckin mc = new MockCheckin();
//			
//			res.setCheckins(mc.getCheckinList());
//			
//			return res;
//		}
//	}
//	
//	/**
//	 * 
//	 * @param afterTimestamp
//	 * @param beforeTimestamp
//	 * @return
//	 * @throws Exception
//	 */
//	public UserVenueHistoryResponse getVenueHistory(Long afterTimestamp, Long beforeTimestamp) throws Exception
//	{
//		Map<String, String>params = new HashMap<String,String>();
//
//		if(afterTimestamp != null)
//		{
//			params.put("afterTimestamp", afterTimestamp.toString());
//		}
//		if(beforeTimestamp != null)
//		{
//			params.put("beforeTimestamp", beforeTimestamp.toString());
//		}
//		
//		if(!Constants.MOCK_DATA)
//		{
//			return dao.execute("self","venuehistory",params, new Results<UserVenueHistoryResponse>(){});			
//		}
//		else
//		{
//			MockVenueHistory mv = new MockVenueHistory();
//			
//			UserVenueHistoryResponse res = new UserVenueHistoryResponse();
//			res.setVenues(mv.getVenueHistoryList());
//			return res;
//			
//		}
//	}
//	
//	/**
//	 * 
//	 * @return
//	 * @throws Exception
//	 */
//	public UserListsResponse getLists() throws Exception
//	{
//		Map<String, String>params = new HashMap<String,String>();
//		
//		if(!Constants.MOCK_DATA)
//		{
//			return dao.execute("self","lists",params, new Results<UserListsResponse>(){});			
//		}
//		else
//		{
//			//TODO add mock for lists
//			return null;
//		}
//	}
//	
//	/**
//	 * 
//	 * @param group
//	 * @return
//	 * @throws Exception
//	 */
//	public UserListsGroupResponse getLists(String group) throws Exception
//	{
//		Map<String, String>params = new HashMap<String,String>();
//		
//		params.put("group", group);
//		
//		if(!Constants.MOCK_DATA)
//		{
//			return dao.execute("self","lists",params, new Results<UserListsGroupResponse>(){});			
//		}
//		else
//		{
//			//TODO add mock for lists
//			return null;
//		}
//	}
}