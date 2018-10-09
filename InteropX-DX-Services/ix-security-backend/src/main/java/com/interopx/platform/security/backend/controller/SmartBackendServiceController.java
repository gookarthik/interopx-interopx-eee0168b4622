package com.interopx.platform.security.backend.controller;

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.InputStreamReader;
import java.security.Key;
import java.security.KeyFactory;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

import javax.ws.rs.FormParam;

import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClientBuilder;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.interopx.platform.security.backend.model.SmartBackendInfo;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;

@RestController
@RequestMapping(value={"/smartbackend"})
public class SmartBackendServiceController {
	
	@RequestMapping(value="/",method=RequestMethod.POST)
    public ResponseEntity<String> getAccessTokenUsingSmartBackend(@FormParam("backendInfo") SmartBackendInfo backendInfo, @FormParam("file") MultipartFile file) {
		StringBuffer result = new StringBuffer();
		try {
	    	String aud = backendInfo.getAud();
	    	String client_assertion = getCompactJWS(backendInfo,file);
	        String json = "grant_type=client_credentials&scope=system/:resourceType.(read|write|*)&client_assertion_type=urn:ietf:params:oauth:client-assertion-type:jwt-bearer&client_assertion="+client_assertion;
	        StringEntity entity = new StringEntity(json,ContentType.DEFAULT_TEXT);
	        
	        HttpClient httpClient = HttpClientBuilder.create().build();
	        HttpPost requestPost = new HttpPost(aud);
	        requestPost.setEntity(entity);
	        
	        HttpResponse response = httpClient.execute(requestPost);
	        if(response.getStatusLine().getStatusCode()==200) {
	        BufferedReader rd = new BufferedReader(
                  new InputStreamReader(response.getEntity().getContent()));

	        
	        String line;
	        while ((line = rd.readLine()) != null) {
	        	result.append(line);
	        }	
	        }
	        
	    	}catch(Exception e) {
	    		System.err.println("error :"+e.getMessage());
	    		e.printStackTrace();
	    	}
		return new ResponseEntity<String>(result.toString(), HttpStatus.OK);
	}
	
	public String getCompactJWS(SmartBackendInfo backendInfo, MultipartFile file) throws Exception {
    	
    	String issuer = backendInfo.getIss();
    	String subject = backendInfo.getSub();
    	String aud = backendInfo.getAud();
    	GregorianCalendar cal = new GregorianCalendar();
    	cal.setTime(new Date());
    	cal.add(Calendar.MINUTE, 2);
    	Date exp = cal.getTime();
    	DataInputStream dis = new DataInputStream(file.getInputStream());
    	byte[] keyBytes = new byte[(int)file.getSize()];
    	dis.readFully(keyBytes);
    	dis.close();
    	PKCS8EncodedKeySpec spec = new PKCS8EncodedKeySpec(keyBytes);
    	Key key = KeyFactory.getInstance("RSA").generatePrivate(spec);
    	String compactJws = Jwts.builder()
    			  .setSubject(subject)
    			  .setIssuedAt(new Date())
    			  .setIssuer(issuer)
    			  .setAudience(aud)
    			  .setExpiration(exp)
    			  .signWith(SignatureAlgorithm.RS256, key)
    			  .compact();
    	
		return compactJws;
    	
    }

}
