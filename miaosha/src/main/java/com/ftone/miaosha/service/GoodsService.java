package com.ftone.miaosha.service;


import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import com.ftone.miaosha.dao.GoodsDao;
import com.ftone.miaosha.dao.UserDao;
import com.ftone.miaosha.domain.MiaoshaUser;
import com.ftone.miaosha.domain.User;
import com.ftone.miaosha.result.Result;
import com.ftone.miaosha.vo.GoodsVo;

@Service
public class GoodsService {
	@Autowired
	GoodsDao goodsDao;
	
	public List<GoodsVo> getGoodsList(){
		List<GoodsVo> goodsList = goodsDao.getGoodsList();
		if(CollectionUtils.isEmpty(goodsList)){
			return new ArrayList<GoodsVo>();
					
		}
		return goodsList;
	}

	public  GoodsVo getGoodsById(Long goodsId) {
		
		return goodsDao.getGoodsById(goodsId);
	}

	public Boolean checkGoodsStock(Long goodsId) {
		GoodsVo goods=goodsDao.getGoodsById(goodsId);
		if(goods==null||goods.getStockCount()<=0){
			return false;
		}
		
		return true;
	}

	public Boolean reduceStock(Long goodsId) {
		int rs = goodsDao.reduceStock(goodsId);
		return rs>0;
		
	}
	
}
