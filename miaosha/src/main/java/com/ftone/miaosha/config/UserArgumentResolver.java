package com.ftone.miaosha.config;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.MethodParameter;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;

import com.ftone.miaosha.domain.MiaoshaUser;
import com.ftone.miaosha.service.MiaoShaUserService;
import com.ftone.miaosha.service.UserService;
@Service
public class UserArgumentResolver implements HandlerMethodArgumentResolver{
	
	@Autowired
	MiaoShaUserService userService;
	
	@Override
	public boolean supportsParameter(MethodParameter parameter) {
		Class<?> clazz=parameter.getParameterType();
		
		
		return clazz==MiaoshaUser.class?true:false;
	}

	@Override
	public Object resolveArgument(MethodParameter parameter,
			ModelAndViewContainer mavContainer, NativeWebRequest webRequest,
			WebDataBinderFactory binderFactory) throws Exception {
			HttpServletRequest request=(HttpServletRequest) webRequest.getNativeRequest();
			//HttpServletResponse	response=(HttpServletResponse) webRequest.getNativeResponse();
			String paramToken = request.getParameter(MiaoShaUserService.COOKIE_NAME_TOKEN);
			String cookieToken=getCookieValue(MiaoShaUserService.COOKIE_NAME_TOKEN,request);
			if(StringUtils.isEmpty(paramToken)&&StringUtils.isEmpty(cookieToken)){
				return null;
			}
			String token=StringUtils.isEmpty(paramToken)?cookieToken:paramToken;
			MiaoshaUser user = userService.getUserByToken(token);
			if(user==null){
				return null;
			}
			userService.resetRedisKeyExpire(token,user);
			return user;
	}

	private String getCookieValue(String cookieNameToken,
			HttpServletRequest request) {
		Cookie[] cookies = request.getCookies();
		if(cookies==null||cookies.length<=0){
			return null;
		}
		for (Cookie cookie : cookies) {
			if(cookie.getName().equals(MiaoShaUserService.COOKIE_NAME_TOKEN)){
				return cookie.getValue();
			}
		}
		
		return null;
	}

	

}
