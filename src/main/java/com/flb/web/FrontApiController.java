package com.flb.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;

import net.sf.json.JSONObject;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;
import org.apache.http.message.BasicHeader;
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
	public ModelAndView login(HttpSession httpSession,HttpServletRequest request) throws InterruptedException, IOException{
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
		int requestCount=serverLoad.getRequestCount();
		serverLoad.setRequestCount(++requestCount);
		System.out.println(serverLoad.getServer().getId()+" request count "+serverLoad.getRequestCount());
		dataStoreManager.save(serverLoad);

		System.out.println("request will be processed by sever id : ***************** "+serverLoad.getId());
		return "http://"+server.getIp()+":"+server.getPortNumber()+relativeURL+"?id="+server.getId();
	}

	private String callBackServers(String url,Object object) throws IOException{		
		StringBuilder stringBuilder=new StringBuilder(""); 
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		try {
		    // Start the client
		    httpclient.start();

		    // Execute request
		    final HttpGet request1 = new HttpGet(url);
		    Future<HttpResponse> future = httpclient.execute(request1, null);
		    // and wait until a response is received
		    HttpResponse response1 = future.get();
		    System.out.println(request1.getRequestLine() + "->" + response1.getStatusLine());
	

			BufferedReader br = new BufferedReader(
					new InputStreamReader((response1.getEntity().getContent())));

			String output;

			System.out.println("Output from Server .... \n");
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				stringBuilder.append(output);
			}


		} catch (ClientProtocolException e) {

			e.printStackTrace();

		} catch (IOException e) {

			e.printStackTrace();
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} finally {
            httpclient.close();
        }
		return stringBuilder.toString();

	}
}