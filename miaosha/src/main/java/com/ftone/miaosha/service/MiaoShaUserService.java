package com.ftone.miaosha.service;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftone.miaosha.dao.MiaoshaoUserDao;
import com.ftone.miaosha.domain.MiaoshaUser;
import com.ftone.miaosha.exception.GlobalException;
import com.ftone.miaosha.redis.MiaoshaUserKey;
import com.ftone.miaosha.redis.RedisService;
import com.ftone.miaosha.result.CodeMsg;
import com.ftone.miaosha.result.Result;
import com.ftone.miaosha.util.MD5Util;
import com.ftone.miaosha.util.UUIDUtil;
import com.ftone.miaosha.vo.LoginVo;

@Service
public class MiaoShaUserService {
	@Autowired
	private MiaoshaoUserDao miaoShaoUserDao;
	@Autowired
	private RedisService redisService;
	
	public static final String COOKIE_NAME_TOKEN="token";
	
	public MiaoshaUser getById(Long id){
		MiaoshaUser user = miaoShaoUserDao.getById(id);
		return user;
	}
	
	public boolean doLogin(HttpServletResponse response,  LoginVo loginVo){
		
		if(loginVo==null){
			throw new GlobalException(CodeMsg.SERVER_ERROR);
		}
		String mobile = loginVo.getMobile();
		String formpass = loginVo.getPassword();
	
		MiaoshaUser user = getById(Long.parseLong(mobile));
		System.out.println(user);
		if(user==null){
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		String dbpass = user.getPassword();
		
		String dbSalt = user.getSalt();
		String formpassToDBpass = MD5Util.formpassToDBpass(formpass, dbSalt);
		if(!formpassToDBpass.equals(dbpass)){
			throw new GlobalException(CodeMsg.MOBILE_NOT_EXIST);
		}
		String token=UUIDUtil.getUUID();
		Cookie cookie = new Cookie(COOKIE_NAME_TOKEN, token);
		cookie.setMaxAge(MiaoshaUserKey.TOKEN_EXPIRE);
		cookie.setPath("/");
		response.addCookie(cookie);
		
		redisService.set(MiaoshaUserKey.token, token, user);
		return true;
	}
	
	public MiaoshaUser getUserByToken(String token){
		MiaoshaUser user = redisService.get(MiaoshaUserKey.token, token, MiaoshaUser.class);
		if(user==null){
			return null;
		}
		return user;
	}

	public  void resetRedisKeyExpire(String key ,MiaoshaUser user) {
		redisService.set(MiaoshaUserKey.token, key, user);
	} 
}
