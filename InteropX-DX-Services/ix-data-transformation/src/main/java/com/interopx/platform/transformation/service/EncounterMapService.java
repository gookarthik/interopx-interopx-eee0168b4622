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
import com.interopx.platform.repository.model.DataProcessingStatus;
import com.interopx.platform.repository.model.EncounterResource;
import com.interopx.platform.repository.model.ExtractionDetails;
import com.interopx.platform.repository.service.EncounterService;
import com.interopx.platform.repository.resource.Encounter;
import com.interopx.platform.transformation.utill.DateUtility;

@Component
public class EncounterMapService {

	@Autowired
	private EncounterService encounterService;

	@Async("asyncExecutor")
	public Future<Long> processCSVFile(File file, ExtractionDetails ed) throws IOException, ParseException {

		try (Reader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			DataProcessingStatus dataProcessingStatus = new DataProcessingStatus(true, false, false, false);
			for (CSVRecord csvRecord : csvParser) {

				// Read CSV values and assigned to appropriate InteropX model class
				Encounter encounter = new Encounter();
				// Accessing values by Header names
				encounter.setTencounterid((csvRecord.get("tencounterid")));
				encounter.setPatientId((csvRecord.get("patientID")));
				encounter.setEncId((csvRecord.get("EncID")));
				encounter.setProviderId((csvRecord.get("ProviderID")));
				encounter.setProviderFullName((csvRecord.get("ProvFullName")));

				if (!csvRecord.get("StartTime").isEmpty() && csvRecord.get("StartTime") != null) {
					Date startTime = DateUtility.convertStringToDate(csvRecord.get("StartTime"));
					encounter.setStartTime(startTime);
				}
				if (!csvRecord.get("EndTime").isEmpty() && csvRecord.get("EndTime") != null) {
					Date endTime = DateUtility.convertStringToDate(csvRecord.get("EndTime"));
					encounter.setEndTime(endTime);
				}
				encounter.setEncounterCode((csvRecord.get("Encounter_Code")));
				encounter.setEncounterCodeDisplayName((csvRecord.get("EncounterCode_CodeSystem")));
				encounter.setEncounterCodeSystem((csvRecord.get("EncounterCode_DisplayName")));
				encounter.setEncounterLocationId((csvRecord.get("enc_location_id")));

				// Convert the model to string and persist the record into table
				EncounterResource encounterResource = new EncounterResource();
				encounterResource.setExtractionTaskId(ed.getExtractionId());
				encounterResource.setDataSourceId(ed.getDataSourceId());
				encounterResource.setDataSourceName(ed.getDataSourceName());

				ObjectMapper mapper = new ObjectMapper();
				String patientString = mapper.writeValueAsString(encounter);
				encounterResource.setData(patientString);
				encounterResource.setDataProcessingStatus(dataProcessingStatus.toString());
				encounterService.saveOrUpdateEncounter(encounterResource);
			}
		}
		return new AsyncResult<>(System.nanoTime());
	}
}
