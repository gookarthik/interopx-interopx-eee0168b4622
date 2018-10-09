package com.interopx.platform.repository.dao;

import java.util.List;

import com.interopx.platform.repository.model.DataProcessingStatus;
import com.interopx.platform.repository.model.LocationResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

public interface LocationDao {

	public LocationResource saveOrUpdateLocation(LocationResource location);

	public List<LocationResource> getAllLocations();

	public LocationResource getLocationById(Integer id);
	
	public List<LocationResource> getLocationResourceByExtractionIdAndInternalPatientId(Integer etId,
			String patientId);

	public List<LocationResource> getLocationsByEtIdAndProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum,
			boolean processStatus);
	
	public List<LocationResource> getDuplicateLocations(Integer id, String locaType, String locationCity, String locationState, String phone, String iXPatientId);
	
	public void updateInteropXLocationId(Integer id, String interopXLocationId, DataProcessingStatusEnum processName, Boolean processStatus);
	
	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,DataProcessingStatusEnum processingName, Boolean processingStatus);
	
	public List<Object> getUniqueLocationsByInteropXPatientId(String interopXPatientId);

}
