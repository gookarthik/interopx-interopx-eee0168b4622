package com.interopx.platform.dataquality.service;

import java.util.ArrayList;
import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.interopx.platform.dataquality.model.DataQualityScoreOutput;
import com.interopx.platform.dataquality.model.QualityScoreOutput;
import com.interopx.platform.dataquality.util.ApplicationUtil;
import com.interopx.platform.repository.model.DataQuality;
import com.interopx.platform.repository.model.DataQualityInformation;
import com.interopx.platform.repository.model.DataQualityIssues;
import com.interopx.platform.repository.model.DataSourceQuality;
import com.interopx.platform.repository.model.PatientResource;
import com.interopx.platform.repository.service.DataQualityService;
import com.interopx.platform.repository.service.PatientService;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Service
@Transactional
@PropertySource(value = { "classpath:rules.properties" })
public class DataQualityScorerServiceImpl implements DataQualityScorerService {

	@Autowired
	private PatientService patientService;

	@Autowired
	private DataQualityService dataQualityService;

	@Autowired
	private Environment environment;

	@Override
	public List<PatientResource> processDataQualityChecking(Integer extractionId) {

		List<PatientResource> patients = patientService.getPatientsByEtIdAndInternalPatientIdAndProcessingStatus(
				extractionId, DataProcessingStatusEnum.DATAQUALITYSCORER, false);

		Integer issueCout = 0, dsId = 0;
		String dataSourceName = null;
		List<Integer> scoresList = new ArrayList<>();
		DataQuality dataQuality = new DataQuality();
		List<DataQualityInformation> resourcesList = new ArrayList<DataQualityInformation>();

		for (PatientResource patient : patients) {
			dsId = patient.getDataSourceId();
			dataSourceName = patient.getDataSourceName();
			DataQualityInformation dataQualityInformation = new DataQualityInformation();
			List<DataQualityIssues> dataQualityIssues = new ArrayList<DataQualityIssues>();

			Gson gson = new Gson();
			Integer noOfIssues = 0;
			if (environment.getProperty("PATIENT_DOB") != null && environment.getProperty("PATIENT_DOB").equals("true")) {
				DataQualityIssues dobDataQuality = checkPatientDOB(patient);
				noOfIssues = dobDataQuality.getNoOfissues();
				dataQualityIssues.add(dobDataQuality);
			}
			if (environment.getProperty("PATIENT_NAME") != null	&& environment.getProperty("PATIENT_NAME").equals("true")) {
				DataQualityIssues nameDataQuality = checkPatientName(patient);
				noOfIssues = nameDataQuality.getNoOfissues();
				dataQualityIssues.add(nameDataQuality);
			}
			issueCout = issueCout + noOfIssues;

			int score = ApplicationUtil.calculateSectionGradeAndIssues(dataQualityIssues);
			dataQualityInformation.setResourceName("Patient");
			dataQualityInformation.setRecordId(patient.getId());
			dataQualityInformation.setIxPatientId(patient.getInteropxPatientId());
			dataQualityInformation.setScore(score);
			dataQualityInformation.setNoOfIssues(noOfIssues);
			dataQualityInformation.setIssueList(gson.toJson(dataQualityIssues).toString());
			scoresList.add(score);
			resourcesList.add(dataQualityInformation);
			dataQualityInformation.setDataQuality(dataQuality);
		}
		Double overallScore = scoresList.stream().mapToInt(val -> val).average().orElse(0.0);
		dataQuality.setEtId(extractionId);
		dataQuality.setDsId(dsId);
		dataQuality.setNoOfIssues(issueCout);
		dataQuality.setOverallScore(overallScore);
		dataQuality.setResourceList(resourcesList);
		dataQualityService.saveOrUpdate(dataQuality);

		DataSourceQuality daSourceQuality = dataQualityService.getDataSourceQualityByDsId(dsId);
		if (daSourceQuality != null) {
			daSourceQuality.setScore(dataQualityService.getAverageScoreByEtIdOrDsId(extractionId, null));
			Double noOfIsssues = dataQualityService.getAllIssuesCountByEtIdOrDsId(extractionId, null);
			daSourceQuality.setNoOfIssues(Integer.valueOf(noOfIsssues.intValue()));
			dataQuality.setDataSourceQuality(daSourceQuality);
			dataQualityService.saveOrUpdate(daSourceQuality);
		} else {
			DataSourceQuality dataSourceQuality = new DataSourceQuality();
			dataSourceQuality.setDsId(dataQuality.getDsId());
			dataSourceQuality.setScore(dataQualityService.getAverageScoreByEtIdOrDsId(extractionId, null));
			Double noOfIsssues = dataQualityService.getAllIssuesCountByEtIdOrDsId(extractionId, null);
			dataSourceQuality.setNoOfIssues(Integer.valueOf(noOfIsssues.intValue()));
			dataSourceQuality.setDataSourceName(dataSourceName);

			dataQuality.setDataSourceQuality(dataSourceQuality);
			dataQualityService.saveOrUpdate(dataSourceQuality);
		}
		return null;
	}
	
