package com.flb.entity;

import java.util.Date;

import javax.persistence.Entity;

import com.evalua.entity.support.EntityBase;

@Entity
public class GraphElement extends EntityBase{

	private Date time;
	private Integer load;
	private Server server;
	
	public Date getTime() {
		return time;
	}
	public void setTime(Date time) {
		this.time = time;
	}
	public Integer getLoad() {
		return load;
	}
	public void setLoad(Integer load) {
		this.load = load;
	}
	public Server getServer() {
		return server;
	}
	public void setServer(Server server) {
		this.server = server;
	}	
}
