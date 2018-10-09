package com.interopx.platform.audit.controller;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.interopx.platform.repository.model.Audit;
import com.interopx.platform.repository.service.AuditService;

@Controller
@RequestMapping("/audit")
public class AuditController {
	
	@Autowired
	private  AuditService auditService;


	@RequestMapping(value = "/create/", method = RequestMethod.POST)
	@ResponseBody
	public  Audit saveDataSource(@RequestBody  Audit audit, HttpServletRequest request) {
		auditService.saveOrUpdate(audit);
		return audit;
	} 
	
	@RequestMapping(value = "/update/", method = RequestMethod.PUT)
	@ResponseBody
	public  Audit updateDataSource(@RequestBody  Audit audit, HttpServletRequest request) {
		auditService.saveOrUpdate(audit);
		return audit;
	}
	
	@RequestMapping(value = "/list", method = RequestMethod.GET)
	@ResponseBody
	public List<Audit> getAllDataSources()throws Exception {
		return auditService.getAllAudits();
	}

	@RequestMapping(value = "/{id}", method = RequestMethod.GET)
	@ResponseBody
	public Audit getDataSourceById(@PathVariable Integer id) {
		return auditService.getAuditById(id);
	}
	
	@RequestMapping(value = "/{id}", method = RequestMethod.DELETE)
	@ResponseBody
	public String deleteDataSourceById(@PathVariable Integer id) {
		auditService.deleteAuditById(id);
		return "Data Source with id:"+id+" successfully deleted";
	}
	
	/*Logger logger = LogManager.getLogger(AuditController.class);
	private static final String template = "Hello, %s!";
    private final AtomicLong counter = new AtomicLong();
    
    
  //Declare Service OBje
	@Resource(name="auditService")
	AuditService restService;

    test only
    @RequestMapping("/hello")
    public Greeting hello(@RequestParam(value="name", defaultValue="World") String name) {
        return new Greeting(counter.incrementAndGet(),
                            String.format(template, name));
    }
	
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String home(Locale locale, Model model) {
		logger.info("Welcome home! The client locale is {}.", locale);
		
		Date date = new Date();
		DateFormat dateFormat = DateFormat.getDateTimeInstance(DateFormat.LONG, DateFormat.LONG, locale);
		
		String formattedDate = dateFormat.format(date);
		
		model.addAttribute("serverTime", formattedDate );
		
		return "home";
	}
    
    *//** 
     * Create new audit
     *
     * @param inputString         audit data as input string to be written to audit event table
     *                  
     * @return          		  Http Response
     *//*
	@RequestMapping("/add")
	public Response create(@RequestParam(value="inputString") String inputString) {
	    try {
	    	
	    	 Save to Database and Log event
	    	 
	    	
	    	
			
			return Response.status(201).build();
		} catch (Exception e) {
			logger.error("Failed to write audit to collection.");
			return Response.status(500).build();
		}
	}
	
	*//** 
     * Get all audits in audit event table
     *            
     * @return          		  Http Response
     *//*
	@RequestMapping("/all")
	public Response getAudits() {
		try {			
			String allAudit = "Audit Data String";   //Get from Database Json format
			return Response.ok(allAudit).build();
		} catch (Exception e) {
			logger.error(e.getClass().getName() + ": " + e.getMessage());
			return Response.status(404).build();
		}
	}
	

	*//**
	 * Get count of all audit events
	 * @return 
	 * @return	int
	 *//*
	@RequestMapping("/count")
	public  String getAuditCount() {
			String count = "10";  //test data, get from database
			return count;
	}*/
}
