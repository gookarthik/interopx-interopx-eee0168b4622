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
import com.interopx.platform.repository.model.ConditionResource;
import com.interopx.platform.repository.model.DataProcessingStatus;
import com.interopx.platform.repository.model.ExtractionDetails;
import com.interopx.platform.repository.service.ConditionService;
import com.interopx.platform.repository.resource.Problem;
import com.interopx.platform.transformation.utill.DateUtility;

@Component
public class ConditionMapService {

	@Autowired
	private ConditionService conditionService;

	@Async("asyncExecutor")
	public Future<Long> processCSVFile(File file, ExtractionDetails ed) throws IOException, ParseException {

		try (Reader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			DataProcessingStatus dataProcessingStatus = new DataProcessingStatus(true, false, false, false);
			for (CSVRecord csvRecord : csvParser) {

				// Read CSV values and assigned to appropriate InteropX model class
				Problem problem = new Problem();
				// Accessing values by Header names
				problem.setPatientId(csvRecord.get("patientID"));
				problem.setEncId(csvRecord.get("EncID"));
				problem.setProblemCategory(csvRecord.get("ProblemCategory"));
				problem.setProblemCode(csvRecord.get("ProblemCode"));
				problem.setCodeSystem(csvRecord.get("CodeSystem"));
				problem.setProblemDescription(csvRecord.get("problem_description"));

				if (csvRecord.get("onset_date") != null && !csvRecord.get("onset_date").isEmpty()) {
					Date onsetDate = DateUtility.convertStringToDate(csvRecord.get("onset_date"));
					problem.setOnsetDate(onsetDate);
				}
				if (csvRecord.get("resolved_date") != null && !csvRecord.get("resolved_date").isEmpty()) {
					Date resolvedDate = DateUtility.convertStringToDate(csvRecord.get("resolved_date"));
					problem.setResolvedDate(resolvedDate);
				}
				if (csvRecord.get("create_timestamp") != null && !csvRecord.get("create_timestamp").isEmpty()) {
					Date createTimestamp = DateUtility.convertStringToDate(csvRecord.get("create_timestamp"));
					problem.setCreateTimestamp(createTimestamp);
				}
				problem.setProblemStatus(csvRecord.get("problem_status"));

				// Convert the model to string and persist the record into table
				ConditionResource conditionResource = new ConditionResource();
				conditionResource.setExtractionTaskId(ed.getExtractionId());
				conditionResource.setDataSourceId(ed.getDataSourceId());
				conditionResource.setDataSourceName(ed.getDataSourceName());

				ObjectMapper mapper = new ObjectMapper();
				String patientString = mapper.writeValueAsString(problem);
				conditionResource.setData(patientString);
				conditionResource.setDataProcessingStatus(dataProcessingStatus.toString());
				conditionService.saveOrUpdateCondition(conditionResource);
			}
		}
		return new AsyncResult<>(System.nanoTime());
	}
}
