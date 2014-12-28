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
	private Integer requestCapacity;
	private Integer capacityThreshold;	
	private boolean migrationActive;
	private Integer requestMigrated=0;	
	
	public Integer getRequestMigrated() {
		return requestMigrated;
	}
	public void setRequestMigrated(Integer requestMigrated) {
		this.requestMigrated = requestMigrated;
	}
	public boolean isMigrationActive() {
		return migrationActive;
	}
	public void setMigrationActive(boolean migrationActive) {
		this.migrationActive = migrationActive;
	}
	public Integer getRequestCapacity() {
		return requestCapacity;
	}
	public void setRequestCapacity(Integer requestCapacity) {
		this.requestCapacity = requestCapacity;
	}
	public Integer getCapacityThreshold() {
		return capacityThreshold;
	}
	public void setCapacityThreshold(Integer capacityThreshold) {
		this.capacityThreshold = capacityThreshold;
	}
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
