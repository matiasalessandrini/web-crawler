package com.santex.webcrawler.validator;

import java.util.function.Predicate;

import org.springframework.util.StringUtils;

public interface ControllerValidator extends Predicate<String> {
	
	static ControllerValidator isValidParameter() {
		return p -> StringUtils.hasLength(p);
	}


}
