package com.flb.web.support;

import java.util.List;

import com.flb.entity.Server;
import com.flb.entity.ServerLoad;

public class LoadBalancerService {
	
	public Server getServerForRequestProcessing(List<ServerLoad> allActiveServerLoads){
		ServerLoad selectedServerLoad=null;
		for (ServerLoad serverLoad : allActiveServerLoads) {
			if(selectedServerLoad==null){
				selectedServerLoad=serverLoad;
			}
			for (ServerLoad serverLoadToCompare : allActiveServerLoads) {
				if(serverLoad.getId()!=serverLoadToCompare.getId()){
					if(selectedServerLoad.getRequestCount()>serverLoadToCompare.getRequestCount()){
						selectedServerLoad=serverLoadToCompare;
						break;
					}
				}
			}
		}
		return selectedServerLoad.getServer();
	}

}
