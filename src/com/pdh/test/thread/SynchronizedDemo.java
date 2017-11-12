/**
 * @Project Name:test
 * @File Name:SynchronizedTest.java
 * @Package Name:com.pdh
 * @Date:2017年2月12日下午5:36:46
 */

package com.pdh.test.thread;


/**
 * synchronized 具有原子性和可见性
 *
 * @author pengdh
 * @date 2017/11/12
 */
public class SynchronizedDemo {

	private int safeNum = 0;
	private int unsafeNum = 0;

	/**
	 * 使用 synchronized 同步实现复合运算，线程安全的
	 */
	private synchronized void safeIncrease() {
		safeNum++;
	}

	/**
	 * 普通复合运算，线程不安全的
	 */
	private void unsafeIncrease() {
		unsafeNum++;
	}

	public static void main(String[] args) {
		SynchronizedDemo demo = new SynchronizedDemo();
		for (int i = 0; i < 20000; i++) {
			new Thread(() -> {
				demo.unsafeIncrease();
				demo.safeIncrease();
			}).start();
		}

		while (Thread.activeCount() > 2) {
			Thread.yield();
		}

		System.out.println("unsafeNum: " + demo.unsafeNum);
		System.out.println("safeNum: " + demo.safeNum);
	}
}

