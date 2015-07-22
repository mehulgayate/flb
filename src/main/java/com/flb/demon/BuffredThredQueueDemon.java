package com.flb.demon;

import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.flb.request.BuffredThread;
import com.flb.request.BuffredThreadQueue;



public class BuffredThredQueueDemon extends TimerTask{


	public static Logger logger = LoggerFactory.getLogger(BuffredThredQueueDemon.class);

	public static Timer sessionTimer = new Timer("Buffered Queue Thread Schudular", true);   

	private int initialDelay; 
	private int period;
	private SessionFactory sessionFactory;


	public BuffredThredQueueDemon(SessionFactory sessionFactory,int initialDelay,int period){
		this.initialDelay=initialDelay;
		this.period=period;
		this.sessionFactory=sessionFactory;
	}


	public void init() {
		logger.info("Scedulaing Buffered Queue Thread Schudular *******");
		sessionTimer.schedule(this, initialDelay,period);
	}

	@Override
	public void run() {

		logger.info("Running Buffered Thread QUEUE Schudular *******");
		
		for(int i=0;i<BuffredThreadQueue.buffredThreads.size();i++){
			BuffredThread buffredThread=BuffredThreadQueue.buffredThreads.poll();
			synchronized (buffredThread.getThread()) {
				System.out.println("POPING request out.....****");
				buffredThread.getThread().notify();
			}
		}		
		
	}

}
