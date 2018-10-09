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
import com.interopx.platform.repository.model.MedicationResource;
import com.interopx.platform.repository.service.MedicationService;
import com.interopx.platform.repository.resource.Medication;
import com.interopx.platform.transformation.utill.DateUtility;

@Component
public class MedicationMapService {

	@Autowired
	private MedicationService medicationService;

	@Async("asyncExecutor")
	public Future<Long> processCSVFile(File file, ExtractionDetails ed) throws IOException, ParseException {

		try (Reader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			DataProcessingStatus dataProcessingStatus = new DataProcessingStatus(true, false, false, false);
			for (CSVRecord csvRecord : csvParser) {

				// Read CSV values and assigned to appropriate InteropX model class
				Medication medication = new Medication();
				// Accessing values by Header names
				medication.setPatientId(csvRecord.get("patientID"));
				medication.setEncId(csvRecord.get("EncID"));
				medication.setType(csvRecord.get("Type"));
				medication.setStatus(csvRecord.get("status"));
				medication.setCode(csvRecord.get("Code"));
				medication.setCodeSystem(csvRecord.get("CodeSystem"));
				medication.setMedicationName(csvRecord.get("medication_name"));

				if (csvRecord.get("prescription_date") != null && !csvRecord.get("prescription_date").isEmpty()) {
					Date prescriptionDate = DateUtility.convertStringToDate(csvRecord.get("prescription_date"));
					medication.setPrescriptionDate(prescriptionDate);
				}
				if (csvRecord.get("stop_date") != null && !csvRecord.get("stop_date").isEmpty()) {
					Date stopDate = DateUtility.convertStringToDate(csvRecord.get("stop_date"));
					medication.setStopDate(stopDate);
				}
				medication.setFrequency(csvRecord.get("Frequency"));
				medication.setDosage(csvRecord.get("Dosage"));
				medication.setProductForm(csvRecord.get("ProductForm"));
				medication.setRouteCode(csvRecord.get("RouteCode"));

				// Convert the model to string and persist the record into table
				MedicationResource medicationResource = new MedicationResource();
				medicationResource.setExtractionTaskId(ed.getExtractionId());
				medicationResource.setDataSourceId(ed.getDataSourceId());
				medicationResource.setDataSourceName(ed.getDataSourceName());

				ObjectMapper mapper = new ObjectMapper();
				String patientString = mapper.writeValueAsString(medication);
				medicationResource.setData(patientString);
				medicationResource.setDataProcessingStatus(dataProcessingStatus.toString());
				medicationService.saveOrUpdateMedication(medicationResource);
			}
		}
		return new AsyncResult<>(System.nanoTime());
	}
}
