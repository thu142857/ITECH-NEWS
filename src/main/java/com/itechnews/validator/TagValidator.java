package com.itechnews.validator;

import com.itechnews.entity.Tag;
import com.itechnews.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TagValidator implements Validator {

	@Autowired
	private UserService userService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Tag.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		System.out.println("TagValidator.validate");
		errors.rejectValue("name", "tag.name.existed");
		System.out.println();
	}

}
