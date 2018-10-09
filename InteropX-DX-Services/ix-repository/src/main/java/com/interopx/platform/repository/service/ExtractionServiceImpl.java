package com.interopx.platform.repository.service;

import java.util.List;

import javax.transaction.Transactional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.interopx.platform.repository.dao.ExtractionDao;
import com.interopx.platform.repository.model.ExtractionDetails;

@Service
@Transactional
public class ExtractionServiceImpl implements ExtractionService {
	
	@Autowired
	private ExtractionDao extractionDao;
	
	public List<ExtractionDetails> getExtractionDetailsBydsId(Integer dsId) {
		
		return extractionDao.getExtractionDetailsBydsId(dsId);
	}

	public ExtractionDetails getExtractionById(Integer etId) {
		
		return extractionDao.getExtractionById(etId);
	}

}
