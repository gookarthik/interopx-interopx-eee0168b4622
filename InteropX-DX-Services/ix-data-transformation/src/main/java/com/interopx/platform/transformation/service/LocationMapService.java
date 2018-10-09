package com.interopx.platform.transformation.service;

import java.io.File;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
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
import com.interopx.platform.repository.model.LocationResource;
import com.interopx.platform.repository.service.LocationService;
import com.interopx.platform.repository.resource.Location;

@Component
public class LocationMapService {

	@Autowired
	private LocationService locationService;

	@Async("asyncExecutor")
	public Future<Long> processCSVFile(File file, ExtractionDetails ed) throws IOException {

		try (Reader reader = Files.newBufferedReader(Paths.get(file.getAbsolutePath()));
				CSVParser csvParser = new CSVParser(reader,
						CSVFormat.DEFAULT.withFirstRecordAsHeader().withIgnoreHeaderCase().withTrim());) {
			DataProcessingStatus dataProcessingStatus = new DataProcessingStatus(true, false, false, false);
			for (CSVRecord csvRecord : csvParser) {
				Location location = new Location();
				// Accessing values by Header names
				location.setLocationId(csvRecord.get("location_id"));
				location.setLocType(csvRecord.get("locType"));
				location.setLocationDisplayName(csvRecord.get("location_display_name"));
				location.setLocationAddress1(csvRecord.get("location_address_1"));
				location.setLocationCity(csvRecord.get("location_city"));
				location.setLocationState(csvRecord.get("location_state"));
				location.setPhone(csvRecord.get("phone"));

				// Convert the model to string and persist the record into table
				LocationResource locationResource = new LocationResource();
				locationResource.setExtractionTaskId(ed.getExtractionId());
				locationResource.setDataSourceId(ed.getDataSourceId());
				locationResource.setDataSourceName(ed.getDataSourceName());

				ObjectMapper mapper = new ObjectMapper();
				String patientString = mapper.writeValueAsString(location);
				locationResource.setData(patientString);
				locationResource.setDataProcessingStatus(dataProcessingStatus.toString());
				locationService.saveOrUpdateLocation(locationResource);
			}
		}
		return new AsyncResult<>(System.nanoTime());
	}
}