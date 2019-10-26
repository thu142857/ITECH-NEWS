package com.itechnews.validator;

import com.itechnews.entity.Tag;
import com.itechnews.entity.User;
import com.itechnews.repository.UserRepository;
import com.itechnews.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class UserEditingValidator implements Validator {

	@Autowired
	private UserRepository userRepository;

	@Override
	public boolean supports(Class<?> clazz) {
		return User.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target instanceof User) {
			User user = (User) target;
			Integer count = userRepository.countAllByEmailEquals(user.getEmail());
			if (count > 1) {
				errors.rejectValue("email", "user.email.existed");
			}
		}
	}

}
