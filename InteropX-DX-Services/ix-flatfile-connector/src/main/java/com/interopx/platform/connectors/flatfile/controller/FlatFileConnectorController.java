package com.interopx.platform.connectors.flatfile.controller;

import java.io.File;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.interopx.platform.repository.model.DataSet;
import com.interopx.platform.repository.model.DataSource;
import com.interopx.platform.repository.model.ExtractionDetails;
import com.interopx.platform.repository.service.FlatFileConnectorService;


@Controller
@RequestMapping("/flat-file-connector")
@PropertySource(value = { "classpath:application.properties" })
public class FlatFileConnectorController {

	@Autowired
	private FlatFileConnectorService flatfileConnectorService;

	@Autowired
	private Environment environment;

	@RequestMapping(value = "/extract", method = RequestMethod.POST)
	public ResponseEntity<String> extractDataFromFlatFile(@RequestParam("dataSetId") Integer dataSetId,
			HttpServletRequest request) {
		DataSet dataSet = flatfileConnectorService.getDataSetBydsId(dataSetId);
		for (DataSource dataSource : dataSet.getData_sources()) {
			ExtractionDetails extraction = new ExtractionDetails();
			extraction.setDataSetId(dataSet.getDataSetId());
			extraction.setDataSetName(dataSet.getDataSetName());
			extraction.setDataSourceId(dataSource.getDataSourceId());
			extraction.setDataSourceName(dataSource.getDataSourceName());
			extraction.setFilesLocation("");
			extraction.setStatus("Pending");
			extraction = flatfileConnectorService.saveOrUpdate(extraction);
			String files_location = environment.getRequiredProperty("directory") + dataSet.getDataSetName() + "-"
					+ dataSet.getDataSetId() + "\\" + dataSource.getDataSourceName() + "-"
					+ dataSource.getDataSourceId() + "\\Extraction-"+extraction.getExtractionId();
			File files = new File(files_location);
			if (!files.exists()) {
				if (files.mkdirs()) {
					System.out.println("Multiple directories are created!");
				} else {
					System.out.println("Failed to create multiple directories!");
				}
			}
			extraction.setFilesLocation(files_location);
			flatfileConnectorService.saveOrUpdate(extraction);
		}
		return new ResponseEntity<String>("Extraction Process Started...!", HttpStatus.OK);
	}

	@RequestMapping(value = "/extraction-details/{dsId}", method = RequestMethod.GET)
	@ResponseBody
	public List<ExtractionDetails> getExtractionDetailsById(@PathVariable Integer dsId) {
		return flatfileConnectorService.getExtractionDetailsBydsId(dsId);
	}

	@RequestMapping(value = "/extraction-details/{dsId}", method = RequestMethod.PUT)
	@ResponseBody
	public String updateStatusByETId(@PathVariable Integer dsId, @RequestParam("etId") Integer etId,
			@RequestParam("status") String status) {
		flatfileConnectorService.updateStatusByETId(etId, status);
		return "Status Updated Successfully";
	}

}
