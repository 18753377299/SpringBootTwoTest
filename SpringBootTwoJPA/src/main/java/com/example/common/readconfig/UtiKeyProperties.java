package com.example.common.readconfig;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import com.example.common.vo.UtiKey;

@Component
@ConfigurationProperties(value="utikeyconfig")
public class UtiKeyProperties {
	
	private List<UtiKey>  utikeys;

	public List<UtiKey> getUtikeys() {
		return utikeys;
	}

	public void setUtikeys(List<UtiKey> utikeys) {
		this.utikeys = utikeys;
	}
	
}
