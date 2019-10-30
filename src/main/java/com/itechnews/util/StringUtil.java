package com.itechnews.util;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class StringUtil {

	public static String md5(String str) {
		MessageDigest md;
		String result = "";
		try {
			md = MessageDigest.getInstance("MD5");
			md.update(str.getBytes());
			BigInteger bi = new BigInteger(1, md.digest());

			result = bi.toString(16);
		} catch (NoSuchAlgorithmException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String getParagraphsFromHtml(String html) {
		StringBuilder result = new StringBuilder();
		Document doc = Jsoup.parse(html);
		Elements paragraphs = doc.select("p");
		for (int i = 0; i < paragraphs.size(); i++) {
			result.append(paragraphs.get(i).text());
		}
		return result.toString();
	}

	// lấy chư mà giới hạn số lượng chữ mà ko bị cắt ngang chữ
	public static String getThreeDotsText(String str, int totalChar) {
		if (totalChar > str.length()) {
			return str;
		}
		String result = "";
		try {
			int endIndex = str.lastIndexOf(" ", totalChar);
			result = str.substring(0, endIndex);
		} catch (Exception e) {
			System.out.println("Lỗi xử lí rút gọn text");
		}
		return result + " ...";
	}

	public static String getThreeDotsTextFromHtml(String html, int totalChar) {
		String str = getParagraphsFromHtml(html);
		return getThreeDotsText(str, totalChar);
	}

	public static void main(String[] args) {
		String str1 = "“Nhớ…tiếng mưa rơi ngày xưa lúc đôi ta còn nhau, khi tình yêu…" + "bắt đầu…….”\r\n"
				+ "Những ca từ quen thuộc của ngày nào bổng vang lên giữa một buổi chiều mưa nhẹ rơi…Đã từ "
				+ "rất lâu rồi tôi mới được nghe lại bài hát này. Bài hát khiến\r\n"
				+ "		 tôi nhớ về kỷ niệm một thời mà tôi cứ nghỡ như chuyện mới vừa xãy ra hôm qua\r\n"
				+ "		 vậy…!!!.";
		System.out.println(getThreeDotsText(str1, 30));
		/*
		 * String str = " tran  huu tran tran hong  son"; int pos = 0;
		 */
	}

}
