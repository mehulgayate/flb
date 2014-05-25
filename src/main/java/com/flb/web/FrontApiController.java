package com.flb.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.message.BasicHeader;
import org.apache.http.protocol.HTTP;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import com.evalua.entity.support.DataStoreManager;
import com.flb.entity.Server;
import com.flb.entity.ServerLoad;
import com.flb.entity.support.Repository;

@Controller
public class FrontApiController {

	@Resource
	private Repository repository; 

	@Resource
	private DataStoreManager dataStoreManager;

	@RequestMapping("/api/simple-service")
	public ModelAndView login(HttpSession httpSession,HttpServletRequest request) throws InterruptedException{
		ModelAndView mv=new ModelAndView("json-string");
		JSONObject jsonObject=new JSONObject();
		jsonObject.put("result", callBackServers(getServerString("/api/simple-service"), null));
		mv.addObject("result",jsonObject); 
		return mv; 
	}

	private String getServerString(String relativeURL){
		List<ServerLoad> serverLoads = repository.listMinLoadServers();
		ServerLoad serverLoad=serverLoads.get(0);
		Server server=serverLoad.getServer();
		serverLoad.setRequestCount(serverLoad.getRequestCount()+1);
		dataStoreManager.save(serverLoad);

		System.out.println("request will be processed by sever id : ***************** "+serverLoad.getId());
		return "http://"+server.getIp()+":"+server.getPortNumber()+relativeURL+"?id="+server.getId();
	}

	private String callBackServers(String url,Object object){		
		StringBuilder stringBuilder=new StringBuilder(""); 
		try {

			DefaultHttpClient httpClient = new DefaultHttpClient();
			HttpGet getRequest = new HttpGet(url);
			getRequest.addHeader("accept", "application/json");			

			if(object!=null){
				getRequest = new HttpGet(url+"?param="+object.toString());
			}

			HttpResponse response = httpClient.execute(getRequest);

			if (response.getStatusLine().getStatusCode() != 200) {
				throw new RuntimeException("Failed : HTTP error code : "
						+ response.getStatusLine().getStatusCode());
			}

			BufferedReader br = new BufferedReader(
					new InputStreamReader((response.getEntity().getContent())));

			String output;

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				stringBuilder.append(output);
			}

			httpClient.getConnectionManager().shutdown();

		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		}
		return stringBuilder.toString();

	}
}