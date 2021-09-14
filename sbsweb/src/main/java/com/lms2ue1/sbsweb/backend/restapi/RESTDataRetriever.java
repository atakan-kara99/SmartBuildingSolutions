package com.lms2ue1.sbsweb.backend.restapi;

import java.io.IOException;

import org.springframework.stereotype.Component;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

@Component
public class RESTDataRetriever {

	// ---- Constant api url value ----//
	private final String API_URL = "http://localhost:3000/api/v1";

	// ---- Methods for accessing the data from REST-API ----//
	public String fetchProjects() throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		Request request = new Request.Builder().url(API_URL + "/project/list").method("GET", null)
				.header("Authorization", "Bearer 123").build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	public String fetchContracts(long projectID) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		Request request = new Request.Builder().url(API_URL + "/contracts/:" + projectID).method("GET", null)
				.header("Authorization", "Bearer 123").build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	public String fetchBillingUnits(long contractID) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		Request request = new Request.Builder().url(API_URL + "/billingmodel/loadAndParseForContractConfiguration?contractId=" + contractID).method("GET", null)
				.header("Authorization", "Bearer 123").build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

	public String fetchBillingItems(long contractID) throws IOException {
		OkHttpClient client = new OkHttpClient().newBuilder().build();
		Request request = new Request.Builder().url(API_URL + "/contractCompletion/all/:" + contractID).method("GET", null)
				.header("Authorization", "Bearer 123").build();
		Response response = client.newCall(request).execute();
		return response.body().string();
	}

}
