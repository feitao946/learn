package com.ftone.miaosha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ftone.miaosha.domain.MSUser;


@Mapper
public interface MSUserDao {
	@Select("select * from ms_user where id=#{id}")
	MSUser getById(@Param("id")Long id);
	@Select("select * from ms_user where password=#{password}")
	MSUser getByPass(@Param("password")String password);
}
