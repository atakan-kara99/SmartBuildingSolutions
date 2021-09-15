package com.lms2ue1.sbsweb.backend.restapi;

import org.apache.catalina.connector.Request;
import org.apache.catalina.connector.Response;

import okhttp3.OkHttpClient;

public class RESTDataRetriever {

	// ---- Methods for accessing the data from REST-API ----//
	public String fetchProjects() {
		/*OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
		okhttp3.Request request = new Request.Builder()
				  .url("localhost:3001/api/v1/project/list")
				  .method("GET", null)
				  .build();
		okhttp3.Response response = client.newCall(request).execute();*/
	    // TODO: Implement me!
		return null;
	}

	public String fetchContracts() {
		/*OkHttpClient client = new OkHttpClient().newBuilder()
				  .build();
				Request request = new Request.Builder()
				  .url("localhost:3001/api/v1/contracts/:projectId")
				  .method("GET", null)
				  *
				  .build();
				Response response = client.newCall(request).execute();*/
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
