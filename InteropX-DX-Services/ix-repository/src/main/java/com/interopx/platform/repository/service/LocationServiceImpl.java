package com.interopx.platform.repository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.repository.dao.LocationDao;
import com.interopx.platform.repository.model.LocationResource;
import com.interopx.platform.repository.util.DataProcessingStatusEnum;

@Service
@Transactional
public class LocationServiceImpl implements LocationService {

	@Autowired
	private LocationDao locationDao;

	public LocationResource saveOrUpdateLocation(LocationResource location) {
		return locationDao.saveOrUpdateLocation(location);
	}

	public List<LocationResource> getAllLocations() {
		return locationDao.getAllLocations();
	}

	public LocationResource getLocationById(Integer id) {
		return locationDao.getLocationById(id);
	}

	public List<LocationResource> getLocationResourceByExtractionIdAndInternalPatientId(Integer etId,
			String patientId) {
		return locationDao.getLocationResourceByExtractionIdAndInternalPatientId(etId, patientId);
	}

	@Override
	public List<LocationResource> getLocationsByEtIdAndProcessingStatus(Integer etId,
			DataProcessingStatusEnum statusEnum, boolean processStatus) {
		return locationDao.getLocationsByEtIdAndProcessingStatus(etId, statusEnum, processStatus);
	}

	public void updateInteropXPatientId(String interopXPatientId, String actualPatientId,
			DataProcessingStatusEnum processingName, Boolean processingStatus) {
		locationDao.updateInteropXPatientId(interopXPatientId, actualPatientId,processingName, processingStatus);
	}

	@Override
	public List<LocationResource> getDuplicateLocations(Integer id, String locaType, String locationCity,
			String locationState, String phone, String iXPatientId) {
		return locationDao.getDuplicateLocations(id, locaType, locationCity, locationState, phone, iXPatientId);
	}

	@Override
	public void updateInteropXLocationId(Integer id, String interopXLocationId, DataProcessingStatusEnum processName,
			Boolean processStatus) {
		locationDao.updateInteropXLocationId(id, interopXLocationId, processName, processStatus);
	}

	@Override
	public List<Object> getUniqueLocationsByInteropXPatientId(String interopXPatientId) {
		return locationDao.getUniqueLocationsByInteropXPatientId(interopXPatientId);
	}
}