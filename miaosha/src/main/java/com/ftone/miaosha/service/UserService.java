package com.ftone.miaosha.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftone.miaosha.dao.UserDao;
import com.ftone.miaosha.domain.MiaoshaUser;
import com.ftone.miaosha.domain.User;
import com.ftone.miaosha.result.Result;

@Service

public class UserService {
	@Autowired
	UserDao userDao;
	
	
	public User getById(int id){
		return userDao.getById(id);
	}
	@Transactional
	public Result<Boolean> insertUser(){
		User user1 = new User();
		user1.setId(1);
		user1.setName("lili");
		userDao.InsertUser(user1);
		
		User user2=new User();
		user2.setId(1);
		user2.setName("333");
		userDao.InsertUser(user2);
		//int b=1/0;
		return Result.success(true);
	}
}
