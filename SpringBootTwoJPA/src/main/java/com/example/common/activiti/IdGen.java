package com.example.common.activiti;

import java.util.UUID;

import org.activiti.engine.impl.cfg.IdGenerator;

public class IdGen implements IdGenerator{

	 /**
     * 封装JDK自带的UUID, 通过Random数字生成, 中间无-分割.
     */
    public static String uuid() {
        return UUID.randomUUID().toString().replaceAll("-", "");
    }
    
	@Override
	public String getNextId() {
		return IdGen.uuid();
	}

}
