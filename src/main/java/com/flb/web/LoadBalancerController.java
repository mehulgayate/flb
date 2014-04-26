package com.flb.web;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
	
	@RequestMapping("/notify/request-complete")
	public ModelAndView notify(HttpSession httpSession, @RequestParam Long id){
		ModelAndView mv=new ModelAndView("json-string");
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("result", "true");
		mv.addObject("result", jsonObject);

		
		Server server=repository.findServerById(id);
		ServerLoad serverLoad=repository.findServerLoadByServer(server);
		int load=serverLoad.getRequestCount();
		load--;
		serverLoad.setRequestCount(load);
		System.out.println(load+"****************");
		dataStoreManager.save(serverLoad);
		
		return mv;
	}

}
