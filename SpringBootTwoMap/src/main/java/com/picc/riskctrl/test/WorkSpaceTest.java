package com.picc.riskctrl.test;

import com.supermap.data.Workspace;

public class WorkSpaceTest {

	public static void main(String[] args) {
		long startDate = System.currentTimeMillis();
		new Workspace();
		System.out.println("====totalDate======="+(System.currentTimeMillis()-startDate));
	}

}
