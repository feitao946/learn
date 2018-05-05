package com.ftone.miaosha.util;

import java.util.UUID;

public class UUIDUtil {
	
	public static String getUUID(){
		UUID uuid = UUID.randomUUID();
		return uuid.toString().replace("-", "");
	}
}
