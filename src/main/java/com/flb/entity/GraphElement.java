package com.flb.entity;

import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;

import com.evalua.entity.support.EntityBase;

@Entity
public class GraphElement extends EntityBase{

	private Date analisysTime;
	private Integer serverLoad;
	private Server server;
	
	
	public Date getAnalisysTime() {
		return analisysTime;
	}
	public void setAnalisysTime(Date analisysTime) {
		this.analisysTime = analisysTime;
	}	
	
	public Integer getServerLoad() {
		return serverLoad;
	}
	public void setServerLoad(Integer serverLoad) {
		this.serverLoad = serverLoad;
	}
	@OneToOne(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	public Server getServer() {
		return server;
	}
	public void setServer(Server server) {
		this.server = server;
	}	
}
