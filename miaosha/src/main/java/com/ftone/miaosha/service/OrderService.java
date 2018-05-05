package com.ftone.miaosha.service;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftone.miaosha.dao.OrderDao;
import com.ftone.miaosha.domain.MiaoshaOrder;
import com.ftone.miaosha.domain.OrderInfo;
import com.ftone.miaosha.exception.GlobalException;
import com.ftone.miaosha.redis.OrderKey;
import com.ftone.miaosha.redis.RedisService;
import com.ftone.miaosha.result.CodeMsg;
import com.ftone.miaosha.vo.GoodsVo;

@Service
public class OrderService {
	
	@Autowired
	OrderDao orderDao;
	
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	RedisService redisService;
	
	public OrderInfo getOrderByUserIdandGoodsId(Long userId, Long goodsId) {
		try {
			OrderInfo orderInfo=redisService.get(OrderKey.getOrderByUserIDandGoodsId, ""+userId+goodsId.toString(),OrderInfo.class);
			if(orderInfo==null){
				return orderDao.getOrderByUserIdandGoodsId(userId,goodsId);
			}
			return orderInfo;
		} catch (Exception e) {
		e.printStackTrace();
		}
		return orderDao.getOrderByUserIdandGoodsId(userId,goodsId);
	}

	public void reduceStock(Long goodsId) {
		// TODO Auto-generated method stub
		goodsService.reduceStock(goodsId);
	}

	public void insertOrderInfo(OrderInfo orderInfo) {
		orderDao.insertOrderInfo(orderInfo);
		
	}

	public void insertMiaoshaOrder(MiaoshaOrder miaoshaOrder) {
		orderDao.insertMiaoshaOrder(miaoshaOrder);
		
	}

	public OrderInfo getOrderById(Long orderId) {
		return orderDao.getOrderById(orderId);
	}
	
	

}
