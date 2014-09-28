package com.flb.demon;

import java.util.Date;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import com.flb.entity.GraphElement;
import com.flb.entity.ServerLoad;

public class LoadAnalyzerDemon extends TimerTask{


	public static Logger logger = LoggerFactory.getLogger(LoadAnalyzerDemon.class);

	public static Timer sessionTimer = new Timer("Buffered Thread Schudular", true);   

	private int initialDelay; 
	private int period;
	private SessionFactory sessionFactory;


	public LoadAnalyzerDemon(SessionFactory sessionFactory,int initialDelay,int period){
		this.initialDelay=initialDelay;
		this.period=period;
		this.sessionFactory=sessionFactory;
	}


	public void init() {
		logger.info("Scedulaing Load Analyzer Demon *******");
		sessionTimer.schedule(this, initialDelay,period);
	}

	@Override
	public void run() {

		logger.info("Running Load Analyzer Demon *******");
		Session hibernateSession=sessionFactory.openSession();
		Transaction transaction=hibernateSession.beginTransaction();
		
		List<ServerLoad> serverLoads=listServersLoads();
		Date currentTime=new Date();
		
		for (ServerLoad serverLoad : serverLoads) {
			GraphElement graphElement = new GraphElement();
			graphElement.setAnalisysTime(currentTime);
			graphElement.setServerLoad(serverLoad.getRequestCount());
			graphElement.setServer(serverLoad.getServer());
			graphElement.setMigrationActive(serverLoad.getServer().isMigrationActive());
			hibernateSession.save(graphElement);
		}
		
		transaction.commit();
		hibernateSession.close();
	}
	
	
	private List<ServerLoad> listServersLoads(){
		Session hibernateSession=sessionFactory.openSession();
		
		List<ServerLoad> serverLoads= hibernateSession.createQuery("FROM "+ServerLoad.class.getName()).list();
		hibernateSession.close();
		return serverLoads;
	}

}
