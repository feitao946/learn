package com.ftone.miaosha.controller;

import java.util.List;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.CollectionUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ftone.miaosha.domain.MiaoshaUser;
import com.ftone.miaosha.domain.OrderInfo;
import com.ftone.miaosha.rabbitmq.MQSender;
import com.ftone.miaosha.rabbitmq.MiaoshaMessage;
import com.ftone.miaosha.redis.MiaoShaKey;
import com.ftone.miaosha.redis.OrderKey;
import com.ftone.miaosha.redis.RedisService;
import com.ftone.miaosha.result.CodeMsg;
import com.ftone.miaosha.result.Result;
import com.ftone.miaosha.service.GoodsService;
import com.ftone.miaosha.service.MiaoshaService;
import com.ftone.miaosha.service.OrderService;
import com.ftone.miaosha.vo.GoodsVo;
import com.ftone.miaosha.vo.OrderDetailVo;

@Controller
@RequestMapping("/miaosha")
public class MiaoshaController implements InitializingBean{
	@Autowired
	GoodsService goodsService;
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	MiaoshaService miaoshaService;
	
	@Autowired
	RedisService redisService;
	
	@Autowired
	MQSender mqSender;
	
	@PostMapping("/do_miaosha")
	@ResponseBody
	public Result<Integer> doMiaosha(@RequestParam("goodsId")Long goodsId,
			 MiaoshaUser user){
		//redis预减库存
		Long stockCount = redisService.decr(MiaoShaKey.stockCount, ""+goodsId);
		if(stockCount<=0){
			return Result.error(CodeMsg.MIAO_SHA_OVER);
		}
		
		//判断是否已秒杀过
	OrderInfo orderInfo=orderService.getOrderByUserIdandGoodsId(user.getId(),goodsId);
	if(orderInfo!=null){
		return Result.error(CodeMsg.REPEATE_MIAOSHA);
	}
		MiaoshaMessage message = new MiaoshaMessage();
		message.setGoodsId(goodsId);
		message.setUser(user);
		//请求入队，返回排队中
		mqSender.sendTopic(message);;
		
		return Result.success(0);
		
		
	
	}

	@GetMapping("/getMiaoshaResult")
	@ResponseBody
	public Result<Long> getMiaoshaResult(MiaoshaUser user,@RequestParam("goodsId")Long goodsId){
		OrderInfo orderInfo = orderService.getOrderByUserIdandGoodsId(user.getId(), goodsId);
		boolean exists = redisService.exists(MiaoShaKey.isGoodsOver, ""+goodsId);
		if(orderInfo==null){
			//是减库存失败，还是重复秒杀过
			
			if(exists){
				//秒杀失败
				return Result.success(-1L);
				}else {
					//没处理完，继续轮询
					return Result.success(0L);
				}
			
		}else {
			
			return Result.success(orderInfo.getId());
		}
	}
	
	
	
	@Override
	public void afterPropertiesSet() throws Exception {
		//系统初始化前将商品存入Redis
		List<GoodsVo> goodsList = goodsService.getGoodsList();
		if(CollectionUtils.isEmpty(goodsList)){
			return;
		}
		for (GoodsVo goods : goodsList) {
			redisService.set(MiaoShaKey.stockCount, ""+goods.getId(), goods.getStockCount());
		}
		
	}
}
