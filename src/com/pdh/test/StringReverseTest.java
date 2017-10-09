package com.pdh.test;

/**
 * 手动实现字符串反转
 *
 * @auther: alexpdh
 * @create: 2017-10-07 13:50
 */
public class StringReverseTest {

	private static String reverse(String originStr) {
		if (null == originStr || originStr.length() <= 0) {
			return originStr;
		} else {
			return reverse(originStr.substring(1)) + originStr.charAt(0);
		}
	}
	public static void main(String[] args) {
		String originStr = "Program";
		System.out.println(reverse(originStr));
	}
}
