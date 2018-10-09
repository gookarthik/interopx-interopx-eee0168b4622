package com.interopx.platform.repository.service;

import java.util.List;

import com.interopx.platform.repository.model.Audit;

public interface AuditService {
	
	Audit saveOrUpdate(Audit audit);
	
	Audit getAuditById(Integer dsId);
	
	List<Audit> getAllAudits();
	
	Audit deleteAuditById(Integer dsId);
}


/*@Service("auditService")
public class AuditService {
	@Autowired
	AuditDAO auditDAO;
	
	@Transactional(value="transactionManager")  here value transactionManager is default so it is optional specify only 
	                                                wheh we use custom name other than transactionManager 
	public void saveEmployeeService(Audit audit){
		System.out.println("---------------------------------Save Audit SERVICE");
		auditDAO.saveAudit(audit);
	}
	
	@Transactional
	public void updateEmployeeService(Audit audit){
		System.out.println("---------------------------------Update Audit SERVICE");
		auditDAO.updateAudit(audit);
	}
	@Transactional
	public void deleteAuditService(Audit audit){
		System.out.println("---------------------------------Delete Audit SERVICE");
		auditDAO.deleteAudit(audit);
	}
	@Transactional
	public Audit getEmployeeService(Integer id){
		System.out.println("---------------------------------Get Audit SERVICE");
		return auditDAO.getAudit(id);

	}
	@Transactional
	public List<Audit> getAllAuditService(){
		System.out.println("---------------------------------Get All Audit SERVICE");
		System.out.println("---------------------------------Total Audits :" + auditDAO.getAllAudit().size());
		return auditDAO.getAllAudit();
	}
}
*/