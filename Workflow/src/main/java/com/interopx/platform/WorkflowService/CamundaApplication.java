package com.interopx.platform.WorkflowService;

import org.camunda.bpm.spring.boot.starter.annotation.EnableProcessApplication;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.client.RestTemplate;

import com.interopx.platform.WorkflowService.dao.WorkflowRepositoryDAO;

@SpringBootApplication
@EnableProcessApplication("WorkflowService")
@ComponentScan(basePackages= {"com.interopx.platform.WorkflowService", "com.interopx.platform.repository"})
public class CamundaApplication extends SpringBootServletInitializer {
  public static void main(String... args) {
    SpringApplication.run(CamundaApplication.class, args);
  }
  
	@Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(CamundaApplication.class);
    }

  
	@Bean
	public RestTemplate restTemplate() {
	    return new RestTemplate();
	}
	
	@Bean
	public WorkflowRepositoryDAO repositoryDao() {
		return new WorkflowRepositoryDAO();
	}
}