	private DataQualityIssues checkPatientName(PatientResource patient) {
		DataQualityIssues dataQualityIssues = new DataQualityIssues();
		int actualPoints = 0;
		int maxPoints = 1;
		if (patient != null) {
			if (patient.getFirstName() != null && !patient.getFirstName().isEmpty()) {
				if (ApplicationUtil.validateName(patient.getFirstName())) {
					actualPoints++;
				} else {
					dataQualityIssues.setCategoryElement("name");
					dataQualityIssues.setCategoryDescription("Name is Invalid");
					dataQualityIssues.setNoOfissues(1);
				}
			} else {
				dataQualityIssues.setCategoryElement("name");
				dataQualityIssues.setCategoryDescription("Name is Null");
				dataQualityIssues.setNoOfissues(1);
			}
		} else {
			dataQualityIssues.setCategoryElement("name");
			dataQualityIssues.setCategoryDescription("Patient is Null");
			dataQualityIssues.setNoOfissues(1);
		}

		dataQualityIssues.setActualPoints(actualPoints);
		dataQualityIssues.setMaxPoints(maxPoints);
		dataQualityIssues.setRubricScore(ApplicationUtil.calculateRubricScore(maxPoints, actualPoints));

		float rubricScore = ApplicationUtil.calculateRubricScore(maxPoints, actualPoints);

		System.out.println(rubricScore);

		return dataQualityIssues;
	}

	private DataQualityIssues checkPatientDOB(PatientResource patient) {
		DataQualityIssues dataQualityIssues = new DataQualityIssues();
		int actualPoints = 0;
		int maxPoints = 1;
		if (patient != null) {
			if (patient.getDob() != null) {
				if (ApplicationUtil.validateBirthDate(patient.getDob())) {
					actualPoints++;
				} else {
					dataQualityIssues.setCategoryElement("dob");
					dataQualityIssues.setCategoryDescription("DOB is Invalid");
					dataQualityIssues.setNoOfissues(2);
				}
			} else {
				dataQualityIssues.setCategoryElement("dob");
				dataQualityIssues.setCategoryDescription("DOB is Null");
				dataQualityIssues.setNoOfissues(2);
			}
		} else {
			dataQualityIssues.setCategoryElement("dob");
			dataQualityIssues.setCategoryDescription("Patient is Null");
			dataQualityIssues.setNoOfissues(2);
		}

		dataQualityIssues.setActualPoints(actualPoints);
		dataQualityIssues.setMaxPoints(maxPoints);
		dataQualityIssues.setRubricScore(ApplicationUtil.calculateRubricScore(maxPoints, actualPoints));

		return dataQualityIssues;
	}

	public QualityScoreOutput getQualityScoreByEtIdOrDsId(Integer extractionId, Integer dsId) {
		QualityScoreOutput qso = new QualityScoreOutput();

		// get data quality object
		List<DataQuality> dqList = dataQualityService.getDataQualityByEtIdOrDsId(extractionId, dsId);
		if (dqList.size() > 0) {
			qso.setExtractionId(extractionId);
			Double noOfIsssues= dataQualityService.getAllIssuesCountByEtIdOrDsId(extractionId, null);
			qso.setIssues(Integer.valueOf(noOfIsssues.intValue()));
			qso.setDatasourceId(dqList.get(0).getDsId());
			qso.setDataSourceName(dataQualityService.getDataSourceNameByDsIdOrEtId(extractionId, null));
			qso.setDataQuality(dqList);

			// get average score
			Double averageScore = dataQualityService.getAverageScoreByEtIdOrDsId(extractionId, null);
			qso.setScore(averageScore);
		}
		return qso;
	}

	@Override
	public DataQualityScoreOutput getDataQualityScoreByDsId(Integer datasourceId) {
		List<QualityScoreOutput> qualityScoreOutputlist = new ArrayList<>();
		DataQualityScoreOutput dataQualityScoreOutput = new DataQualityScoreOutput();
		QualityScoreOutput qso = new QualityScoreOutput();
		List<DataQuality> dqList = dataQualityService.getDataQualityByEtIdOrDsId(null, datasourceId);
		if (dqList.size() > 0) {
			qso.setExtractionId(dqList.get(0).getEtId());
			qso.setDatasourceId(dqList.get(0).getDsId());
			qso.setDataQuality(dqList);
			qso.setScore(dataQualityService.getAverageScoreByEtIdOrDsId(dqList.get(0).getEtId(), null));
			Double noOfIsssues= dataQualityService.getAllIssuesCountByEtIdOrDsId(dqList.get(0).getEtId(), null);
			qso.setIssues(Integer.valueOf(noOfIsssues.intValue()));
			qso.setDataSourceName(dataQualityService.getDataSourceNameByDsIdOrEtId(dqList.get(0).getEtId(), null));
		}
		dataQualityScoreOutput.setDatasourceId(datasourceId);
		dataQualityScoreOutput.setScore(dataQualityService.getAverageScoreByEtIdOrDsId(null, datasourceId));
		Double noOfIsssues= dataQualityService.getAllIssuesCountByEtIdOrDsId(null, datasourceId);
		dataQualityScoreOutput.setIssues(Integer.valueOf(noOfIsssues.intValue()));
		dataQualityScoreOutput.setDataSourceName(dataQualityService.getDataSourceNameByDsIdOrEtId(null, datasourceId));
		qualityScoreOutputlist.add(qso);
		dataQualityScoreOutput.setExtractionList(qualityScoreOutputlist);
		return dataQualityScoreOutput;
	}

	@Override
	public List<DataQuality> getDataQualityByExtractionId() {
		return dataQualityService.getDataQualityByExtractionId();
	}

	@Override
	public DataSourceQuality getDistinctDataSourceQualityByDataSourceId() {
		return dataQualityService.getDistinctDataSourceQualityByDataSourceId();
	}

}
