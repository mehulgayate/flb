package com.evalua.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClients;

public class RequestGenerator {

	/**
	 * @param args
	 * @throws IOException 
	 * @throws InterruptedException 
	 */
	public static void main(String[] args) throws IOException, InterruptedException {
		
		int i=1;
		
		while(true){
			System.out.println("Creatting req num "+i);
			createRequest(i);
			if(i%30==0){
				Thread.currentThread().sleep(120000);
			}
			i++;
		}

	}
	
	private static String createRequest(Integer id) throws IOException{
		CloseableHttpAsyncClient httpclient = HttpAsyncClients.createDefault();
		StringBuilder stringBuilder=new StringBuilder("");
		String url="http://localhost:8080/api/simple-service?id="+id;
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
		
		System.out.println("OUt : "+stringBuilder.toString());
		return stringBuilder.toString();
	}

}
