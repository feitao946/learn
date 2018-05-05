package com.ftone.miaosha.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ftone.miaosha.domain.User;
@Mapper
public interface UserDao {
	@Select("select * from user where id=#{id}")
	User getById(@Param("id")int id);
	@Insert("insert into user(name)values(#{name})")
	int InsertUser(User user);
}
