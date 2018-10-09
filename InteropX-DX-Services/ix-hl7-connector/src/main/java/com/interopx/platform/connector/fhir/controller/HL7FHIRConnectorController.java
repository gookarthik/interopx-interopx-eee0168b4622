package com.interopx.platform.connector.fhir.controller;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileWriter;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.interopx.platform.connector.fhir.model.FHIRAPIInfo;

@RestController
@RequestMapping(value={"/hl7connector"})
public class HL7FHIRConnectorController {
	
	 	@RequestMapping(value="/extract",method=RequestMethod.POST)
	    public ResponseEntity<String> extractDataFromHL7FHIR(@RequestBody FHIRAPIInfo apiInfo) {
	 		
	 		String serverBase = apiInfo.getEndPointUrl();
	 		
	 		//Get Metadata and read available resources
	 		List<String> fhirResources = getMetadataResources(serverBase+"/metadata?_format=json",apiInfo.getIsSecured(),apiInfo.getAccessToken());	 
	 		
	 		for(String resource : fhirResources) {
	 			
	 			//GET Request to the resource url
	 			String response = invokeURL(serverBase+"/"+resource.trim()+"?_format=json",apiInfo.getIsSecured(),apiInfo.getAccessToken());
	 			//Get Resource data and create ndjson files
	 			getResourceData(response, resource, apiInfo.getExtractionId());
	 			
	 			
	 		}
	 		
		 return new ResponseEntity<String>("Extraction completed...!", HttpStatus.OK);
	 }
	 	
	 	private List<String> getMetadataResources(String url, Boolean isSecured, String accessToken){
			List<String> fhirResources = new ArrayList<>();
			
			//GET Request to the url
	 		String response = invokeURL(url,isSecured,accessToken);
	 		
	 		try {
	 			if(response!=null) {
	 				JSONObject data = new JSONObject(response);
	 				if(data.has("resourceType")&&data.getString("resourceType").equalsIgnoreCase("Conformance")&&data.has("rest")) {
	 					
	 					JSONArray rest = data.getJSONArray("rest");
	 					if(rest.length()>0 && rest.getJSONObject(0).has("resource")) {
	 						JSONArray resouces = rest.getJSONObject(0).getJSONArray("resource");
	 						for(int i=0;i<resouces.length();i++) {
	 							JSONObject resourceObject = resouces.getJSONObject(i);
	 							if(resourceObject.has("type")) {
	 								fhirResources.add(resourceObject.getString("type"));
	 							}
	 						}
	 					}
	 					
	 				}
				
	 			}
			} catch (JSONException e) {
				System.err.println(e.getMessage());
			}
	 		
	 		return fhirResources;
	 	}
	 	
	 	//Invoke URL
	 	private String invokeURL(String url, Boolean isSecured, String accessToken) {
	 		StringBuffer result = new StringBuffer();
	 		try {
				HttpClient client = HttpClientBuilder.create().build();
				HttpGet request = new HttpGet(url);

				// add request header
				request.addHeader("Accept", "application/json+fhir");
				
				if(isSecured!=null&&isSecured)
					request.addHeader("Authorization","Bearer "+accessToken);

				 HttpResponse response = client.execute(request);
				 
				 if (response.getStatusLine().getStatusCode() == 200) {
						BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));

						
						String line = "";
						while ((line = rd.readLine()) != null) {
							result.append(line);
						}

						System.out.println(result.toString());
				 }
				
			}catch(Exception e) {
				System.err.println(e.getMessage());
			}
			
	 		return result.toString();
	 	}

	 	
	 // HTTP GET request
		private void getResourceData(String response, String resourceName, Integer extractionId) {
			try {

				if (response!=null) {
					
					JSONObject data = new JSONObject(response);

					if (data.has("entry")) {
						JSONArray entryList = data.getJSONArray("entry");
						if (entryList != null) {
							try {
								String contextPath = System.getProperty("catalina.base");
								File destDir = new File(contextPath + "/Extraction Data/"+extractionId+"/");

								if (!destDir.exists()) {
									destDir.mkdirs();
								}
								String fileName = resourceName + ".ndjson";

								File ndJsonFile = new File(destDir.getAbsolutePath() + "/" + fileName);

								FileWriter fw = new FileWriter(ndJsonFile.getAbsolutePath(), true); // the true will append
																									// the new data
								System.out.println("Writing data into file ..."+ndJsonFile.getAbsolutePath());
								for (int i = 0; i < entryList.length(); i++) {
									JSONObject entry = entryList.getJSONObject(i);
									if(entry.has("resource")) {
									StringBuilder exportData = new StringBuilder();

									exportData.append(entry.get("resource"));
									exportData.append('\n');
									fw.write(exportData.toString());
									}
								}
								System.out.println("Data added to the file : " + ndJsonFile.getAbsolutePath());
								fw.close();

							} catch (Exception e) {
								System.err.println(e.getMessage());
							}

						}
					}

				}

			} catch (Exception e) {
				System.err.println(e.getMessage());
			}

		}

}
