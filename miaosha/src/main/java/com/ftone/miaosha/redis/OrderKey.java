package com.ftone.miaosha.redis;

public class OrderKey extends BasePrefix {

	public OrderKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	
	public static OrderKey getOrderByUserIDandGoodsId=new OrderKey(6500,"goug");
}
