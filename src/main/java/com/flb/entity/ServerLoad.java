package com.flb.entity;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import com.evalua.entity.support.EntityBase;

@Entity
public class ServerLoad extends EntityBase{
	
	private Server server;
	private Integer requestCount=0;
	
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	public Server getServer() {
		return server;
	}
	public void setServer(Server server) {
		this.server = server;
	}
	public Integer getRequestCount() {
		return requestCount;
	}
	public void setRequestCount(Integer requestCount) {
		this.requestCount = requestCount;
	}

}
