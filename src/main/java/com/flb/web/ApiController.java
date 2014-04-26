package com.flb.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.evalua.entity.support.DataStoreManager;
import com.flb.entity.Server;
import com.flb.entity.ServerLoad;
import com.flb.entity.support.Repository;

@Controller
public class ApiController {
	
	@Resource
	private Repository repository; 
	
	@Resource
	private DataStoreManager dataStoreManager;
	
	@RequestMapping("/api/simple-service")
	public ModelAndView login(HttpSession httpSession) throws InterruptedException{		
		return new ModelAndView("redirect:"+getServerString("/api/simple-service"));
	}
	
	private String getServerString(String relativeURL){
		List<ServerLoad> serverLoads = repository.listMinLoadServers();
		ServerLoad serverLoad=serverLoads.get(0);
		Server server=serverLoad.getServer();
		serverLoad.setRequestCount(serverLoad.getRequestCount()+1);
		dataStoreManager.save(serverLoad);
		
		System.out.println("***************** "+serverLoad.getId());
		return "http://"+server.getIp()+":"+server.getPortNumber()+relativeURL+"?id="+server.getId();
	}

}
