package com.ftone.miaosha.dao;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import com.ftone.miaosha.domain.MiaoshaUser;

@Mapper
public interface MiaoshaoUserDao {
	@Select("select * from miaosha_user where id=#{id}")
	MiaoshaUser getById(@Param("id")Long id);
}
