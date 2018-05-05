package com.ftone.miaosha.controller;





import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ftone.miaosha.dao.MSUserDao;
import com.ftone.miaosha.dao.MiaoshaoUserDao;
import com.ftone.miaosha.domain.MSUser;
import com.ftone.miaosha.domain.MiaoshaUser;
import com.ftone.miaosha.domain.User;
import com.ftone.miaosha.rabbitmq.MQSender;
import com.ftone.miaosha.redis.RedisService;
import com.ftone.miaosha.redis.UserKey;
import com.ftone.miaosha.result.CodeMsg;
import com.ftone.miaosha.result.Result;
import com.ftone.miaosha.service.UserService;

@Controller
public class DemoController {
	@Autowired
	MQSender mQSender;
	
	
	@RequestMapping("/rabbit")
	@ResponseBody
	public Result<String> rabbitTest(){
		User user = new User();
		user.setName("feitao");
		mQSender.sendTopic(user);
		return Result.success("ok");
	}
	
	
	@RequestMapping("/demoSuccess")
	public Result<String> springbootDemo(){
		return Result.success("success");
	}
	
	@RequestMapping("/demoError")
	public Result<String> springbootDemo2(){
		return Result.error(CodeMsg.SERVER_ERROR);
	}
	
	@RequestMapping("/thymeleaf")
	public String thymeleaf(Model model){
		model.addAttribute("name", "hello");
		return "helloThymeleaf";
	}
	
	@Autowired
	UserService UserService;
	@Autowired
	MSUserDao msUserDao;
	
	@Autowired
	MiaoshaoUserDao miaoshaDao;
	
	@RequestMapping("/db/get")
	@ResponseBody
	public Result<MiaoshaUser> getmiaoshaUser(){
		//MSUser msUser=msUserDao.getByPass("123");
		//MSUser msUser=msUserDao.getById(Long.parseLong("18098393772"));
		
		return Result.success(miaoshaDao.getById(Long.parseLong("18098393772")));
	}
	
	@RequestMapping("/db/getUser")
	@ResponseBody
	public Result<User> getUser(){
		 User user = UserService.getById(1);
		return Result.success(user);
	}
	
	@Autowired
	RedisService redisService;
	@RequestMapping("/redis/get")
	@ResponseBody
	public Result<String> getFromRedis(){
		redisService.set(UserKey.getById, "key01", "value01");
		String value = redisService.get(UserKey.getById, "key01", String.class);
		return Result.success(value);
	}
}
