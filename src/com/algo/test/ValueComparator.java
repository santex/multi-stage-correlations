package com.algo.test;

import java.util.Comparator;
import java.util.Map;


public	class ValueComparator implements Comparator {

	Map base;
	private int type = 1;
	public ValueComparator(Map base,int type) {
		this.base = base;
		this.type = type;
		
	}

	public int compare(Object a, Object b) {

		if(this.type == 0){
			if((Double)base.get(a) < (Double)base.get(b)) {
				return 1;
			} else if((Double)base.get(a) == (Double)base.get(b)) {
				return 0;
			} else {
				return -1;
			}
			
		}else{
			if((Integer)base.get(a) < (Integer)base.get(b)) {
				return 1;
			} else if((Integer)base.get(a) == (Integer)base.get(b)) {
				return 0;
			} else {
				return -1;
			}
			
			
		}
	}


}

