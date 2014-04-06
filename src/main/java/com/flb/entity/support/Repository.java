package com.flb.entity.support;

import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.transaction.annotation.Transactional;

import com.flb.entity.Server;
import com.flb.entity.ServerLoad;

@Transactional
public class Repository {
	
	@Resource
	private SessionFactory sessionFactory;

	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	Session getSession(){
		return sessionFactory.getCurrentSession();
	}
	
	public List<ServerLoad> listAllServerLoads(){
		return getSession().createQuery("From "+ServerLoad.class.getName()).list();
	}
	
	public List<ServerLoad> listMinLoadServers(){
		return getSession().createQuery("From "+ServerLoad.class.getName()+" sl where sl.requestCount=(select min(slinnr.requestCount) FROM "+ServerLoad.class.getName()+" slinnr)").list();
	}

}
