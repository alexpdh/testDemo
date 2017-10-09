package com.pdh.test.thread;

import java.util.concurrent.CyclicBarrier;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * CyclicBarrier示例
 *
 * CyclicBarrier就字面意思是可循环的屏障，其体现了两个特点，可循环和屏障。
 * 调用CyclicBarrier的await()方法便是在运行线程中插入了屏障，当线程运行到这个屏障时，
 * 便会阻塞在await()方法中，直到等待所有线程运行到屏障后，才会返回。
 * CyclicBarrier的构造函数同样接受一个int类型的参数，表示屏障拦截线程的数目。
 * 另一个特点循环便是体现处出了CyclicBarrier与CountDownLatch不同之处了，
 * CyclicBarrier可以通过reset()方法，将N值重置，循环使用，而CountDownLatch的计数器是不能重置的。
 * 此外，CyclicBarrier还提供了一个更高级的用法，允许我们设置一个所有线程到达屏障后，
 * 便立即执行的Runnable类型的barrierAction（注意：barrierAction不会等待await()方法的返回才执行，
 * 是立即执行！）机会，这里我们通过以下代码来测试一下CyclicBarrier的特性。
 *
 * @auther: alexpdh
 * @create: 2017-10-10 0:36
 */
public class CyclicBarrierDemo {

	private static final CyclicBarrier cyclicBarrier = new CyclicBarrier(5, new MyBarrierAction());
	private static final AtomicInteger acti = new AtomicInteger(1);

	public static void main(String[] args) {
		for (int i = 0; i < 10; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					System.out.println(Thread.currentThread().getName() + " is start");
					try {
						Thread.sleep(acti.getAndIncrement() * 1000);
						cyclicBarrier.await();
						System.out.println(Thread.currentThread().getName() + " is stop");
					} catch (Exception e) {
						e.printStackTrace();
					}
				}
			}, "Thread-" + i).start();
		}
	}

	private static class MyBarrierAction implements Runnable {

		@Override
		public void run() {
			System.out.println("MyBarrierAction is call");
		}
	}
}
