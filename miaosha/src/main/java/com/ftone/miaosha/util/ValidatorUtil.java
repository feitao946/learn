package com.ftone.miaosha.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.StringUtils;

public class ValidatorUtil {
	private static final Pattern pattern=Pattern.compile("^1([358][0-9]|4[579]|66|7[0135678]|9[89])[0-9]{8}$");
	
	public static boolean isMobile(String value){
		if(StringUtils.isEmpty(value)){
			return false;
		}
		Matcher matcher = pattern.matcher(value);
		return matcher.matches();
	}
}
