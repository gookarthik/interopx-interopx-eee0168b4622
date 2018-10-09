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
import com.interopx.platform.repository.model.LabResultsResource;
import com.interopx.platform.repository.service.LabResultsService;
import com.interopx.platform.repository.resource.LabResults;
import com.interopx.platform.transformation.utill.DateUtility;

@Component
public class LabResultsMapService {

	@Autowired
	private LabResultsService labResultsService;

	@Async("asyncExecutor")
	public Future<Long> processCSVFile(File file, ExtractionDetails ed) throws IOException, ParseException {

		try (Reader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			DataProcessingStatus dataProcessingStatus = new DataProcessingStatus(true, false, false, false);
			for (CSVRecord csvRecord : csvParser) {

				// Read CSV values and assigned to appropriate InteropX model class
				LabResults labResults = new LabResults();
				// Accessing values by Header names
				labResults.setPatientId(csvRecord.get("patientID"));
				labResults.setEncId(csvRecord.get("EncID"));
				labResults.setTestPanel(csvRecord.get("test_panel"));
				labResults.setTestPanelSystem(csvRecord.get("test_panel_system"));
				labResults.setTestPanelName(csvRecord.get("test_panel_name"));
				labResults.setLabType(csvRecord.get("lab_type"));
				labResults.setLabResultCodeSystem(csvRecord.get("LabResult_CodeSystem"));
				labResults.setOriginalName(csvRecord.get("original_name"));
				if (csvRecord.get("collection_date") != null && !csvRecord.get("collection_date").isEmpty()) {
					Date collectionDate = DateUtility.convertStringToDate(csvRecord.get("collection_date"));
					labResults.setCollectionDate(collectionDate);
				}
				labResults.setResultType(csvRecord.get("result_type"));
				labResults.setResultValue(csvRecord.get("result_value"));
				labResults.setResultUnits(csvRecord.get("result_units"));
				labResults.setResultMin(csvRecord.get("result_min"));
				labResults.setResultMax(csvRecord.get("result_max"));

				// Convert the model to string and persist the record into table
				LabResultsResource labResultsResource = new LabResultsResource();
				labResultsResource.setExtractionTaskId(ed.getExtractionId());
				labResultsResource.setDataSourceId(ed.getDataSourceId());
				labResultsResource.setDataSourceName(ed.getDataSourceName());

				ObjectMapper mapper = new ObjectMapper();
				String patientString = mapper.writeValueAsString(labResults);
				labResultsResource.setData(patientString);
				labResultsResource.setDataProcessingStatus(dataProcessingStatus.toString());
				labResultsService.saveOrUpdateLabResults(labResultsResource);
			}
		}
		return new AsyncResult<>(System.nanoTime());
	}
}
