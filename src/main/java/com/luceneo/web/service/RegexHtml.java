package com.luceneo.web.service;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexHtml {
	public String delHtmlTag(String line) {
		String regEx_html = "<[^>]+>";
		// 创建 Pattern 对象
		Pattern r = Pattern.compile(regEx_html);
		// 创建 matcher 对象
		Matcher m = r.matcher(line);
		line = m.replaceAll("");
		return line;
	}
}
