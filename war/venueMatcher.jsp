<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1" 
    import="com.thepoofy.constants.VenueCategoryEnum"
    %>
<!DOCTYPE html>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<link rel="shortcut icon" href="assets/stylesheets/images/twitter-gwumpy.png" />
<script type="text/javascript" src="assets/javascripts/jquery-1.6.4.min.js"></script>
<script type="text/javascript" src="assets/javascripts/yqlgeo.js"></script>
<script type="text/javascript" src="http://maps.googleapis.com/maps/api/js?key=AIzaSyA8PcXaepll3qB9JajxXmUnT_G6UXWYgE4&sensor=true"></script>
  <style>
  	td{
  		border-bottom: 1px solid black;
  	}
  	#dataTable{
  		float:left;
  	}
  	#fsq_canvas{
  		float:left;
  		border: 1px solid black;
  		width: 400px;
  		height: 400px;
  	}
  	#yelp_canvas{
  		float:left;
  		border: 1px solid black;
  		width: 400px;
  		height: 400px;
  	}
  	section{
  		clear: both;
  	}
  	
  </style>
<script>
	var maps = [];
	var circles = [];
	var markers = [];
	
	var currentCenter;
	var mapNames = ["fsq", "yelp"];
  
  	$(document).ready(function()
  	{
  		initiate_geolocation();
  		
  		$("#searchButton").click(function(event){
			event.preventDefault();
			
			$("#results_list").empty();
			$("#yelp_results_list").empty();
			clearCircles();
        	//clearMarkers();
			
			$.ajax({
	        url: $("#searchForm").attr("action"),
	        data: $('#searchForm').serialize(),
	        dataType: 'json',
	        success: function(response) 
	        {
	        	drawSearchCircle("fsq");
	        	
	        	var data = response.data;
	        	if(data !== null && data.length > 0)
	        	{
	        		$.each(data, function(i, resp) 
					{
	        			var row = $(document.createElement("tr"));
	        			
	        			var fsqVenue = data[i].fsq;
	        			
	        			var td = renderFsqTd(fsqVenue);
	        			row.append(td);
	        			
	        			var td2 = renderYelpTd(data[i].yelp);
	        			
	        			row.append(td2);
	        			
	        			$("#results_list").append(row);
	        			
	        			drawMarker("fsq", fsqVenue.location.lat, fsqVenue.location.lng, fsqVenue.name);
					});
	        		
	        		fitMapToMarkers("fsq");
	    			
	        	}
	        	else
	         	{
	        		var list_data = '<ul data-json="" data-role="listview" data-inset="true" class="ui-listview ui-listview-inset ui-corner-all ui-shadow"><li data-theme="c" class="ui-btn ui-btn-icon-right ui-li-has-arrow ui-li ui-corner-top ui-btn-up-c"><h3 class="ui-li-heading">No data found.</h3></li></ul>';
	        		$(list_data).appendTo("#results_list");
	         	}
	         }
	        });
			
			$.ajax({
		        url: "/gwumpy/yelpsearch",
		        data: $('#searchForm').serialize(),
		        dataType: 'json',
		        success: function(response) 
		        {
		        	drawSearchCircle("yelp");
		        	
					var data = response.data;
					if(data !== null && data.length > 0)
		        	{
						
						$.each(data, function(i, biz)
						{
							var row = $(document.createElement("tr"));
							
							row.append(renderYelpTd(biz));
								
							$("#yelp_results_list").append(row);
							
							if(biz.location !== null)
							{
								try
								{
									drawMarker("yelp", biz.location.coordinate.latitude, biz.location.coordinate.longitude, biz.name);	
								}
								catch(e)
								{
									alert(e.message);
								}
							}
						});
						
						fitMapToMarkers("yelp");
		        	}
		        }
			});
			
			
  		});
  		
  		var myOptions = {
			zoom: 8,
			mapTypeId: google.maps.MapTypeId.ROADMAP
		};
  		
  		maps["fsq"] = new google.maps.Map(document.getElementById("fsq_canvas"), myOptions);
  		maps["yelp"] = new google.maps.Map(document.getElementById("yelp_canvas"), myOptions);
  		
  		

  	});
  
  	function initiate_geolocation() {
      if(navigator.geolocation) {
        navigator.geolocation.getCurrentPosition(handle_geolocation_query, handle_errors, {timeout: 10000});
      } else {
        yqlgeo.get('visitor', normalize_yql_response);
      }
    }
    
	function handle_errors(error) 
    {
    	
    }

    function normalize_yql_response(response) {
      if (response.error) {
        var error = { code : 0 };
        handle_error(error);
        return;
      }

		var position = {
	        coords : {
	          latitude: response.place.centroid.latitude,
	          longitude: response.place.centroid.longitude,
	          accuracy: response.place.centroid.accuracy
	        },
	        address : {
	          city: response.place.locality2.content,
	          region: response.place.admin1.content,
	          country: response.place.country.content
	        }
		};

		handle_geolocation_query(position);
    }

    function handle_geolocation_query(position) 
    {
		$('#latitude').val(position.coords.latitude);                                                                                     
		$('#longitude').val(position.coords.longitude);
		$('#accuracy').val(position.coords.accuracy);
		
		currentCenter = new google.maps.LatLng(position.coords.latitude,position.coords.longitude);
		
		$.each(mapNames, function(i, name){
			maps[name].setCenter(currentCenter);
		});
    }

    function drawSearchCircle(mapName)
    {
    	//alert("drawing search bounds for "+mapName);
    	if(maps[mapName] != null)
    	{
    		alert("drawing search bounds for "+mapName+" with bounds: "+$("#radius").val());
    	}
		
    	var populationOptions = {
			strokeColor: "#FF0000",
			strokeOpacity: 0.8,
			strokeWeight: 2,
			fillColor: "#FF0000",
			fillOpacity: 0.35,
			map: maps[mapName],
			center: currentCenter,
			radius: parseInt($("#radius").val(), 10)
		};
		
		circles[mapName] = new google.maps.Circle(populationOptions);
    }
    
    function clearCircles()
    {
    	$.each(mapNames, function(i, name){
    		if(circles[name] != null)
    		{
    			circles[name].setMap(null);	
    		}
		});
    }
    
    function renderFsqTd(venue)
    {
    	var td = $(document.createElement("td"));
    	
    	if(venue === null)
    	{
    		return td;
    	}
    	
    	td.append(document.createTextNode(venue.name));
	
		if(venue.location)
		{
			td.append(document.createElement("br"));
			td.append(document.createTextNode(venue.location.address));
			td.append(document.createElement("br"));
			td.append(document.createTextNode(venue.location.postalCode));
		}
		if(venue.categories[0] && venue.categories[0].name)
		{
			td.append(document.createElement("br"));
			td.append(document.createTextNode(venue.categories[0].name));
		}
		
		return td;
    }
    
    function renderYelpTd(yelpBiz)
    {
    	var td2 = $(document.createElement("td"));
		if(yelpBiz !== null)
		{
			td2.append(document.createTextNode(yelpBiz.name));
			
			if(yelpBiz.location)
			{
				td2.append(document.createElement("br"));
				td2.append(document.createTextNode(yelpBiz.location.address[0]));
				td2.append(document.createElement("br"));
				td2.append(document.createTextNode(yelpBiz.location.postal_code));
				td2.append(document.createElement("br"));
				td2.append(document.createTextNode("Lat: "+yelpBiz.location.coordinate.latitude));
				td2.append(document.createElement("br"));
				td2.append(document.createTextNode("Lng: "+yelpBiz.location.coordinate.longitude));
			}
			
			if(yelpBiz.categories[0] !== null && yelpBiz.categories[0][0] !== null)
			{
				td2.append(document.createElement("br"));
				td2.append(document.createTextNode(yelpBiz.categories[0][0]));
			}
			td2.append(document.createElement("br"));
			td2.append($(document.createElement("img"))
				.attr("src",yelpBiz.rating_img_url)
			);
			
		}
    	
		return td2;
    }
    
    function drawMarker(mapName, latitude, longitude, name)
    {
    	var myLatlng = new google.maps.LatLng(latitude, longitude);
    	
    	var marker = new google.maps.Marker({
    		position: myLatlng,
    		map: maps[mapName],
    		title:name
    	});
    	
    	if(markers[mapName] == null)
    	{
    		markers[mapName] = [];
    	}
    	markers[mapName].push(marker);
    }
    
    function fitMapToMarkers(mapName)
    {
    	var bounds = new google.maps.LatLngBounds();
    	
    	$.each(markers[mapName], function(i, marker){
    		bounds.extend(marker.getPosition());
    	});
    	
    	maps[mapName].fitBounds(bounds);
	}
    function clearMarkers()
    {
    	$.each(mapNames, function(i, name){
    		if(markers[name] != null)
    		{
    			$.each(markers[name], function(i, marker){
        			marker.setMap(null);
        		});	
    		}
    	});
    }
  
  </script>
  
