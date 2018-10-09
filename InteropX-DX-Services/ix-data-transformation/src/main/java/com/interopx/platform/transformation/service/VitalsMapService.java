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
import com.interopx.platform.repository.model.VitalSigns;
import com.interopx.platform.repository.service.VitalsService;
import com.interopx.platform.repository.resource.Vitals;
import com.interopx.platform.transformation.utill.DateUtility;

@Component
public class VitalsMapService {

	@Autowired
	private VitalsService vitalsService;

	@Async("asyncExecutor")
	public Future<Long> processCSVFile(File file, ExtractionDetails ed) throws IOException {

		try (Reader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			DataProcessingStatus dataProcessingStatus = new DataProcessingStatus(true, false, false, false);
			for (CSVRecord csvRecord : csvParser) {
				Vitals vitals = new Vitals();
				// Accessing values by Header names
				vitals.setPatientId(csvRecord.get("patientID"));
				vitals.setEncId(csvRecord.get("EncID"));
				vitals.setVitalCode(csvRecord.get("VitalCode"));
				vitals.setCodeSystem(csvRecord.get("CodeSystem"));
				vitals.setDescription(csvRecord.get("Description"));
				vitals.setVitals(csvRecord.get("Vitals"));
				vitals.setUnits(csvRecord.get("Units"));

				if (csvRecord.get("vitals_date") != null && !csvRecord.get("vitals_date").isEmpty()) {
					Date vitalsDate = DateUtility.convertStringToDate(csvRecord.get("vitals_date"));
					vitals.setVitalsDate(vitalsDate);
				}
				// Convert the model to string and persist the record into table
				VitalSigns vitalsResource = new VitalSigns();
				vitalsResource.setExtractionTaskId(ed.getExtractionId());
				vitalsResource.setDataSourceId(ed.getDataSourceId());
				vitalsResource.setDataSourceName(ed.getDataSourceName());

				ObjectMapper mapper = new ObjectMapper();
				String patientString = mapper.writeValueAsString(vitals);
				vitalsResource.setData(patientString);
				vitalsResource.setDataProcessingStatus(dataProcessingStatus.toString());
				vitalsService.saveOrUpdateVital(vitalsResource);
			}
		}
		return new AsyncResult<>(System.nanoTime());
	}
}
