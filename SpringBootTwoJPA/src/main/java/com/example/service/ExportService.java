package com.example.service;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.example.common.utils.WordUtils;
import com.example.pojo.User;

@Service
@Transactional
public class ExportService {

	@Value("${export.word.filePath}")
	private String filePath;
	
	
	public String exportWordFile() {
		
		Map<String, Object> dataMap = new HashMap<String, Object>();
        
        try {
        	List<User> users=new ArrayList<User>();
        	for(int i=0;i<3;i++){
        		User user =new User();
        		user.setName("aaaaad");
        		users.add(user);
        	}
        	String name="2";
        	dataMap.put("users", users);
        	dataMap.put("image", getImageStr());
            dataMap.put("name", name);
            dataMap.put("sex", "男");
            // 生成word文件
        	WordUtils.createWord(dataMap,"abc.ftl",filePath+"/wordTest.doc");
        	WordUtils.createZip(filePath+"/wordTest.zip",filePath+"/wordTest.doc",true);
        	
        	
        	
        }catch(Exception e) {
        	e.printStackTrace();
        }
        return filePath;
      }
        public  String getImageStr() {
//          String imgFile = "C:/Users/lqk/Desktop/p2.jpg";
          String imgFile = "C:/Users/lqk/Desktop/aa.png";
          InputStream in = null;
          byte[] data = null;
          try {
              in = new FileInputStream(imgFile);
              data = new byte[in.available()];
              in.read(data);
              in.close();
          } catch (IOException e) {
              e.printStackTrace();
          }
          String imageCodeBase64 = Base64.encodeBase64String(data);
          return imageCodeBase64;
      }

	
}
