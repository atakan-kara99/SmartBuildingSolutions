package com.lms2ue1.sbsweb.backend.restapi;

import java.io.IOException;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;


public class RESTDataRetriever {
    
    /**
     * URL to connect to the adesso API.
     */
    private final String API_URL = "http://localhost:3000/api/v1";

    // ---- Methods for accessing the data from REST-API ----//
    public String fetchProjects() throws IOException {
	OkHttpClient client = new OkHttpClient().newBuilder().build();
	Request request = new Request.Builder()
		.url(API_URL + "/project/list")
		.method("GET", null)
		.header("Authorization", "Bearer 123").build();
	Response response = client.newCall(request).execute();
	System.out.println(response);
	return response.body().string();
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
