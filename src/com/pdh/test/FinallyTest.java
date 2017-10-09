package com.pdh.test;

import java.util.HashMap;
import java.util.Map;

/**
 * finally语句在try或catch中的return语句执行之后，还是return返回之前执行呢？
 * @auther: alexpdh
 * @create: 2017-10-08 18:08
 */
public class FinallyTest {

	/**
	 * finally代码块是在try代码块中的return语句执行之后，返回之前执行的。
	 *
	 * @return
	 */
	private static int test1() {
		int i = 1;
		try {
			System.out.println("try...");
			return i += 10;
		} catch (Exception e) {
			System.out.println("catch...");
		} finally {
			i++;
			System.out.println("finally...");
			System.out.println("i=" + i);
		}
		return i;
	}

	/**
	 * finally代码块中的return语句覆盖try代码块中的return语句。
	 *
	 * @return
	 */
	private static int test2() {
		int i = 1;
		try {
			System.out.println("try...");
			return i += 10;
		} catch (Exception e) {
			System.out.println("catch...");
		} finally {
			i++;
			System.out.println("finally...");
			System.out.println("i=" + i);
			return i;
		}
	}

	/**
	 * 如果finally语句中没有return语句覆盖返回值，那么原来的返回值可能因为finally里的修改而改变也可能不变。
	 * 传值类型的返回值：不变；传址类型的返回值：会变。
	 *
	 * 怎么判断一个变量是传值还是传址？
	 * 传值：8 种基本数据类型及其包装类，字符常量。传址：数组和对象。
	 *
	 * @return
	 */
	private static Map<String, String> test3() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("KEY", "INIT");
		try {
			System.out.println("try...");
			map.put("KEY", "TRY");
			return map;
		} catch (Exception e) {
			System.out.println("catch...");
			map.put("KEY", "CATCH");
		} finally {
			System.out.println("finally...");
			map.put("KEY", "FINALLY");
			map = null;
		}
		return map;
	}

	/**
	 *  try代码块中的return语句在异常的情况下不会被执行，这样具体返回哪个看情况；
	 *  catch中的return执行情况与未发生异常时try中return的执行情况完全一样。
	 *
	 * @return
	 */
	private static int test4() {
		int i = 1;
		try {
			System.out.println("try...");
			i = i / 0;
			return i += 10;
		} catch (Exception e) {
			System.out.println("catch...");
			return i;
		} finally {
			i++;
			System.out.println("finally...");
			System.out.println("i=" + i);
		}
	}

	public static void main(String[] args) {
		System.out.println(test4());
	}
}
