package com.flb.entity;

import javax.persistence.Entity;
import javax.persistence.Lob;
import com.evalua.entity.support.EntityBase;

@Entity
public class ServerLog extends EntityBase{

	private String log="";

	@Lob
	public String getLog() {
		return log;
	}

	public void setLog(String log) {
		this.log = log;
	}
	
	
}
