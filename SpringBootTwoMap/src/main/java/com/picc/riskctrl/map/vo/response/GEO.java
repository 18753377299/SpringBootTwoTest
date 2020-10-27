package com.picc.riskctrl.map.vo.response;

import java.math.BigDecimal;

public class GEO {
	private String location;
	private BigDecimal score;
	public String getLocation() {
		return location;
	}
	public void setLocation(String location) {
		this.location = location;
	}
	public BigDecimal getScore() {
		return score;
	}
	public void setScore(BigDecimal score) {
		this.score = score;
	}

	
}
