package com.pdh.test.thread;

import java.util.concurrent.CountDownLatch;

/**
 * CountDownLatch示例
 *
 * @auther: alexpdh
 * @create: 2017-10-10 0:21
 */
public class CountDownLatchDemo {
	public static final CountDownLatch countDownLatch = new CountDownLatch(5);

	/**
	 * 在主线程创建了5个工作线程后，就会阻塞在countDownLatch.await()，
	 * 等待5个工作线程全部完成任务后返回。任务的执行顺序可能会不同，
	 * 但是任务完成的Log一定会在最后显示。CountDownLatch通过计数器值的控制，
	 * 实现了允许一个或多个线程等待其他线程完成操作的并发控制。
	 *
	 * @param args
	 * @throws InterruptedException
	 */
	public static void main(String[] args) throws InterruptedException {
		for (int i = 0; i < 5; i ++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + " is start");
					try {
						Thread.sleep(1000);
					} catch (InterruptedException e) {

					}
					// 指定工作线程执行完毕
					countDownLatch.countDown();
					System.out.println(Thread.currentThread().getName() + " is stop");
				}
			}, "task thread " + i).start();
		}
		Thread.sleep(500);
		// 主线程等待
		countDownLatch.await();
		System.out.println("all task has ended");
	}
}
