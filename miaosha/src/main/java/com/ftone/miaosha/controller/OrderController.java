package com.ftone.miaosha.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ftone.miaosha.domain.MiaoshaUser;
import com.ftone.miaosha.domain.OrderInfo;
import com.ftone.miaosha.result.CodeMsg;
import com.ftone.miaosha.result.Result;
import com.ftone.miaosha.service.GoodsService;
import com.ftone.miaosha.service.OrderService;
import com.ftone.miaosha.vo.GoodsVo;
import com.ftone.miaosha.vo.OrderDetailVo;

@Controller
@RequestMapping("/order")
public class OrderController {
	
	@Autowired
	OrderService orderService;
	
	@Autowired
	GoodsService goodService;
	
	@RequestMapping("/orderDetail/{orderId}")
	@ResponseBody
	public Result<OrderDetailVo> getOrderDetail(@PathVariable("orderId")Long orderId,
			MiaoshaUser user){
		if(user==null){
			Result.error(CodeMsg.NOT_LOGIN);
		}
		OrderInfo orderInfo=orderService.getOrderById(orderId);
		if(orderInfo==null){
			Result.error(CodeMsg.ORDER_NOT_EXIST);
		}
		GoodsVo goodsVo = goodService.getGoodsById(orderInfo.getGoodsId());
		OrderDetailVo orderDetailVo = new OrderDetailVo();
		orderDetailVo.setGoods(goodsVo);
		orderDetailVo.setOrderInfo(orderInfo);
		return Result.success(orderDetailVo);
	}
	
}
