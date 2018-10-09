package com.interopx.sample.repository.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.interopx.sample.repository.model.DataSource;
import com.interopx.sample.repository.service.DataSourceService;


@Controller
@RequestMapping("/datasource")
public class DataSourceController {
	@Autowired
	private  DataSourceService dataSourceService;


	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	@ResponseBody
	public  DataSource saveOrUpdateDataSource(@RequestBody  DataSource dataSource, HttpServletRequest request) {
		dataSourceService.saveOrUpdate(dataSource);
		return dataSource;
	} 
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<DataSource> getAllDataSources()throws Exception {
		return dataSourceService.getAllDataSources();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public DataSource getDataSourceById(@PathVariable Integer id) {
		return dataSourceService.getDataSourceById(id);
	}

}
