package com.interopx.platform.repository.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.interopx.platform.repository.dao.FlatFileConnectorDao;
import com.interopx.platform.repository.model.DataSet;
import com.interopx.platform.repository.model.ExtractionDetails;


@Service
@Transactional
public class FlatFileConnectorServiceImpl implements FlatFileConnectorService{
	
	@Autowired
	private FlatFileConnectorDao flatfileConnectorDao;
	
	public List<ExtractionDetails> getExtractionDetailsBydsId(Integer dsId) {
		return flatfileConnectorDao.getExtractionDetailsBydsId(dsId);
	}
	
	public String updateStatusByETId(Integer etId, String status) {
		return flatfileConnectorDao.updateStatusByETId(etId,status);
	}
	
	public DataSet getDataSetBydsId(Integer dsId) {
		return flatfileConnectorDao.getDataSetBydsId(dsId);
	}

	public ExtractionDetails saveOrUpdate(ExtractionDetails extraction) {
		return flatfileConnectorDao.saveOrUpdate(extraction);
	}
}
