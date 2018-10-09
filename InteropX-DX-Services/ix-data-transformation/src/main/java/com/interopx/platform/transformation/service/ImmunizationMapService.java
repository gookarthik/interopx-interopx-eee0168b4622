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
import com.interopx.platform.repository.model.ExtractionDetails;
import com.interopx.platform.repository.model.ImmunizationResource;
import com.interopx.platform.repository.service.ImmunizationService;
import com.interopx.platform.repository.resource.Immunization;
import com.interopx.platform.transformation.utill.DateUtility;

@Component
public class ImmunizationMapService {

	@Autowired
	private ImmunizationService immunizationService;


	@Async("asyncExecutor")
	public Future<Long> processCSVFile(File file, ExtractionDetails ed) throws IOException, ParseException {

		try (Reader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			DataProcessingStatus dataProcessingStatus = new DataProcessingStatus(true, false, false, false);
			for (CSVRecord csvRecord : csvParser) {

				// Read CSV values and assigned to appropriate InteropX model class
				Immunization immunization = new Immunization();
				// Accessing values by Header names
				immunization.setPatientId(csvRecord.get("patientID"));
				immunization.setEncId(csvRecord.get("EncID"));
				immunization.setImmCode(csvRecord.get("ImmCode"));
				immunization.setCodeSystem(csvRecord.get("CodeSystem"));
				immunization.setDescription(csvRecord.get("description"));
				immunization.setImmSite(csvRecord.get("imm_site"));

				if (csvRecord.get("completed_date") != null && !csvRecord.get("completed_date").isEmpty()) {
					Date completedDate = DateUtility.convertStringToDate(csvRecord.get("completed_date"));
					immunization.setCompletedDate(completedDate);
				}
				immunization.setLotNumber(csvRecord.get("lot_number"));
				immunization.setManufacturer(csvRecord.get("manufacturer"));
				immunization.setRefused(csvRecord.get("Refused/Cancelled/Administered"));
				immunization.setNotes(csvRecord.get("notes"));
				// Convert the model to string and persist the record into table
				ImmunizationResource immunizationResource = new ImmunizationResource();
				immunizationResource.setExtractionTaskId(ed.getExtractionId());
				immunizationResource.setDataSourceId(ed.getDataSourceId());
				immunizationResource.setDataSourceName(ed.getDataSourceName());

				ObjectMapper mapper = new ObjectMapper();
				String patientString = mapper.writeValueAsString(immunization);
				immunizationResource.setData(patientString);
				immunizationResource.setDataProcessingStatus(dataProcessingStatus.toString());
				immunizationService.saveOrUpdateImmunization(immunizationResource);
			}
		}
		return new AsyncResult<>(System.nanoTime());
	}
}