<title>Insert title here</title>
</head>
<body>

	<form id="searchForm" action="/gwumpy/qasearch" >
		<label>Latitude: </label><input type="text" name="lat" id="latitude" /><br />
		<label>Longitude: </label><input type="text" name="lng" id="longitude" /><br />
		<label>Accuracy: </label><input type="text" name="llAcc" id="accuracy" /><br />
		<label>Radius: </label><input type="text" name="radius" id="radius" value="800" /><br />
		<label>Category: </label><select name="category" value="FOOD">
		<% for(VenueCategoryEnum cat : VenueCategoryEnum.values()){ %>
			<option value="<%= cat.name() %>"><%= cat.name() %></option>
		<% } %>
		</select><br />
		
		<input type="button" name="submit" value="Search" id="searchButton" />
	</form>

	<section>
		<div id="fsq_canvas"></div>
		
		<div id="yelp_canvas"></div>
	</section>	
	
	<section>
	<table id="dataTable">
		<thead>
			<tr>
				<td>Foursquare</td>
				<td>Yelp</td>
			</tr>
		</thead>
		<tbody id="results_list">
			
		</tbody>
	</table>
	<table>
		<thead>
			<tr>
				<td>Yelp</td>
			</tr>
		</thead>
		<tbody id="yelp_results_list"></tbody>
	</table>
	</section>
	
	

</body>
</html>