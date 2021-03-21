package com.example.activiti.func.controller;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

@RestController
public class RestTemplateApi {

    @RequestMapping("/testHttpClient")
    public Object getUser(String msg) throws IOException {
        CloseableHttpClient closeableHttpClient = HttpClients.createDefault();
        HttpGet get = new HttpGet("http://localhost:15031/repository/deployments");
        CloseableHttpResponse response = closeableHttpClient.execute(get);
        return EntityUtils.toString(response.getEntity(), "utf-8");
    }
}
