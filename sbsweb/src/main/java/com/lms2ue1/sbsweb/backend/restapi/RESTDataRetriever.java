package com.lms2ue1.sbsweb.backend.restapi;

import java.io.IOException;
import java.net.http.HttpResponse;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;

import kong.unirest.Unirest;
import okhttp3.OkHttpClient;

public class RESTDataRetriever {

	// ---- Methods for accessing the data from REST-API ----//
	public String fetchProjects() throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				okhttp3.Request request = new okhttp3.Request.Builder()
				  .url("http://localhost:3000/api/v1/project/list")
				  .method("GET", null).header("Authorization", "Bearer 123")
				  .build();
				okhttp3.Response response = client.newCall(request).execute();
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
