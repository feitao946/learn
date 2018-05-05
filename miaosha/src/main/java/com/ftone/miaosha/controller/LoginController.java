package com.ftone.miaosha.controller;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ftone.miaosha.result.Result;
import com.ftone.miaosha.service.MiaoShaUserService;
import com.ftone.miaosha.vo.LoginVo;

@Controller
@RequestMapping("/login")
public class LoginController {
	@Autowired
	private MiaoShaUserService miaoShaUserService;
	
	private static Logger log=LoggerFactory.getLogger(LoginController.class);
	@RequestMapping("/to_login")
	public String toLogin(){
		return "login";
	}
	
	@RequestMapping("/do_login")
	@ResponseBody
	public Result<?> doLogin(@Valid LoginVo loginVo,HttpServletResponse response){
		boolean flag = miaoShaUserService.doLogin(response,loginVo);
		return Result.success(flag);
	}
	
}
