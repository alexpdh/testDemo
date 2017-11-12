package com.pdh.test;


/**
 * volatile 复合操作测试
 *
 * @author pengdh
 * @date 2017/11/12
 */
public class VolatileDemo {
	//	申明 volatile 变量
	private volatile boolean flag = false;
	//	计数
	private static final int COUNT = 10;

	/**
	 * 使用 volatile 变量作为线程结束标志
	 */
	private void start() {
		new Thread(() -> {
			while (!flag) {
				System.out.println("Thread is running");
			}
		}).start();
	}

	private void shutdown() {
		flag = true;
		System.out.println("Thread is stop");
	}

	public static void main(String[] args) throws InterruptedException {
		VolatileDemo demo = new VolatileDemo();
		demo.start();
		Thread.sleep(2000);
		demo.shutdown();
	}
}
