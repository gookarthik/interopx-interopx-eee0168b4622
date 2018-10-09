package com.interopx.platform.dataquality.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import com.interopx.platform.dataquality.model.DataQualityScoreOutput;
import com.interopx.platform.dataquality.model.QualityScoreOutput;
import com.interopx.platform.dataquality.service.DataQualityScorerService;
import com.interopx.platform.repository.model.DataQuality;
import com.interopx.platform.repository.model.DataSourceQuality;
import com.interopx.platform.repository.service.DataQualityService;
import com.interopx.platform.repository.service.PatientService;

@Controller
@RequestMapping("/data-quality")
public class DataQualityServiceController {

	@Autowired
	PatientService patientService;
	
	@Autowired
	DataQualityScorerService dataQualityService;
	
	@Autowired
	DataQualityService dataQualitySer;

	@RequestMapping(value = "/", method = RequestMethod.POST)
	@ResponseBody
	public ResponseEntity<String> checkDataQuality(@RequestParam("etId") Integer extractionId,
			HttpServletRequest request) {

		dataQualityService.processDataQualityChecking(extractionId);

		return new ResponseEntity<String>("Process Completed...!", HttpStatus.OK);
	}

	@RequestMapping(value = "/", method = RequestMethod.GET)
	@ResponseBody
	public ResponseEntity<String> getDataQualityStatus(@RequestParam("etId") Integer extractionId,
			HttpServletRequest request) {
		 /*DataProcessingStatus status = dataProcessingStatusService.getDataProcessingStatus(extractionId);
		 System.out.println(status.getDataQuality());*/
		return new ResponseEntity<String>("Status: ", HttpStatus.OK);
	}
	
	@RequestMapping(value = "/extraction/{etId}", method = RequestMethod.GET)
	@ResponseBody
	public QualityScoreOutput getDataQualityScoreByExtractionId(@RequestParam("etId") Integer extractionId,
			HttpServletRequest request) {
		return dataQualityService.getQualityScoreByEtIdOrDsId(extractionId, null);
	}

	@RequestMapping(value = "/datasource/{dsId}", method = RequestMethod.GET)
	@ResponseBody
	public DataQualityScoreOutput getDataQualityScoreByDatasourceId(@RequestParam("dsId") Integer datasourceId,
			HttpServletRequest request) {
		return dataQualityService.getDataQualityScoreByDsId(datasourceId);
	}
	
	@RequestMapping(value = "/by/extractions/", method = RequestMethod.GET)
	@ResponseBody
	public List<DataQuality> getUniqueDataQualityByExtractionId() {
		return dataQualityService.getDataQualityByExtractionId();
	}
	
	@RequestMapping(value = "/by/datasources/", method = RequestMethod.GET)
	@ResponseBody
	public DataSourceQuality getDistinctDataSourceQualityByDataSourceId() {
		return dataQualityService.getDistinctDataSourceQualityByDataSourceId();
	}

}
