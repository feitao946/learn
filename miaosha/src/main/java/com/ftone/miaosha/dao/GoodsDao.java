package com.ftone.miaosha.dao;

import java.util.List;

import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

import com.ftone.miaosha.vo.GoodsVo;
@Mapper
public interface GoodsDao {
	@Select("select g.*,msg.* from goods g left join miaosha_goods msg on g.id=msg.goods_id")
	 List<GoodsVo> getGoodsList();
	@Select("select g.*,msg.* from goods g left join miaosha_goods msg on g.id=msg.goods_id where g.id=#{goodsId}")
	 GoodsVo getGoodsById(@Param("goodsId")Long goodsId) ;
	@Update("update miaosha_goods set stock_count=stock_count-1 where goods_id=#{goodsId}")
	int reduceStock(@Param("goodsId")Long goodsId);
	
	
	
}
