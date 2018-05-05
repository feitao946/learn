package com.ftone.miaosha.dao;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.SelectKey;

import com.ftone.miaosha.domain.MiaoshaOrder;
import com.ftone.miaosha.domain.OrderInfo;

@Mapper
public interface OrderDao {
	@Select("select * from order_info where user_id=#{userId} and goods_id=#{goodsId}")
	OrderInfo getOrderByUserIdandGoodsId(@Param("userId")Long userId,@Param("goodsId") Long goodsId);
	
	@Insert("insert into order_info(user_id,goods_id,delivery_addr_id,goods_name,goods_count,goods_price,order_channel,status,create_date,pay_date)values"
			+ "(#{userId},#{goodsId},#{deliveryAddrId},#{goodsName},#{goodsCount},#{goodsPrice},#{orderChannel},#{status},#{createDate},#{payDate})")
	
	@SelectKey(keyColumn="id",keyProperty="id",before=false,resultType=long.class,statement="select last_insert_id()")
	int insertOrderInfo(OrderInfo orderInfo);
	
	@Insert("insert into miaosha_order(user_id,goods_id,order_id)values(#{userId},#{goodsId},#{orderId})")
	int insertMiaoshaOrder(MiaoshaOrder miaoshaOrder);
	
	@Select("select * from order_info where id=#{orderId}")
	OrderInfo getOrderById(@Param("orderId")Long orderId);
}
