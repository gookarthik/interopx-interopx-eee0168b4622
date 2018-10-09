package com.xyram.test.employee.dashboard;

import java.util.Collection;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@RefreshScope
@RestController
public class EmployeeInfoController {

	@Autowired
	private RestTemplate restTemplate;

	@Autowired
	private EurekaClient eurekaClient;

	@RequestMapping("/dashboard/{id}")
	public EmployeeInfo findme(@PathVariable Long id) {

		Application application = eurekaClient.getApplication("EmployeeSearchService");
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/" + "employee/find/"
				+ id;
		System.out.println("URL" + url);
		EmployeeInfo emp = restTemplate.getForObject(url, EmployeeInfo.class);
		System.out.println("RESPONSE " + emp);
		return emp;
	}

	@RequestMapping("/dashboard/peers")
	public Collection<EmployeeInfo> findPeers() {
		
		Application application = eurekaClient.getApplication("EmployeeSearchService");
		InstanceInfo instanceInfo = application.getInstances().get(0);
		String url = "http://" + instanceInfo.getIPAddr() + ":" + instanceInfo.getPort() + "/" + "employee/findall";
		System.out.println("URL" + url);
		
		Collection<EmployeeInfo> list = restTemplate.getForObject(url, Collection.class);
		System.out.println("RESPONSE " + list);
		return list;
	}
}
