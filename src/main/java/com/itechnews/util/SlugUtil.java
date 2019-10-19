package com.itechnews.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

public class SlugUtil {

	public static String makeSlug(String title) {
		String slug = Normalizer.normalize(title, Normalizer.Form.NFD);
		Pattern pattern = Pattern.compile("\\p{InCombiningDiacriticalMarks}+");
		slug = pattern.matcher(slug).replaceAll("");
		slug = slug.toLowerCase();
		// replace Đ with D
		slug = slug.replaceAll("đ", "d");
		// remove special character
		slug = slug.replaceAll("([^0-9a-z-\\s])", "");
		// replace SPACE with -
		slug = slug.replaceAll("[\\s]", "-");
		// replace multiple - with one -
		slug = slug.replaceAll("(-+)", "-");
		// remove - at the first or the end
		slug = slug.replaceAll("^-+", "");
		slug = slug.replaceAll("-+$", "");
		return slug;
	}
}