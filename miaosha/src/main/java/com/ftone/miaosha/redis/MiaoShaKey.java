package com.ftone.miaosha.redis;

public class MiaoShaKey extends BasePrefix {

	public MiaoShaKey(int expireSeconds, String prefix) {
		super(expireSeconds, prefix);
	}
	
	public static MiaoShaKey stockCount=new MiaoShaKey(0,"sc");
	public static MiaoShaKey isGoodsOver=new MiaoShaKey(0,"igo");
}
