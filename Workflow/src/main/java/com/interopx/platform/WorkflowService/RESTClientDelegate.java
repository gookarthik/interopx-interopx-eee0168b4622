package com.interopx.platform.WorkflowService;

import java.util.logging.Logger;

import org.camunda.bpm.engine.delegate.DelegateExecution;
import org.camunda.bpm.engine.delegate.JavaDelegate;
import org.springframework.stereotype.Component;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import org.stringtemplate.v4.*;

/**
 * Delegate for calling restful services from camunda workflow.
 */
//https://jsonplaceholder.typicode.com/posts/1
@Component("rest_client")
public class RESTClientDelegate implements JavaDelegate {
	
	@Autowired
	private RestTemplate restTemplate;
	
	public void execute(DelegateExecution execution) throws Exception {

		ST uriTemplate = new ST(execution.getVariableLocal("serviceUri").toString());
		
		uriTemplate.add("dataSetId", execution.getVariable("dataSetId").toString());
		
		String uri = uriTemplate.render();
		
		Object result = restTemplate.postForObject(uri, null, String.class);
		
		System.out.println("service called: " + uri);
		System.out.println(execution.getVariable("dataSetId").toString());
	}

}
