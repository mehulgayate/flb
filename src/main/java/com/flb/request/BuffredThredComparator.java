package com.flb.request;

import java.util.Comparator;

public class BuffredThredComparator implements Comparator<BuffredThread>{

	@Override
	public int compare(BuffredThread o1, BuffredThread o2) {		
		return o1.getPriority()-o2.getPriority();
	}

}
