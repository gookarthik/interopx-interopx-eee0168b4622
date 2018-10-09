package com.interopx.platform.agent;

import java.io.File;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import com.interopx.platform.agent.model.ExtractionInfo;
import com.interopx.platform.agent.util.DBConnector;
import com.interopx.platform.agent.util.SFTPConnector;

@Component
@PropertySource("classpath:application.properties")
public class InteropXAgent {
	
	@Value("${serverurl}")
	private String serverURL;
	
	@Value("${jdbcurl}")
	private String jdbcURL;
	
	@Value("${db.username}")
	private String dbUserName;
	
	@Value("${db.password}")
	private String dbPassword;
	
	@Value("${sqlfile.location}")
	private String sqlFileLocation;
	
	@Value("${exportcsv.location}")
	private String exportCSVLocation;
	
	@Value("${sftp.host}")
	private String sftpHOST;
	
	@Value("${sftp.port}")
	private Integer sftpPort;
	
	
	@Value("${sftp.user}")
	private String sftpUser;
	
	@Value("${sftp.password}")
	private String sftpPassword;
	
	
	@Value("${sftp.privatekey}")
	private String sftpPrivateKey;
	
	@Autowired
	private RestTemplate restTemplate;
	
	@Scheduled(cron = "1 * * * * ?")
	public void scheduleTaskWithCronExpression() throws Exception {
		String status = "";
		//GET triggered extractions info from server
	    ExtractionInfo[] list = restTemplate.getForObject(serverURL, ExtractionInfo[].class);
	    
	    for(ExtractionInfo e : list) {
	    	
	    	//update status to the server
	    	 restTemplate.put(serverURL+"?etId="+e.getExtractionId()+"&status=In Progress", String.class);
	    	
	    	//create folder with extraction id
	    	File exportDir = new File(exportCSVLocation+"/"+e.getExtractionId());
	    	if(!exportDir.exists()) {
	    		exportDir.mkdirs();
	    	}
	    	
	    	//Execute SQL Scripts to generate CSV files
	    	Boolean sqlProcessed = DBConnector.generateCSV(jdbcURL, dbUserName, dbPassword, sqlFileLocation,exportDir.getPath());
	    	
	    	
	    	if(sqlProcessed) {
	    		
	    		String sftpDir = "/"+e.getFilesLocation().replaceAll("\\\\", "/");
	    		String localDir = exportCSVLocation+"/"+e.getExtractionId();
	    		//Upload generated CSV files to SFTP server
	    		SFTPConnector.uploadFiles(sftpHOST, sftpPort, sftpUser, sftpPassword, sftpPrivateKey, sftpDir, localDir);
	    		status="Completed";
	    	}else {
	    		status="Failed";
	    		throw new Exception("Failed to export CSV files");
	    	}
	    	
	    	
	    	//update status to the server
	    	System.out.println("update status : "+status);
	    	restTemplate.put(serverURL+"?etId="+e.getExtractionId()+"&status="+status, String.class);
	    }
		
	}

}
