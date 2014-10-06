package com.flb.web;

import java.io.IOException;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import com.evalua.entity.support.DataStoreManager;
import com.evalua.util.RequestGenerator;
import com.flb.entity.GraphElement;
import com.flb.entity.Server;
import com.flb.entity.ServerLoad;
import com.flb.entity.support.Repository;

@Controller
public class LoadBalancerController {
	
	@Resource
	private DataStoreManager dataStoreManager;
	
	@Resource
	private Repository repository;
	
	@Resource
	private RequestGenerator requestGenerator;
	
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
	
	@RequestMapping("/admin/edit-server")
	public ModelAndView showeEditNew(HttpSession httpSession,@RequestParam Long id){
		ModelAndView mv=new ModelAndView("admin/edit-server");
		Server server=repository.findServerById(id);
		mv.addObject("server", server);
		
		return mv;
	}
	
	@RequestMapping("/admin/graphs")
	public ModelAndView showGraphs(HttpSession httpSession){
		ModelAndView mv=new ModelAndView("admin/graph");
		mv.addObject("servers", repository.listAllServer());
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
	
	@RequestMapping("/admin/update-server")
	public ModelAndView editServer(HttpSession httpSession, @ModelAttribute Server server){
		ModelAndView mv=new ModelAndView("redirect:/admin");
		Server toUpdateServer=repository.findServerById(server.getId());
		toUpdateServer.setCapacityThreshold(server.getCapacityThreshold());
		toUpdateServer.setIp(server.getIp());
		toUpdateServer.setMigrationActive(server.isMigrationActive());
		toUpdateServer.setName(server.getName());
		toUpdateServer.setPortNumber(server.getPortNumber());
		toUpdateServer.setRequestCapacity(server.getRequestCapacity());
		toUpdateServer.setStatus(server.getStatus());
		dataStoreManager.save(toUpdateServer);		
		
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
		dataStoreManager.save(serverLoad);
		
		return mv;
	}
	
	@RequestMapping("/graph-data")
	public ModelAndView getGrapgData(@RequestParam Long id){
		ModelAndView mv=new ModelAndView("json-string");
		
		Server server=repository.findServerById(id);
		JSONObject mainObject=new JSONObject();
		List<GraphElement> graphElements=repository.listGraphElementByServer(server,true);
		int i=0;
		JSONObject jsonObjectOuter=new JSONObject();
		for (GraphElement graphElement : graphElements) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("time", graphElement.getAnalisysTime().toString());
			jsonObject.put("load",graphElement.getServerLoad());
			jsonObject.put("capacity", graphElement.getServer().getRequestCapacity());
			jsonObjectOuter.put(i, jsonObject);
			i++;
		}
		mainObject.put("migrationActive", jsonObjectOuter);
		graphElements=repository.listGraphElementByServer(server,false);
		i=0;
		
		jsonObjectOuter=new JSONObject();
		for (GraphElement graphElement : graphElements) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("time", graphElement.getAnalisysTime().toString());
			jsonObject.put("load",graphElement.getServerLoad());
			jsonObject.put("capacity", graphElement.getServer().getRequestCapacity());
			jsonObjectOuter.put(i, jsonObject);
			i++;
		}
		mainObject.put("migrationDeactive", jsonObjectOuter);
		
		
		List<Server> servers=repository.listAllServer();
		
		jsonObjectOuter=new JSONObject();
		i=0;
		for (Server server2 : servers) {
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("name", server2.getName());
			jsonObject.put("requests", server2.getRequestMigrated());
			jsonObjectOuter.put(i,jsonObject);
			i++;
		}
		
		mainObject.put("migration", jsonObjectOuter);
		mv.addObject(mainObject);
		return mv;
	}
	
	@RequestMapping("/create-load")
	public ModelAndView createLoad() throws IOException, InterruptedException{
		ModelAndView mv=new ModelAndView("admin/create-load");	
		
		
		return mv;
	}

}
