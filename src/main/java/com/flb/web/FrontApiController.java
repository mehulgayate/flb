package com.flb.web;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.UUID;
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
import com.flb.entity.ServerLog;
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
		String uuid=UUID.randomUUID().toString();
		jsonObject.put("result", callBackServers(getServerString("/api/simple-service?uuidf="+uuid), null,"/api/simple-service?uuidf="+uuid));
		mv.addObject("result",jsonObject); 
		return mv; 
	}

	private String getServerString(String relativeURL){
		List<ServerLoad> serverLoads = repository.listMinLoadServers();
		ServerLoad serverLoad=serverLoads.get(0);
		Server server=serverLoad.getServer();
		int requestCount=serverLoad.getRequestCount();
		requestCount++;
		serverLoad.setRequestCount(requestCount);
		
		int totalRequestCount=serverLoad.getTotalRequestCount();
		totalRequestCount++;
		serverLoad.setTotalRequestCount(totalRequestCount);
		dataStoreManager.save(serverLoad);

		ServerLog serverLog = repository.findServerLastLog();
		if(serverLog==null){
			serverLog = new ServerLog();
		}
		serverLog.setLog(serverLog.getLog()+"<br/>"+"New request recieved, will be processed by sever id : ***************** "+server.getId()+"\n");
		dataStoreManager.save(serverLog);
		System.out.println("New request recieved, will be processed by sever id : ***************** "+server.getId());
		return "http://"+server.getIp()+":"+server.getPortNumber()+relativeURL+"&id="+server.getId();
	}

	private String callBackServers(String url,Object object,String relativeUrl) throws IOException{		
		StringBuilder stringBuilder=new StringBuilder(""); 
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		try {
		    // Start the client
			ServerLog serverLog = repository.findServerLastLog();
			serverLog.setLog(serverLog.getLog()+"<br/>"+"Making request to back server, url: "+url+" ......\n");
		    httpclient.start();

		    // Execute request
		    final HttpGet request1 = new HttpGet(url);
		    Future<HttpResponse> future = httpclient.execute(request1, null);
		    // and wait until a response is received
		    HttpResponse response1 = future.get();
		    serverLog.setLog(serverLog.getLog()+"<br/>"+"Output recived from back server.....\n");
	

			BufferedReader br = new BufferedReader(
					new InputStreamReader((response1.getEntity().getContent())));

			String output;

						
			
			dataStoreManager.save(serverLog);
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				stringBuilder.append(output);
			}
			
			if(stringBuilder.toString().indexOf("migrate")>0){
				Long oldServerId=new Long(url.split("id=")[1]);
				return migrateRequest(oldServerId,relativeUrl,object);
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
	
	private String migrateRequest(Long oldServerId,String relativeUrl,Object object) throws IOException{
		
		System.out.println("**** #### ***** **** #### ***** **** #### ***** Load migration detected from server : "+oldServerId);
		ServerLog serverLog = repository.findServerLastLog();
		serverLog.setLog(serverLog.getLog()+"<br/>"+"**** #### ***** **** #### ***** **** #### ***** Load migration detected from server : "+oldServerId);
		Server server=repository.findServerById(oldServerId);
		
		int requestMig=server.getRequestMigrated();
		System.out.println("Migrated request numbers "+requestMig);
		serverLog.setLog(serverLog.getLog()+"<br/>"+"Migrated request numbers "+requestMig);
		server.setRequestMigrated(++requestMig);
		dataStoreManager.save(server);
		
		
		String url=getNewServerString(relativeUrl,oldServerId);
		
		if(url.equals("false")){
			return "false";
		}
		
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

			System.out.println("Migration Output from Server for UUIDF : "+relativeUrl);
			serverLog.setLog(serverLog.getLog()+"<br/>"+"Migration Output from Server for UUIDF : "+relativeUrl);
			while ((output = br.readLine()) != null) {
				System.out.println(output);
				stringBuilder.append(output);
			}
			
			if(stringBuilder.toString().indexOf("migrate")>0){
				Long olderServerId=new Long(url.split("id=")[1]);
				migrateRequest(olderServerId,relativeUrl,object);
			}

			dataStoreManager.save(serverLog);

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
	
	
	private String getNewServerString(String relativeURL,Long oldServerId){
		List<ServerLoad> serverLoads = repository.listMinLoadServersForMigration(oldServerId);
		ServerLog serverLog = repository.findServerLastLog();
		
		/*if(serverLoads.isEmpty()){
			serverLog.setLog(serverLog.getLog()+"<br/>"+"########### ALL SERVERS ARE OVERLOADED .....UNABLE TO SERVE AT CURRENT MOMENT...!!!!!");
			System.out.println("########### ALL SERVERS ARE OVERLOADED .....UNABLE TO SERVE AT CURRENT MOMENT...!!!!!");
			return "false";
		}*/
		ServerLoad serverLoad=serverLoads.get(0);
		Server server=serverLoad.getServer();
		int requestCount=serverLoad.getRequestCount();
		requestCount++;
		serverLoad.setRequestCount(requestCount);	
		int totalRequestCount=serverLoad.getTotalRequestCount();
		totalRequestCount++;
		serverLoad.setTotalRequestCount(totalRequestCount);
		dataStoreManager.save(serverLoad);

		System.out.println("Sever id: "+oldServerId+" has migrated request to server ID: ***************** "+server.getId());
		serverLog.setLog(serverLog.getLog()+"<br/>"+"Sever id: "+oldServerId+" has migrated request to server ID: ***************** "+server.getId());
		return "http://"+server.getIp()+":"+server.getPortNumber()+relativeURL+"&id="+server.getId();
	}
}
