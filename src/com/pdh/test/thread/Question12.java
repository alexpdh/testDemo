package com.pdh.test.thread;

/**
 * 线程协调问题
 *
 * 题目：多线程之间需要等待协调，才能完成某种工作，问怎么设计这种协调方案？
 * 如：子线程循环10次，接着主线程循环100，接着又回到子线程循环10次，
 * 接着再回到主线程又循环100，如此循环50次。
 *
 * @auther: alexpdh
 * @create: 2017-10-10 1:17
 */
public class Question12 {

	public static void main(String[] args) throws InterruptedException {
		final Object object = new Object();
		new Thread(new Runnable() {
			@Override
			public void run() {
				for (int i = 0; i < 50; i++) {
					synchronized (object) {
						for (int j = 0; j < 10; j++) {
							System.out.println("SubThread:" + (j + 1));
						}
						object.notify();
						try {
							object.wait();
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
			}
		}).start();

		for (int i = 0; i < 50; i++) {
			synchronized (object) {
				//主线程让出锁，等待子线程唤醒
				object.wait();
				for (int j = 0; j < 100; j++) {
					System.out.println("MainThread:" + (j + 1));
				}
				object.notify();
			}
		}
	}
}
