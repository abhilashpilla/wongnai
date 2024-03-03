package com.lineman.wongnai.order.service;

import org.springframework.stereotype.Service;


import com.google.maps.DirectionsApi.RouteRestriction;
import com.google.maps.DistanceMatrixApi;
import com.google.maps.DistanceMatrixApiRequest;
import com.google.maps.GeoApiContext;
import com.google.maps.errors.ApiException;
import com.google.maps.model.DistanceMatrix;
import com.google.maps.model.DistanceMatrixElement;
import com.google.maps.model.DistanceMatrixRow;
import com.google.maps.model.TravelMode;


import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class GoogleMapsApiService {

	
	public void estimateDistance() {
		GeoApiContext context = new GeoApiContext.Builder().apiKey("AIzaSyBwGGZjpf6_shy4MSOuZmPWAyCC-uGhFtw").build();
	    try {
	        DistanceMatrixApiRequest req = DistanceMatrixApi.newRequest(context); 
	        DistanceMatrixRow[] rows = req.origins("57WC+WPQ - Downtown Dubai - Dubai - United Arab Emirates",
                    "Crescent Rd - The Palm Jumeirah - Dubai - United Arab Emirates")
	                .destinations("25.1225786,55.2072704")
	                
	                
	                .mode(TravelMode.DRIVING)
	                .language("en-EN")
	                .await().rows;
	        System.out.println(rows.length);
	        for(DistanceMatrixRow row: rows) {
	        	System.out.println("row---------");
	        	
	        	
	        	//DistanceMatrixElement[] ele = row.elements;
	        	for(DistanceMatrixElement ele: row.elements) {
	        		System.out.println(ele.distance.inMeters);
	        		System.out.println(ele.duration.inSeconds);
	        		
	        	}
	        	//ele.
	        }
	        System.out.println();
	        //Do something with result here
	        // ....
	    } catch(ApiException e){
	        //output += this.printError(e);
	    } catch(Exception e){
	        System.out.println(e.getMessage());
	    }   
	}
}
