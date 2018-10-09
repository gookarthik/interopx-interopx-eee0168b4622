package com.interopx.platform.datasets.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

@RestController
public class ApplicationController {
	
	@RequestMapping(value="/actuator/info",method = RequestMethod.GET)
    public ModelAndView swaggerHomePage(){
       return new ModelAndView("redirect:/swagger-ui.html");
    }
	
	@RequestMapping(value="",method = RequestMethod.GET)
    public ModelAndView swaggerPage(){
       return new ModelAndView("redirect:/swagger-ui.html");
    }

}
