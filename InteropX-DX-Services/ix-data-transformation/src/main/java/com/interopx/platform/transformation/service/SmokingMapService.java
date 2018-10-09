package com.interopx.platform.transformation.service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;
import java.util.concurrent.Future;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.interopx.platform.repository.model.DataProcessingStatus;
import com.interopx.platform.repository.model.ExtractionDetails;
import com.interopx.platform.repository.model.SmokingResource;
import com.interopx.platform.repository.service.SmokingService;
import com.interopx.platform.repository.resource.Smoking;
import com.interopx.platform.transformation.utill.DateUtility;

@Component
public class SmokingMapService {

	@Autowired
	private SmokingService smokingService;

	@Async("asyncExecutor")
	public Future<Long> processCSVFile(File file, ExtractionDetails ed) throws IOException {

		try (Reader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			DataProcessingStatus dataProcessingStatus = new DataProcessingStatus(true, false, false, false);
			for (CSVRecord csvRecord : csvParser) {
				Smoking smoking = new Smoking();
				// Accessing values by Header names
				smoking.setPatientId(csvRecord.get("patientID"));
				smoking.setEncId(csvRecord.get("encID"));
				smoking.setSmokingStatusCode(csvRecord.get("smoking_Status_Code"));
				smoking.setSmokingStatusCodeSystem(csvRecord.get("smoking_status_code_code_system"));
				smoking.setSmokingStatusDisplayName(csvRecord.get("smoking_Status_display_name"));

				if (csvRecord.get("start_time") != null && !csvRecord.get("start_time").isEmpty()) {
					Date startTime = DateUtility.convertStringToDate(csvRecord.get("start_time"));
					smoking.setStartTime(startTime);
				}
				if (csvRecord.get("end_date") != null && !csvRecord.get("end_date").isEmpty()) {
					Date endDate = DateUtility.convertStringToDate(csvRecord.get("end_date"));
					smoking.setEndDate(endDate);
				}
				// Convert the model to string and persist the record into table
				SmokingResource smokingResource = new SmokingResource();
				smokingResource.setExtractionTaskId(ed.getExtractionId());
				smokingResource.setDataSourceId(ed.getDataSourceId());
				smokingResource.setDataSourceName(ed.getDataSourceName());

				ObjectMapper mapper = new ObjectMapper();
				String patientString = mapper.writeValueAsString(smoking);
				smokingResource.setData(patientString);
				smokingResource.setDataProcessingStatus(dataProcessingStatus.toString());
				smokingService.saveOrUpdateSmoke(smokingResource);
			}
		}
		return new AsyncResult<>(System.nanoTime());
	}
}
