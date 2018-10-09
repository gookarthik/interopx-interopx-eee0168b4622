package com.interopx.platform.repository.service;

import java.util.List;

import com.interopx.platform.repository.model.LocationResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

public interface LocationService {
	
	public LocationResource saveOrUpdateLocation(LocationResource location);

	public List<LocationResource> getAllLocations();

	public LocationResource getLocationById(Integer id);
	
	public List<LocationResource> getLocationResourceByExtractionIdAndInternalPatientId(Integer etId,
			String patientId);
	
	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,DataProcessingStatusEnum processingName, Boolean processingStatus);

	public List<LocationResource> getLocationsByEtIdAndProcessingStatus(Integer etId, DataProcessingStatusEnum statusEnum,
			boolean processStatus);
	
	public List<LocationResource> getDuplicateLocations(Integer id, String locaType, String locationCity, String locationState, String phone, String iXPatientId);
	
	public void updateInteropXLocationId(Integer id, String interopXLocationId, DataProcessingStatusEnum processName, Boolean processStatus);

	public List<Object> getUniqueLocationsByInteropXPatientId(String interopXPatientId);
}
