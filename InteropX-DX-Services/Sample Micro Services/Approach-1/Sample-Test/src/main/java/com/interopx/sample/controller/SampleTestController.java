package com.interopx.sample.controller;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.protocol.HTTP;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.google.gson.Gson;
import com.interopx.sample.model.DataSource;

@RestController
@RequestMapping(value = { "/sample" })
public class SampleTestController {

	@RequestMapping(value = "/", method = RequestMethod.POST)
	public ResponseEntity<String> createDataSource(@RequestBody DataSource dataSource) {
		StringBuffer result = new StringBuffer();
		try {

			Gson gson = new Gson();
			StringEntity stringEntity = new StringEntity(gson.toJson(dataSource), ContentType.DEFAULT_TEXT);

			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpPost requestPost = new HttpPost("http://localhost:2211/datasource/create/");
			requestPost.addHeader("Content-Type", "application/json");
			requestPost.setEntity(stringEntity);

			HttpResponse response = httpClient.execute(requestPost);
			if (response.getStatusLine().getStatusCode() == 200) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

				String line;
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
			}

		} catch (Exception e) {
			System.err.println("error :" + e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
	}
	
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getAllDataSources() {
		StringBuffer result = new StringBuffer();
		try {
			HttpClient httpClient = HttpClientBuilder.create().build();
			HttpGet getRequest = new HttpGet("http://localhost:2211/datasource/list");
			HttpResponse response = httpClient.execute(getRequest);
			if (response.getStatusLine().getStatusCode() == 200) {
				BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
				String line;
				while ((line = rd.readLine()) != null) {
					result.append(line);
				}
			}
		} catch (Exception e) {
			System.err.println("error :" + e.getMessage());
			e.printStackTrace();
		}
		return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
	}
	
}
