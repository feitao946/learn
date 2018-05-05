package com.ftone.miaosha.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.ftone.miaosha.domain.MiaoshaOrder;
import com.ftone.miaosha.domain.OrderInfo;
import com.ftone.miaosha.redis.MiaoShaKey;
import com.ftone.miaosha.redis.RedisService;
import com.ftone.miaosha.vo.GoodsVo;

@Service
@Transactional
public class MiaoshaService {
	@Autowired
	OrderService orderService;
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	RedisService redisService;
	
	//减库存，下单，写入秒杀订单
		public OrderInfo miaosha(Long userId, Long goodsId) {
			
			Boolean isOk = goodsService.reduceStock(goodsId);
			if(isOk){
				//减库存成功，下订单
				return createOrder(userId, goodsId);
			}else {
				//减库存失败，做个标记
				setGoodsOver(goodsId);
				return null;
			}
			
			
			
		}

		private void setGoodsOver(Long goodsId) {
		redisService.set(MiaoShaKey.isGoodsOver, ""+goodsId, true);
		
	}

		public OrderInfo createOrder(Long userId, Long goodsId) {
			GoodsVo goods = goodsService.getGoodsById(goodsId);
			OrderInfo orderInfo = new OrderInfo();
			orderInfo.setCreateDate(new Date());
			orderInfo.setDeliveryAddrId(0L);
			orderInfo.setGoodsCount(1);
			orderInfo.setGoodsName(goods.getGoodsName());
			orderInfo.setGoodsId(goodsId);
			orderInfo.setGoodsPrice(goods.getMiaoshaPrice());
			orderInfo.setOrderChannel(1);
			orderInfo.setStatus(1);
			orderInfo.setPayDate(new Date());
			orderInfo.setUserId(userId);
			
			orderService.insertOrderInfo(orderInfo);
			
			MiaoshaOrder miaoshaOrder = new MiaoshaOrder();
			miaoshaOrder.setGoodsId(goodsId);
			miaoshaOrder.setOrderId(orderInfo.getId());
			miaoshaOrder.setUserId(userId);
			
			orderService.insertMiaoshaOrder(miaoshaOrder);
			return orderInfo;
		}
}
