package com.interopx.platform.repository.service;

import java.util.List;

import com.interopx.platform.repository.model.ExtractionDetails;

public interface ExtractionService {
	
	List<ExtractionDetails> getExtractionDetailsBydsId(Integer dsId);
	
	ExtractionDetails getExtractionById(Integer etId);

}
