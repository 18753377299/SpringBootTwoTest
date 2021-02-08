package com;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class Hello {
	
//	@Value("${msg}")
//	private String msg;
	
	@RequestMapping("/hello")
	@ResponseBody
	public Map<String, Object> showHelloWorld(HttpServletRequest request){
		String path = request.getContextPath();
		Map<String, Object> map =new HashMap<String, Object>();
		map.put("msg", "hello");
//		System.out.println("输出信息："+this.msg+":"+path);
		return map;		
	}
	
	@RequestMapping(value="/showUser")
	public String  showUser(Model model) {
//		List<User> list =new ArrayList<User>();
//		list.add(new User("aaa","bbbb"));
//		list.add(new User("ccc","ddd"));
		
//		model.addAttribute("list", list);
		return "showUser";
	}
	
	
}
