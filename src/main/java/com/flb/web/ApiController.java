package com.flb.web;

import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.flb.entity.Server;
import com.flb.entity.ServerLoad;
import com.flb.entity.support.Repository;

@Controller
public class ApiController {
	
	@Resource
	private Repository repository; 
	
	@RequestMapping("/api/simple-service")
	public ModelAndView login(HttpSession httpSession) throws InterruptedException{
		return new ModelAndView("forward:"+getServerString()+"/api/simple-service");
	}
	
	private String getServerString(){
		List<ServerLoad> serverLoads = repository.listMinLoadServers();
		Server server=serverLoads.get(0).getServer();
		return "http://"+server.getIp()+":"+server.getPortNumber();
	}

}
