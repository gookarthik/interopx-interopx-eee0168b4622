package com.interopx.platform.transformation.service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.ParseException;
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
import com.interopx.platform.repository.model.AllergyIntolerance;
import com.interopx.platform.repository.model.DataProcessingStatus;
import com.interopx.platform.repository.model.ExtractionDetails;
import com.interopx.platform.repository.service.AllergyIntoleranceService;
import com.interopx.platform.repository.resource.Allergy;
import com.interopx.platform.transformation.utill.DateUtility;

@Component
public class AllergyIntoleranceMapService {

	@Autowired
	private AllergyIntoleranceService allergyIntoleranceService;
	
	@Async("asyncExecutor")
	public Future<Long> processCSVFile(File file, ExtractionDetails ed) throws ParseException, IOException {

		try (Reader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			DataProcessingStatus dataProcessingStatus = new DataProcessingStatus(true, false, false, false);
			for (CSVRecord csvRecord : csvParser) {

				// Read CSV values and assigned to appropriate InteropX model class
				Allergy allergy = new Allergy();
				// Accessing values by Header names
				allergy.setPatientId(csvRecord.get("patientID"));
				allergy.setEncId(csvRecord.get("EncID"));
				allergy.setAllergyType(csvRecord.get("allergy_type"));
				allergy.setAllergyCode(csvRecord.get("Allergy_Code"));
				allergy.setCodeSystem(csvRecord.get("CodeSystem"));
				allergy.setAllergyDescription(csvRecord.get("allergy_description"));

				if (!csvRecord.get("allergy_start_date").isEmpty() && csvRecord.get("allergy_start_date") != null) {
					Date allergyStartDate = DateUtility.convertStringToDate(csvRecord.get("allergy_start_date"));
					allergy.setAllergyStartDate(allergyStartDate);
				}
				if (csvRecord.get("allergy_stopped_date") != null && !csvRecord.get("allergy_stopped_date").isEmpty()) {
					Date allergyEndDate = DateUtility.convertStringToDate(csvRecord.get("allergy_stopped_date"));
					allergy.setAllergyStoppedDate(allergyEndDate);
				}
				if (csvRecord.get("allergy_create_timestamp") != null
						&& !csvRecord.get("allergy_create_timestamp").isEmpty()) {
					Date allergyCreate = DateUtility.convertStringToDate(csvRecord.get("allergy_create_timestamp"));
					allergy.setAllergyCreateTimestamp(allergyCreate);
				}
				allergy.setStatus(csvRecord.get("Status"));
				allergy.setAllergyReaction(csvRecord.get("allergy_reaction"));
				allergy.setAllergySeverity(csvRecord.get("allergy_severity"));

				// Convert the model to string and persist the record into table
				AllergyIntolerance allergyResource = new AllergyIntolerance();
				allergyResource.setExtractionTaskId(ed.getExtractionId());
				allergyResource.setDataSourceId(ed.getDataSourceId());
				allergyResource.setDataSourceName(ed.getDataSourceName());

				ObjectMapper mapper = new ObjectMapper();
				String allergyString = mapper.writeValueAsString(allergy);
				allergyResource.setData(allergyString);
				allergyResource.setDataProcessingStatus(dataProcessingStatus.toString());
				allergyIntoleranceService.saveOrUpdateAllergy(allergyResource);
			}
		}
		return new AsyncResult<>(System.nanoTime());
	}
}
