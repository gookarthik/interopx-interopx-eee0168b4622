package com.interopx.platform.repository.dao;

import java.util.List;

import com.interopx.platform.repository.model.ExtractionDetails;

public interface ExtractionDao {
	
	List<ExtractionDetails> getExtractionDetailsBydsId(Integer dsId);
	
	ExtractionDetails getExtractionById(Integer etId);

}
