package com.flb.web.interceptor;

import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.evalua.entity.support.DataStoreManager;
import com.flb.entity.Server;
import com.flb.request.BuffredThread;
import com.flb.request.BuffredThreadQueue;

public class DetectionInterceptor implements HandlerInterceptor {

	@Resource
	private com.flb.entity.support.Repository repository;
	
	@Resource
	private DataStoreManager dataStoreManager;
		
	List<String> ignoreAuthenticationActions;
	
	public static Logger logger=LoggerFactory.getLogger(DetectionInterceptor.class);

	
	public List<String> getIgnoreAuthenticationActions() {
		return ignoreAuthenticationActions;
	}

	public void setIgnoreAuthenticationActions(
			List<String> ignoreAuthenticationActions) {
		this.ignoreAuthenticationActions = ignoreAuthenticationActions;
	}
	
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String requestURI = request.getRequestURI();		
		
		if (ignoreAuthenticationActions.contains(requestURI)) {
			return true;
		}
		
		List<Server> servers = repository.listUnderLoadedServers();
		
		if(servers.isEmpty()){
			Thread currentThread=Thread.currentThread();
			BuffredThread buffredThread=new BuffredThread();
			buffredThread.setThread(currentThread);
			buffredThread.setPriority(1);
			BuffredThreadQueue.buffredThreads.offer(buffredThread);

			synchronized (currentThread) {
				try {
					System.out.println("All Servers are overloaded, putting request in Request Delay Queue");
					currentThread.wait();					
					System.out.println("Queue Size is now : "+ BuffredThreadQueue.buffredThreads.size());
				} catch (InterruptedException e) {
					System.out.println("Unable to wait thread ******");
					e.printStackTrace();
				}
			}
		}
		
		return true;
	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		// TODO Auto-generated method stub

	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
					throws Exception {
		// TODO Auto-generated method stub

	}

}
