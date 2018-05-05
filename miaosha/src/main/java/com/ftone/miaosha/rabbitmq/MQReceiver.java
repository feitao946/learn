package com.ftone.miaosha.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSON;
import com.ftone.miaosha.domain.MiaoshaOrder;
import com.ftone.miaosha.domain.MiaoshaUser;
import com.ftone.miaosha.domain.OrderInfo;
import com.ftone.miaosha.exception.GlobalException;
import com.ftone.miaosha.redis.OrderKey;
import com.ftone.miaosha.redis.RedisService;
import com.ftone.miaosha.result.CodeMsg;
import com.ftone.miaosha.service.GoodsService;
import com.ftone.miaosha.service.MiaoshaService;
import com.ftone.miaosha.service.OrderService;
import com.ftone.miaosha.vo.GoodsVo;

@Service
public class MQReceiver {

		private static Logger log = LoggerFactory.getLogger(MQReceiver.class);
		
		@Autowired
		RedisService redisService;
		
		@Autowired
		GoodsService goodsService;
		
		@Autowired
		OrderService orderService;
		
		@Autowired
		MiaoshaService miaoshaService;
		
		/*@RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
		public void receive(String message) {
			
		}*/
	
//		@RabbitListener(queues=MQConfig.QUEUE)
//		public void receive(String message) {
//			log.info("receive message:"+message);
//		}
//		
	@RabbitListener(queues=MQConfig.TOPIC_QUEUE2)
		public void receiveTopic1(String message) {
		System.out.println("resecieve topic message:"+message);
		MiaoshaMessage msg = JSON.toJavaObject(JSON.parseObject(message), MiaoshaMessage.class);
		Long goodsId = msg.getGoodsId();
		MiaoshaUser user = msg.getUser();
		//判断是否登录
		if(user==null){
			throw new GlobalException(CodeMsg.NOT_LOGIN);
		}
		
		//判断库存
		GoodsVo goods = goodsService.getGoodsById(goodsId);
		if(goods==null||goods.getStockCount()<=0){
			return;
		}
		
		//判断是否已秒杀过
		OrderInfo orderInfo=orderService.getOrderByUserIdandGoodsId(user.getId(),goodsId);
		if(orderInfo!=null){
			return;
		}
		//减库存，下单，写入秒杀订单
		OrderInfo order=miaoshaService.miaosha(user.getId(),goodsId);
		
		redisService.set(OrderKey.getOrderByUserIDandGoodsId, ""+user.getId()+goodsId, order);
		
		}
		
	
	
	
	
	
	
	
	
		/*@RabbitListener(queues=MQConfig.MIAOSHA_QUEUE)
		public void miaoshaReceiveTopic(String message) {
			MiaoshaMessage msg = JSON.toJavaObject(JSON.parseObject(message), MiaoshaMessage.class);
			Long goodsId = msg.getGoodsId();
			MiaoshaUser user = msg.getUser();
			//判断是否登录
			if(user==null){
				throw new GlobalException(CodeMsg.NOT_LOGIN);
			}
			
			//判断库存
			GoodsVo goods = goodsService.getGoodsById(goodsId);
			if(goods==null||goods.getStockCount()<=0){
				throw new GlobalException(CodeMsg.MIAO_SHA_OVER);
			}
			
			//判断是否已秒杀过
			OrderInfo orderInfo=orderService.getOrderByUserIdandGoodsId(user.getId(),goodsId);
			if(orderInfo!=null){
				throw new GlobalException(CodeMsg.REPEATE_MIAOSHA);
			}
			//减库存，下单，写入秒杀订单
			OrderInfo order=miaoshaService.miaosha(user.getId(),goodsId);
			
			redisService.set(OrderKey.getOrderByUserIDandGoodsId, ""+user.getId()+goodsId, order);
			}*/
		}
		
//		@RabbitListener(queues=MQConfig.HEADER_QUEUE)
//		public void receiveHeaderQueue(byte[] message) {
//			log.info(" header  queue message:"+new String(message));
//		}
//		
		

