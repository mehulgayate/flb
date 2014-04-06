package com.flb.entity;

import javax.persistence.Entity;

import com.evalua.entity.support.EntityBase;

@Entity
public class Server extends EntityBase{
	
	public enum ServerStatus{
		ACTIVE,SUSPENDED,INACTIVE;
	}
	
	private String ip;
	private String portNumber;
	private String name;
	private ServerStatus status;
	
	public ServerStatus getStatus() {
		return status;
	}
	public void setStatus(ServerStatus status) {
		this.status = status;
	}
	public String getIp() {
		return ip;
	}
	public void setIp(String ip) {
		this.ip = ip;
	}
	public String getPortNumber() {
		return portNumber;
	}
	public void setPortNumber(String portNumber) {
		this.portNumber = portNumber;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	
	
	

}
