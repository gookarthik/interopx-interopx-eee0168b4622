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
import com.interopx.platform.repository.model.PatientResource;
import com.interopx.platform.repository.service.PatientService;
import com.interopx.platform.repository.resource.Patient;

@Component
public class PatientMapService {

	@Autowired
	private PatientService patientService;

	@Async("asyncExecutor")
	public Future<Long> processCSVFile(File file, ExtractionDetails ed) throws IOException {

		try (Reader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			DataProcessingStatus dataProcessingStatus = new DataProcessingStatus(true, false, false, false);
			for (CSVRecord csvRecord : csvParser) {

				// Read CSV values and assigned to appropriate InteropX model class
				Patient patient = new Patient();
				// Accessing values by Header names
				patient.setPatientId(csvRecord.get("PatientId"));
				patient.setEncId(csvRecord.get("EncID"));
				patient.setHAOID(csvRecord.get("HAOID"));
				patient.setFirstName(csvRecord.get("firstname"));
				patient.setLastName(csvRecord.get("lastname"));
				patient.setMiddleInitial(csvRecord.get("middleinitial"));
				patient.setDateOfBirth(csvRecord.get("dateofbirth"));
				patient.setLanguage(csvRecord.get("Language"));
				patient.setRaceCode(csvRecord.get("RaceCode"));
				patient.setEthnicityCode(csvRecord.get("EthnicityCode"));
				patient.setAdminGender(csvRecord.get("AdminGender"));
				patient.setAliveIndicator(csvRecord.get("AliveIndicator"));
				patient.setDeceasedDateTime(csvRecord.get("DeceasedDateTime"));
				patient.setStreetAddress(csvRecord.get("StreetAddress"));
				patient.setCity(csvRecord.get("city"));
				patient.setState(csvRecord.get("state"));
				patient.setZip(csvRecord.get("zip"));
				patient.setTelecom(csvRecord.get("Telecom"));
				patient.setSiteOID(csvRecord.get("Site_OID"));
				patient.setPatIdExtension(csvRecord.get("PatIDExtension"));

				// Convert the model to string and persist the record into table
				PatientResource patientResource = new PatientResource();
				patientResource.setExtractionTaskId(ed.getExtractionId());
				patientResource.setDataSourceId(ed.getDataSourceId());
				patientResource.setDataSourceName(ed.getDataSourceName());
				patientResource.setActualPatientId(patient.getPatientId());
				patientResource.setFirstName(patient.getFirstName());
				patientResource.setLastName(patient.getLastName());
				patientResource.setGender(patient.getAdminGender());
				patientResource.setDob(patient.getDateOfBirth());

				ObjectMapper mapper = new ObjectMapper();
				String patientString = mapper.writeValueAsString(patient);
				patientResource.setData(patientString);
				patientResource.setLastUpdatedTs(new Date());
				patientResource.setDataProcessingStatus(dataProcessingStatus.toString());
				patientService.saveOrUpdatePatient(patientResource);
			}
		}
		return new AsyncResult<>(System.nanoTime());
	}
}
