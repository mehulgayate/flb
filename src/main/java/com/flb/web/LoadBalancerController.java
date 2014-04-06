package com.flb.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.evalua.entity.support.DataStoreManager;
import com.flb.entity.Server;
import com.flb.entity.ServerLoad;
import com.flb.entity.support.Repository;

@Controller
public class LoadBalancerController {
	
	@Resource
	private DataStoreManager dataStoreManager;
	
	@Resource
	private Repository repository;
	
	@RequestMapping("/admin")
	public ModelAndView login(HttpSession httpSession){
		ModelAndView mv=new ModelAndView("admin/index");
		repository.listAllServerLoads();
		mv.addObject("serverLoads", repository.listAllServerLoads());
		return mv;
	}
	
	@RequestMapping("/admin/add-new-server")
	public ModelAndView showAddNew(HttpSession httpSession){
		ModelAndView mv=new ModelAndView("admin/add-new-server");
		
		return mv;
	}
	
	@RequestMapping("/admin/add-server")
	public ModelAndView addNewServer(HttpSession httpSession, @ModelAttribute Server server){
		ModelAndView mv=new ModelAndView("redirect:/admin");		
		dataStoreManager.save(server);		
		
		ServerLoad serverLoad=new ServerLoad();
		serverLoad.setServer(server);
		dataStoreManager.save(serverLoad);
		
		return mv;
	}

}
