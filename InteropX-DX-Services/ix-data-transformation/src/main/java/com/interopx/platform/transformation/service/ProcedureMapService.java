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
import com.interopx.platform.repository.model.ProcedureResource;
import com.interopx.platform.repository.service.ProcedureService;
import com.interopx.platform.repository.resource.Procedure;
import com.interopx.platform.transformation.utill.DateUtility;

@Component
public class ProcedureMapService {

	@Autowired
	private ProcedureService procedureService;

	@Async("asyncExecutor")
	public Future<Long> processCSVFile(File file, ExtractionDetails ed) throws IOException, ParseException {

		try (Reader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			DataProcessingStatus dataProcessingStatus = new DataProcessingStatus(true, false, false, false);
			for (CSVRecord csvRecord : csvParser) {

				// Read CSV values and assigned to appropriate InteropX model class
				Procedure procedure = new Procedure();
				// Accessing values by Header names
				procedure.setPatientId(csvRecord.get("patientID"));
				procedure.setEncId(csvRecord.get("EncID"));
				procedure.setCcCptCode(csvRecord.get("cc_cpt_code"));
				procedure.setCodeSystem(csvRecord.get("CodeSystem"));
				procedure.setDescription(csvRecord.get("description"));
				procedure.setTencounterid(csvRecord.get("tencounterid"));
				procedure.setTargetSiteCode(csvRecord.get("TargetSiteCode"));

				if (csvRecord.get("cc_date_of_service") != null && !csvRecord.get("cc_date_of_service").isEmpty()) {
					Date ccDateOfService = DateUtility.convertStringToDate(csvRecord.get("cc_date_of_service"));
					procedure.setCcDateOfService(ccDateOfService);
				}
				// Convert the model to string and persist the record into table
				ProcedureResource procedureResource = new ProcedureResource();
				procedureResource.setExtractionTaskId(ed.getExtractionId());
				procedureResource.setDataSourceId(ed.getDataSourceId());
				procedureResource.setDataSourceName(ed.getDataSourceName());

				ObjectMapper mapper = new ObjectMapper();
				String patientString = mapper.writeValueAsString(procedure);
				procedureResource.setData(patientString);
				procedureResource.setDataProcessingStatus(dataProcessingStatus.toString());
				procedureService.saveOrUpdateProcedure(procedureResource);
			}
		}
		return new AsyncResult<>(System.nanoTime());
	}
}
