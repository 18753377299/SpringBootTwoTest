package com.example.api;

import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.Map;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.example.pojo.Test;

@RestController
public class RestTemplateApi {
	
	@Autowired
	RestTemplate simpleRestTemplate;
	@Autowired
	RestTemplate restTemplate;
	
	/*HttpCilent：使用示例*/
	@RequestMapping("/testHttpClient")
    public Object getUser(String msg) throws IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://localhost:15021/test/hello");
        CloseableHttpResponse response = closeableHttpClient.execute(get);
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }
	
	/*RestTemplate：*/
	@RequestMapping("/testRestTemplate")
    public Object testRestTemplate() throws IOException {
//		http://localhost:15021/test/queryUser
        ResponseEntity<Test> result= restTemplate.getForEntity("http://localhost:15021/test/hello",Test.class);
        return result.getBody();
    }
	@RequestMapping("/sayhello")
	public String sayHello() {
	    ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://localhost:15021/test/helloTwo", String.class, "张三");
	    return responseEntity.getBody();
	}
	@RequestMapping("/sayhello2")
	public String sayHello2() {
	    Map<String, String> map = new HashMap<>();
	    map.put("name", "李四");
	    ResponseEntity<String> responseEntity = restTemplate.getForEntity("http://HELLO-SERVICE/sayhello?name={name}", String.class, map);
	    return responseEntity.getBody();
	}
//	getForObject
	@RequestMapping("/book2")
	public Test book2() {
		Test test = restTemplate.getForObject("http://localhost:15021/test/hello",Test.class);
	    return test;
	}
	
//	postForEntity
	@RequestMapping("/book3")
	public Test book3() {
		Test book = new Test();
	    book.setName("红楼梦");
	    ResponseEntity<Test> responseEntity = restTemplate.postForEntity("http://localhost:15021/test/hello", book, Test.class);
	    return responseEntity.getBody();
	}
	
//	postForObject
	@GetMapping("/book4")
	public Test book4() {
		Test book = new Test();
	    book.setName("红楼梦");
	    Test test  = null;
//	    test  = restTemplate.postForObject("http://localhost:15021/test/hello", book, Test.class);
	    test  = restTemplate.postForObject("http://localhost:15021/book4receive", book, Test.class);
	    return test;
	}
	/**进行restTemplate接收参数测试*/
	@PostMapping("/book4receive")
	public Test book4receive(@RequestBody Object object) {
		Test book = new Test();
	    book.setName("红楼梦receive");
	    System.out.println(object);
	    Test test  = restTemplate.postForObject("http://localhost:15021/test/hello", book, Test.class);
	    return book;
	}
	
//	postForLocation
	@RequestMapping("/book5")
	public String book5() {
		Test book = new Test();
	    book.setName("红楼梦");
	    URI uri = restTemplate.postForLocation("http://localhost:15021/test/hello", book);
	    String path= uri.getPath()+":"+uri.getHost()+":"+uri.getPort();
	    return path;
	}
	
	
	
	
	
	
	
	
}
