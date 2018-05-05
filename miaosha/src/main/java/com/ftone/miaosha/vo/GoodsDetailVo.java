package com.ftone.miaosha.vo;


import com.ftone.miaosha.domain.Goods;
import com.ftone.miaosha.domain.MiaoshaUser;

public class GoodsDetailVo {
	private GoodsVo goods;
	private MiaoshaUser user;
	private Integer remainSeconds;
	private Integer	 miaoshaStatus;
	
	public GoodsVo getGoods() {
		return goods;
	}
	public void setGoods(GoodsVo goods) {
		this.goods = goods;
	}
	public MiaoshaUser getUser() {
		return user;
	}
	public void setUser(MiaoshaUser user) {
		this.user = user;
	}
	public Integer getRemainSeconds() {
		return remainSeconds;
	}
	public void setRemainSeconds(Integer remainSeconds) {
		this.remainSeconds = remainSeconds;
	}
	public Integer getMiaoshaStatus() {
		return miaoshaStatus;
	}
	public void setMiaoshaStatus(Integer miaoshaStatus) {
		this.miaoshaStatus = miaoshaStatus;
	}
	
	
	
	
}
