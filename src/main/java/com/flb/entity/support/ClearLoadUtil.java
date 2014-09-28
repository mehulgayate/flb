package com.flb.entity.support;

import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.flb.entity.Server;
import com.flb.entity.ServerLoad;

public class ClearLoadUtil {
	private SessionFactory sessionFactory;

	public void init(){
		System.out.println("************* clearing all load");
		
		Session session=sessionFactory.openSession();
		Transaction transaction=session.beginTransaction();
		List<ServerLoad> serverLoads= session.createQuery("FROM "+ServerLoad.class.getName()).list();
		
		for (ServerLoad serverLoad : serverLoads) {
			serverLoad.setRequestCount(0);
			session.saveOrUpdate(serverLoad);
			Server server=serverLoad.getServer();
			server.setRequestMigrated(0);
			session.saveOrUpdate(server);
			
		}
		transaction.commit();
		session.close();
		
	}
	
	public ClearLoadUtil(SessionFactory sessionFactory){
		this.sessionFactory=sessionFactory;
	}
}
