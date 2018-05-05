package com.ftone.miaosha.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ftone.miaosha.domain.MiaoshaUser;
import com.ftone.miaosha.result.Result;
import com.ftone.miaosha.service.GoodsService;
import com.ftone.miaosha.service.MiaoShaUserService;
import com.ftone.miaosha.vo.GoodsDetailVo;
import com.ftone.miaosha.vo.GoodsVo;

@Controller
@RequestMapping("/goods/")
public class GoodsController {
	
	@Autowired
private	MiaoShaUserService userService;
	@Autowired
	private	GoodsService goodsService;
	
	
	@RequestMapping("to_list")
	public String toLogin(MiaoshaUser user,
			Model model){
		List<GoodsVo> goodsList = goodsService.getGoodsList();
		model.addAttribute("goodsList", goodsList);
		return "good_list";
	}
	
	@RequestMapping("to_detail/{goodsId}")
	@ResponseBody
	public Result<GoodsDetailVo> toGoodsDetail(@PathVariable("goodsId")Long goodsId,
			MiaoshaUser user,
			Model model){
		//更具ID查出商品信息返回页面展示
		GoodsVo goodsVo=goodsService.getGoodsById(goodsId);
		/*model.addAttribute("goods", goodsVo);
		model.addAttribute("user", user);*/
		
		//秒杀倒计时
		Long startTime = goodsVo.getStartDate().getTime();
		Long endTime = goodsVo.getEndDate().getTime();
		Long nowTime=System.currentTimeMillis();
		int remainSeconds=(int) ((startTime-nowTime)/1000);
		//秒杀状态
		int miaoshaStatus;
		if(nowTime<startTime){
			//秒杀倒计时
			miaoshaStatus=0;
			
		}else if(endTime<nowTime){
			//秒杀结束
			miaoshaStatus=2;
		}else {
			//秒杀进行中 
			miaoshaStatus=1;
		}
		/*model.addAttribute("miaoshaStatus", miaoshaStatus);
		model.addAttribute("remainSeconds", remainSeconds);*/
		GoodsDetailVo goodsDetailVo =new GoodsDetailVo();
		goodsDetailVo.setGoods(goodsVo);
		goodsDetailVo.setUser(user);
		goodsDetailVo.setRemainSeconds(remainSeconds);
 		goodsDetailVo.setMiaoshaStatus(miaoshaStatus);
		
		return Result.success(goodsDetailVo);
		
	}
	@RequestMapping("to_detail2/{goodsId}")
	public String toGoodsDetail2(@PathVariable("goodsId")Long goodsId,
			MiaoshaUser user,
			Model model){
		//更具ID查出商品信息返回页面展示
		GoodsVo goodsVo=goodsService.getGoodsById(goodsId);
		model.addAttribute("goods", goodsVo);
		model.addAttribute("user", user);
		
		//秒杀倒计时
		Long startTime = goodsVo.getStartDate().getTime();
		Long endTime = goodsVo.getEndDate().getTime();
		Long nowTime=System.currentTimeMillis();
		int remainSeconds=(int) ((startTime-nowTime)/1000);
		//秒杀状态
		int miaoshaStatus;
		if(nowTime<startTime){
			//秒杀倒计时
			miaoshaStatus=0;
			
		}else if(endTime<nowTime){
			//秒杀结束
			miaoshaStatus=2;
		}else {
			//秒杀进行中 
			miaoshaStatus=1;
		}
		model.addAttribute("miaoshaStatus", miaoshaStatus);
		model.addAttribute("remainSeconds", remainSeconds);
		return "goods_detail";
		
	}
	
	
}
