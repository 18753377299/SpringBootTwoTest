package com.example.common.readconfig;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/*locations = "classpath:book.properties"*/
/*prefix是指前缀，location指定要注入文件的位置。
 * 
 * 类型安全的配置方式
 * */
@ConfigurationProperties(prefix="http")
@Component
public class HttpProperties {
	
	
	  private Integer  maxTotal;
	   /*#并发数*/
	   private Integer  defaultMaxPerRoute;
	 /*  #创建连接的最长时间*/
	   private Integer  connectTimeout;
	  /* #从连接池中获取到连接的最长时间*/
	   private Integer  connectionRequestTimeout;
	  /* #数据传输的最长时间*/
	   private Integer  socketTimeout;
	   /*#提交请求前测试连接是否可用*/
	   private Boolean  staleConnectionCheckEnabled;
	   /*#可用空闲连接过期时间,重用空闲连接时会先检查是否空闲时间超过这个时间，如果超过，释放socket重新建立*/
	   private Integer  validateAfterInactivity;
	   
	   
		public Integer getMaxTotal() {
			return maxTotal;
		}
		public void setMaxTotal(Integer maxTotal) {
			this.maxTotal = maxTotal;
		}
		public Integer getDefaultMaxPerRoute() {
			return defaultMaxPerRoute;
		}
		public void setDefaultMaxPerRoute(Integer defaultMaxPerRoute) {
			this.defaultMaxPerRoute = defaultMaxPerRoute;
		}
		public Integer getConnectTimeout() {
			return connectTimeout;
		}
		public void setConnectTimeout(Integer connectTimeout) {
			this.connectTimeout = connectTimeout;
		}
		public Integer getConnectionRequestTimeout() {
			return connectionRequestTimeout;
		}
		public void setConnectionRequestTimeout(Integer connectionRequestTimeout) {
			this.connectionRequestTimeout = connectionRequestTimeout;
		}
		public Integer getSocketTimeout() {
			return socketTimeout;
		}
		public void setSocketTimeout(Integer socketTimeout) {
			this.socketTimeout = socketTimeout;
		}
		public Boolean getStaleConnectionCheckEnabled() {
			return staleConnectionCheckEnabled;
		}
		public void setStaleConnectionCheckEnabled(Boolean staleConnectionCheckEnabled) {
			this.staleConnectionCheckEnabled = staleConnectionCheckEnabled;
		}
		public Integer getValidateAfterInactivity() {
			return validateAfterInactivity;
		}
		public void setValidateAfterInactivity(Integer validateAfterInactivity) {
			this.validateAfterInactivity = validateAfterInactivity;
		}
	   
	   
	   
}
