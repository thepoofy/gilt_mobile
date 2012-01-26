package com.thepoofy.ca.dao.foursquare;


/**
 * 
 * @author <a href="mailto://william.vanderhoef@gmail.com">William Vanderhoef</a>
 *
 */
public class CheckinsDao
{
//	private final FoursquareDao dao;
//	
//	public CheckinsDao(String accessToken)
//	{
//		dao = new FoursquareDao("checkins", accessToken);
//	}
//	
//	public CheckinAddResponse addCheckin(String venueId, BroadcastType broadcast, Location loc)
//		throws HttpConnectionException, InvalidCredentialsException, Exception
//	{
//		
//		if(venueId == null)
//		{
//			throw new IllegalArgumentException("Venue ID must be supplied");
//		}
//		if(loc == null)
//		{
//			throw new IllegalArgumentException("Location cannot be null");
//		}
//		
//		
//		Map<String, String>params = new HashMap<String,String>();
//		
//		params.put("venueId", venueId);
//		params.put("ll", loc.getLatitude()+","+loc.getLongitude());
//		params.put("broadcast", broadcast.getBroadcast());
//		
//		if(loc.hasAccuracy())
//		{
//			params.put("llAcc", ""+loc.getAccuracy());
//		}
//		
//		dao.setUsePost(true);
//		
//		if(!Constants.MOCK_DATA)
//		{
//			return dao.execute(null,"add", params, new Results<CheckinAddResponse>(){});
//		}
//		else
//		{
//			CheckinAddResponse res = new CheckinAddResponse();
//			MockCheckin mc = new MockCheckin();
//			res.setCheckin(mc.getCheckin());
//		
//			return res;
//		}
//	}
//	
//	
//	/**
//	 * http://developer.foursquare.com/docs/checkins/add.html
//	 * 
//	 * @param venueId
//	 * @param venue
//	 * @param shout
//	 * @param broadcast
//	 * @param lat
//	 * @param lng
//	 * @param llAcc
//	 * @param alt
//	 * @param altAcc
//	 * @return
//	 * @throws IOException
//	 * @throws HttpConnectionException
//	 * @throws InvalidCredentialsException
//	 */
//	public CheckinAddResponse addCheckin(String venueId, String venue, String shout, BroadcastType broadcast, 
//			Double lat, Double lng, double llAcc, float alt, Integer altAcc) 
//				throws HttpConnectionException, InvalidCredentialsException, Exception
//	{
//		// take care of all this later
//		Map<String, String>params = new HashMap<String,String>();
//		
//		if(venueId != null)
//		{
//			params.put("venueId", venueId);
//		}
//		if(venue != null)
//		{
//			params.put("venue", venue);
//		}
//		if(shout != null)
//		{
//			params.put("shout", shout);
//		}
//		if(broadcast != null)
//		{
//			params.put("broadcast", broadcast.getBroadcast());
//		}
//		
//		if(lat != null && lng != null)
//		{
//			params.put("ll", lat.toString()+","+lng.toString());
//		}
//		else
//		{
//			Log.e(Constants.TAG, "A valid lat/lng combo were not supplied.");
//		}
//		
//		params.put("llAcc", ""+llAcc);
//		
//		//TODO add alt and altAcc
//		
//		return dao.execute(null,"add", params, new Results<CheckinAddResponse>(){});
//	}
//	
//	
//
//	//there's a funky logic to define the value of the broadcast parameter
//	public static BroadcastType defineBroadcast(boolean isPublic, boolean followers, boolean facebook, boolean twitter)
//	{
//		StringBuilder sb = new StringBuilder();
//		if(isPublic)
//		{
//			sb.append("public");
//			if(followers)
//			{
//				sb.append(",followers");
//			}
//			if(facebook)
//			{
//				sb.append(",facebook");
//			}
//			if(twitter)
//			{
//				sb.append(",twitter");
//			}
//		}
//		else
//		{
//			sb.append("private");
//		}
//		
//		return new BroadcastType(sb.toString());
//	}
//	
//	public static class BroadcastType{
//		private final String broadcast;
//		public BroadcastType(String b)
//		{
//			broadcast = b;
//		}
//		public String getBroadcast()
//		{
//			return this.broadcast;
//		}
//	}
}
