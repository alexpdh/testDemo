package com.pdh.test;

import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 手动实现字符串反转
 *
 * @author: alexpdh
 * @date: 2017-10-07 13:50
 */
public class StringReverseTest {

	private static String reverse(String originStr) {
		if (null == originStr || originStr.length() <= 0) {
			return originStr;
		} else {
			String sub = originStr.substring(1);
			char ch = originStr.charAt(0);
			return reverse(sub) + ch;
		}
	}

	public static void main(String[] args) {
		String originStr = "Program";
		System.out.println(reverse(originStr));
	}
}
