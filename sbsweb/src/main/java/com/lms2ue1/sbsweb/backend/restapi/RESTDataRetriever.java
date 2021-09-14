package com.lms2ue1.sbsweb.backend.restapi;

import java.io.IOException;

import org.springframework.stereotype.Component;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class RESTDataRetriever {
    
    /**
     * URL to connect to the adesso API.
     */
    private final String API_URL = "http://localhost:3000/api/v1/";
    
    /**
     * Retrieve the wanted data from the REST API.
     * 
     * @param url = The URL for the wanted data
     * @return the JSON String (Response body)
     * @throws IOException 
     */
    private String retrieveData(String url) throws IOException {
	OkHttpClient client = new OkHttpClient().newBuilder().build();
	Request request = new Request.Builder()
		.url(url)
		.method("GET", null)
		.header("Authorization", "Bearer 123").build();
	Response response = client.newCall(request).execute();
	//System.out.println(response);
	return response.body().string();
    }

    // ------------------------------------------------------//
    // ---- Methods for accessing the data from REST-API ----//
    // ------------------------------------------------------//
    
    public String fetchProjects() throws IOException {
	return retrieveData( API_URL + "project/list");
    }

    public String fetchContracts() {
	// TODO: Implement me!
	return null;
    }

    public String fetchBillingUnits() {
	// TODO: implement me!
	return null;
    }

    public String fetchBillingItems() {
	// TODO: implement me!
	return null;
    }

}
