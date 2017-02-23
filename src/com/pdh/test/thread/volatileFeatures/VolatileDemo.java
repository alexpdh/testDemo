/**
 * @Project Name:testDemo
 * @File Name:VolatileDemo.java
 * @Package Name:com.pdh.testThread
 * @Date:2017年2月12日下午7:10:04
 *
*/

package com.pdh.test.thread.volatileFeatures;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @ClassName:VolatileDemo
 * @Function:  
 * volatile 能保证被修饰变量的可见性，但是不能保证volatile变量相关操作的原子性。
 * 例如：volatile i 是原子性的。但是 i++不是原子性的
 * 解决办法可以使用 synchronized(互斥锁) 或者ReentrantLock(可重入锁)替换解决
 * @version
 *
 * @author pengdh
 * @date: 2017年2月12日 下午7:10:04
 */
public class VolatileDemo {
//	private volatile int number; // 1.1
	
//	private int number;	// 1.2
	
	private int number;	// 1.3
	private Lock lock = new ReentrantLock();	// 1.3
	
	public int getNumber() {
		return this.number;
	}
	// 1.1 volatile 不能保证 number++的原子性
//	public void increase() {
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//		}
//		this.number++;
//	}
	
	// 1.2 使用synchronized 解决原子性操作问题
//	public void increase() {
//		try {
//			Thread.sleep(100);
//		} catch (InterruptedException e) {
//			
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//			
//		}
//		synchronized(this) {
//			this.number++;
//		}
//	}
	
	// 1.3 使用ReentrantLock 可重入锁 解决原子性操作问题
	public void increase() {
		try {
			Thread.sleep(100);
		} catch (InterruptedException e) {
			
			// TODO Auto-generated catch block
			e.printStackTrace();
			
		}
		lock.lock();
		try {
			this.number++;
			
		} finally {
			lock.unlock();
		}
	}
	
	public static void main(String[] args) {
		final VolatileDemo volDemo = new VolatileDemo();
		for(int i = 0; i < 500; i++) {
			new Thread(new Runnable() {
				
				@Override
				public void run() {
					volDemo.increase();
				}
			}).start();
		}
		// 如果还有子线程在执行，主线程就让出CPU资源，直到所有的子线程都执行完了，主线程再继续往下执行
		while(Thread.activeCount() > 1) {
			Thread.yield();
		}
		System.out.println("number:"+volDemo.getNumber());
	}
}

