package com.ftone.miaosha.result;

public class CodeMsg {
	private int code;
	private String msg;
	//通用异常
	public static CodeMsg SUCCESS=new CodeMsg(0,"success");
	public static CodeMsg SERVER_ERROR=new CodeMsg(5001000,"服务端异常");
	public static CodeMsg BIND_ERROR=new CodeMsg(5001001,"参数校验异常：%s");
	
	//登录模块
	public static CodeMsg MOBILE_NOT_EXIST=new CodeMsg(5002001,"手机号不存在");
	public static CodeMsg PASSWORD_ERROR=new CodeMsg(5002002,"密码错误");
	public static CodeMsg NOT_LOGIN=new CodeMsg(5002002,"未登录");
	
	//秒杀模块
	public static CodeMsg MIAO_SHA_OVER =new CodeMsg(5003001,"商品已经秒杀完毕");
	public static CodeMsg REPEATE_MIAOSHA =new CodeMsg(5003002,"不能重复秒杀");
	private CodeMsg(int code,String msg){
		this.code=code;
		this.msg=msg;
	};
	//订单
	public static CodeMsg ORDER_NOT_EXIST =new CodeMsg(5004002,"订单不存在");
	public int getCode() {
		return code;
	}
	public String getMsg() {
		return msg;
	}
	
	public CodeMsg fillArgs(Object...args){
		int code=this.code;
		String message=String.format(this.msg, args);
				
		return new CodeMsg(code, message);
	}
	
}
