package com.ftone.miaosha.vo;

import com.ftone.miaosha.domain.OrderInfo;

public class OrderDetailVo {
	private OrderInfo orderInfo;
	private GoodsVo goods;
	public OrderInfo getOrderInfo() {
		return orderInfo;
	}
	public void setOrderInfo(OrderInfo orderInfo) {
		this.orderInfo = orderInfo;
	}
	public GoodsVo getGoods() {
		return goods;
	}
	public void setGoods(GoodsVo goods) {
		this.goods = goods;
	}
	
	
}
