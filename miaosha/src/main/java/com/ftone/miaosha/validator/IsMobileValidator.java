package com.ftone.miaosha.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

import org.apache.commons.lang3.StringUtils;

import com.ftone.miaosha.util.ValidatorUtil;

public class IsMobileValidator implements ConstraintValidator<IsMobile,String>{
	private boolean required=false;
	
public void initialize(IsMobile constrintAnnotation){
	required=constrintAnnotation.required();
	}
	
	@Override
	public boolean isValid(String value, ConstraintValidatorContext context) {
		if(required){
			return ValidatorUtil.isMobile(value);
		}else{
			if (StringUtils.isEmpty(value)) {
				return true;
			}else{
				return ValidatorUtil.isMobile(value);
			}
		}
	};
	
	

}
