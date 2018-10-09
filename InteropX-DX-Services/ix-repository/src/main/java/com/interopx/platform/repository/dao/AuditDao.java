package com.interopx.platform.repository.dao;

import java.util.List;

import com.interopx.platform.repository.model.Audit;

public interface AuditDao {
	
	Audit saveOrUpdate(Audit audit);
	
	Audit getAuditById(Integer dsId);
	
	List<Audit> getAllAudits();
	
	Audit deleteAuditById(Integer dsId);

}
/*@Repository
public class AuditDAO {
	@Autowired
	private SessionFactory sessionFactory;

	private Session session;

	public Session getSession() {
		return this.sessionFactory.getCurrentSession();
	}

	public void closeSession() {
		this.session.close();
	}

	public void setSession(Session session) {
		this.session = session;
	}

	public void setSessionFactory(SessionFactory sf) {
		this.sessionFactory = sf;
	}

	// Save Function
	public void saveAudit(Audit audit) {
		System.out.println("---------------------------------Save Audit DAO ");
		Session session = this.sessionFactory.getCurrentSession();
		session.persist(audit);
		// session.close();
	}

	public void updateAudit(Audit audit) {
		System.out.println("---------------------------------Update Audit DAO");
		getSession().update(audit);
		// closeSession();
	}

	public void deleteAudit(Audit audit) {
		System.out.println("---------------------------------Delete Audit DAO");
		getSession().delete(audit);
		// closeSession();
	}

	public Audit getAudit(Integer id) {
		System.out.println("---------------------------------Get Audit DAO");
		Audit audit = (Audit) getSession().get(Audit.class, id);
		return audit;
	}

	public List<Audit> getAllAudit() {
		System.out.println("---------------------------------Get All Audit DAO");
		Session session = this.sessionFactory.getCurrentSession();
		List<Audit> auditList = session.createQuery("From Audit").list(); // here Audit is Not a Table Name its a Class Name
																	// Ref: HQL
		return auditList;
	}
}*/
