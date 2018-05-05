package com.ftone.miaosha.rabbitmq;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ftone.miaosha.domain.MiaoshaUser;
import com.ftone.miaosha.domain.OrderInfo;
import com.ftone.miaosha.exception.GlobalException;
import com.ftone.miaosha.redis.OrderKey;
import com.ftone.miaosha.redis.RedisService;
import com.ftone.miaosha.result.CodeMsg;
import com.ftone.miaosha.result.Result;
import com.ftone.miaosha.service.GoodsService;
import com.ftone.miaosha.service.MiaoshaService;
import com.ftone.miaosha.service.OrderService;
import com.ftone.miaosha.vo.GoodsVo;


@Service
public class MQSender {

	private static Logger log = LoggerFactory.getLogger(MQSender.class);
	
	@Autowired
	AmqpTemplate amqpTemplate ;
	
	@Autowired
	RedisService redisService;
	
	/*public void sendMiaoshaMessage(MiaoshaMessage mm) {
		String msg = RedisService.beanToString(mm);
		log.info("send message:"+msg);
		amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, msg);
	}*/
	
//	public void send(Object message) {
//		String msg = RedisService.beanToString(message);
//		log.info("send message:"+msg);
//		amqpTemplate.convertAndSend(MQConfig.QUEUE, msg);
//	}
//	
	public void  sendTopic(Object message) {
		String msg = redisService.beanToString(message);
	
		System.out.println("send topic message:"+msg);
		
		amqpTemplate.convertAndSend(MQConfig.TOPIC_EXCHANGE, "topic.key2", msg);
	}
//	
//	public void sendFanout(Object message) {
//		String msg = RedisService.beanToString(message);
//		log.info("send fanout message:"+msg);
//		amqpTemplate.convertAndSend(MQConfig.FANOUT_EXCHANGE, "", msg);
//	}
//	
//	public void sendHeader(Object message) {
//		String msg = RedisService.beanToString(message);
//		log.info("send fanout message:"+msg);
//		MessageProperties properties = new MessageProperties();
//		properties.setHeader("header1", "value1");
//		properties.setHeader("header2", "value2");
//		Message obj = new Message(msg.getBytes(), properties);
//		amqpTemplate.convertAndSend(MQConfig.HEADERS_EXCHANGE, "", obj);
//	}

	@Autowired
	GoodsService goodsService;
	@Autowired
	OrderService orderService;
	@Autowired
	MiaoshaService miaoshaService;
	
	public void miaoshaSendTopic(MiaoshaMessage message) {
		String msg = redisService.beanToString(message);
		amqpTemplate.convertAndSend(MQConfig.MIAOSHA_QUEUE, "topic.miaosha", msg);
	}
		

	
}
