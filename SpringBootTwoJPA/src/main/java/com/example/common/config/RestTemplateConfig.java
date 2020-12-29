package com.example.common.config;

import org.apache.http.client.HttpClient;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.config.Registry;
import org.apache.http.config.RegistryBuilder;
import org.apache.http.conn.socket.ConnectionSocketFactory;
import org.apache.http.conn.socket.PlainConnectionSocketFactory;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.HttpClientBuilder;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.client.BufferingClientHttpRequestFactory;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

import com.example.common.interceptor.RestTemplateInterceptor;
import com.example.common.readconfig.HttpProperties;

/*初始化连接池:  https://www.cnblogs.com/yuexiaoyun/articles/13034028.html*/

@Configuration
public class RestTemplateConfig {
	
	@Value("${http.maxTotal}")
    private Integer maxTotal;

    @Value("${http.defaultMaxPerRoute}")
    private Integer defaultMaxPerRoute;

    @Value("${http.connectTimeout}")
    private Integer connectTimeout;

    @Value("${http.connectionRequestTimeout}")
    private Integer connectionRequestTimeout;

    @Value("${http.socketTimeout}")
    private Integer socketTimeout;

    @Value("${http.staleConnectionCheckEnabled}")
    private boolean staleConnectionCheckEnabled;

    @Value("${http.validateAfterInactivity}")
    private Integer validateAfterInactivity;

    //可以用于获取配置文件中的信息： 可以代替@Value 方式获取配置文件的信息
    @Autowired
    private HttpProperties httpProperties;

    @Bean
    public RestTemplate restTemplate() {
//    	RestTemplate restTemplate = new RestTemplate(httpRequestFactory());
    	RestTemplate restTemplate = new RestTemplate(httpRequestFactoryBuffer());
    	/*添加RestTemplate拦截器*/
    	restTemplate.getInterceptors().add(new RestTemplateInterceptor());
        return restTemplate;
    }
   /**  
    *  java.io.IOException:stream closed 异常的原因及处理:
    *  response和request流只能读取一次，只要读取完就结束了,
    *  那么接下来就是如何解决流只能读取一次的问题，网上好多人解决都是说用包装类可以解决该问题，将该对象缓存下来，就不会有问题。
    *  使用BufferingClientHttpRequestFactory包装类（设计模式）
    * */
    @Bean
    public BufferingClientHttpRequestFactory httpRequestFactoryBuffer() {
    	BufferingClientHttpRequestFactory bufferingClientHttpRequestFactory = new BufferingClientHttpRequestFactory(httpRequestFactory());
    	return bufferingClientHttpRequestFactory;
    }
    
    @Bean
    public ClientHttpRequestFactory httpRequestFactory() {
    	HttpComponentsClientHttpRequestFactory httpComponentsClientHttpRequestFactory =  new HttpComponentsClientHttpRequestFactory(httpClient());
    	
    	/*也可以在这里设置时间  begin*/
    	//    	httpComponentsClientHttpRequestFactory.setConnectionRequestTimeout(0);
    	/*也可以在这里设置时间  end*/
    	
    	return httpComponentsClientHttpRequestFactory;
    }

    @SuppressWarnings("deprecation")
	@Bean
    public HttpClient httpClient() {
        Registry<ConnectionSocketFactory> registry = RegistryBuilder.<ConnectionSocketFactory>create()
                .register("http", PlainConnectionSocketFactory.getSocketFactory())
                .register("https", SSLConnectionSocketFactory.getSocketFactory())
                .build();
        PoolingHttpClientConnectionManager connectionManager = new PoolingHttpClientConnectionManager(registry);
        connectionManager.setMaxTotal(maxTotal); // 最大连接数
        connectionManager.setDefaultMaxPerRoute(defaultMaxPerRoute);    //单个路由最大连接数
        connectionManager.setValidateAfterInactivity(validateAfterInactivity); // 最大空间时间
        RequestConfig requestConfig = RequestConfig.custom()
                .setSocketTimeout(socketTimeout)        //服务器返回数据(response)的时间，超过抛出read timeout
                .setConnectTimeout(connectTimeout)      //连接上服务器(握手成功)的时间，超出抛出connect timeout
                .setStaleConnectionCheckEnabled(staleConnectionCheckEnabled) // 提交前检测是否可用
                .setConnectionRequestTimeout(connectionRequestTimeout)//从连接池中获取连接的超时时间，超时间未拿到可用连接，会抛出org.apache.http.conn.ConnectionPoolTimeoutException: Timeout waiting for connection from pool
                .build();
        return HttpClientBuilder.create()
                .setDefaultRequestConfig(requestConfig)
                .setConnectionManager(connectionManager)
                .build();
    }

}
