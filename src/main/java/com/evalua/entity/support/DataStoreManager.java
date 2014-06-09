package com.evalua.entity.support;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

@Transactional
public class DataStoreManager {

	private SessionFactory sessionFactory;	

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	public Session save(EntityBase entity){		
		Session session=getSession();
		session.saveOrUpdate(entity);
		session.flush();
		return session;
	}

	private Session getSession(){
		return sessionFactory.getCurrentSession();
	}

}
