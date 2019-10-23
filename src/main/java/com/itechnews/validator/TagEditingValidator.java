package com.itechnews.validator;

import com.itechnews.entity.Tag;
import com.itechnews.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

@Component
public class TagEditingValidator implements Validator {

	@Autowired
	private TagService tagService;

	@Override
	public boolean supports(Class<?> clazz) {
		return Tag.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		if (target instanceof Tag) {
			Tag tag = (Tag) target;
			Integer count = tagService.countAllByNameEquals(tag.getName());
			if (count > 1) {
				errors.rejectValue("name", "tag.name.existed");
			}
		}
	}

}
