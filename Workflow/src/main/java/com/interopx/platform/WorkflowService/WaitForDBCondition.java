package com.interopx.platform.WorkflowService;

import java.awt.peer.TrayIconPeer;
import java.util.List;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.hibernate.query.NativeQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.interopx.platform.WorkflowService.dao.WorkflowRepositoryDAO;

@Component("waitForDb")
public class WaitForDBCondition implements JavaDelegate {
	
	@Autowired
	private WorkflowRepositoryDAO repositoryDao;
	
	@Override
	public void execute(DelegateExecution execution) throws Exception {
		
		String dataSetIdStr = execution.getVariable("dataSetId").toString();
		String sqlQuery = execution.getVariableLocal("sqlQuery").toString();
		
		int dataSetId = Integer.parseInt(dataSetIdStr);
		
		NativeQuery<?> query = repositoryDao.getRepositorySession().createNativeQuery(sqlQuery);
		query.setParameter(1, dataSetId);
		
		List<?> results = query.getResultList();
		
		execution.setVariable("result", results.get(0));
	}

}
