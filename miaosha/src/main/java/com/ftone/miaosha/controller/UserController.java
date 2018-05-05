package com.ftone.miaosha.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ftone.miaosha.domain.MiaoshaUser;
import com.ftone.miaosha.result.Result;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@RequestMapping("/info")
	@ResponseBody
	public Result<MiaoshaUser> getUser(MiaoshaUser user){
		return Result.success(user);
		
	}
	
}
