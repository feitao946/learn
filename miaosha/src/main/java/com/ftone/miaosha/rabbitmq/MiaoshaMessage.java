package com.ftone.miaosha.rabbitmq;

import com.ftone.miaosha.domain.MiaoshaUser;

public class MiaoshaMessage {
	private MiaoshaUser user;
	private Long goodsId;
	
	public Long getGoodsId() {
		return goodsId;
	}
	public void setGoodsId(Long goodsId) {
		this.goodsId = goodsId;
	}
	public MiaoshaUser getUser() {
		return user;
	}
	public void setUser(MiaoshaUser user) {
		this.user = user;
	}
	
	
	
}
