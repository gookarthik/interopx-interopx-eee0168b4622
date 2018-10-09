package com.interopx.platform.datasets.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.interopx.platform.repository.model.DataSet;
import com.interopx.platform.repository.service.DataSetService;


@Controller
@RequestMapping("/dataset")
public class DataSetController {
	
	@Autowired
	private  DataSetService dataSetService;


	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	@ResponseBody
	public  DataSet saveDataSource(@RequestBody  DataSet dataSet, HttpServletRequest request) {
		dataSetService.saveOrUpdate(dataSet);
		return dataSet;
	} 
	
	@RequestMapping(value = "/update/", method = RequestMethod.PUT)
	@ResponseBody
	public  DataSet updateDataSource(@RequestBody  DataSet dataSet, HttpServletRequest request) {
		dataSetService.saveOrUpdate(dataSet);
		return dataSet;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<DataSet> getAllDataSources()throws Exception {
		return dataSetService.getAllDataSets();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public DataSet getDataSourceById(@PathVariable Integer id) {
		return dataSetService.getDataSetById(id);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteDataSourceById(@PathVariable Integer id) {
		dataSetService.deleteDataSetById(id);
		return "Data Source with id:"+id+" successfully deleted";
	}

}
