package com.ftone.miaosha.exception;

import java.util.List;

import org.springframework.validation.BindException;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ftone.miaosha.result.CodeMsg;
import com.ftone.miaosha.result.Result;

@ControllerAdvice
@ResponseBody
public class GlobalExceptionHandle {
	@ExceptionHandler(value=Exception.class)
	public Result<String> exceptionHandle(Exception e){
		if(e instanceof GlobalException){
			GlobalException gException=(GlobalException)e;
			return Result.error(gException.getCm());
		}else if(e instanceof BindException){
			BindException bException=(BindException)e;
			List<ObjectError> errors = bException.getAllErrors();
			ObjectError error = errors.get(0);
			String message = error.getDefaultMessage();
			return Result.error(CodeMsg.BIND_ERROR.fillArgs(message));
		}
		e.printStackTrace();
		
		return Result.error(CodeMsg.SERVER_ERROR);
	}
}
