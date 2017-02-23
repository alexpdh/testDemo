package com.pdh.test.thread.lock;

import java.util.concurrent.locks.ReentrantLock;
/**
 * 
 * @ClassName:ReentrantLockExample
 * @Function: ReentrantLock 实现线程同步
 * @version
 *
 * @author pengdh
 * @date: 2017年2月23日 下午2:08:19
 * 
 * ReentrantLock的实现依赖于java同步器框架AbstractQueuedSynchronizer
 * 
 * ReentrantLock分为公平锁和非公平锁，我们首先分析公平锁。
 * 使用公平锁时，加锁方法lock()的方法调用轨迹如下：
 * 1、ReentrantLock : lock()
 * 2、FairSync : lock()
 * 3、AbstractQueuedSynchronizer : acquire(int arg)
 * 4、ReentrantLock : tryAcquire(int acquires)	这步真正开始加锁
 * 
 * 在使用公平锁时，解锁方法unlock()的方法调用轨迹如下：
 * 1、ReentrantLock : unlock()
 * 2、AbstractQueuedSynchronizer : release(int arg)
 * 3、Sync : tryRelease(int releases)	这步开始真正释放锁
 * 
 * 使用非公平锁时，加锁方法lock()的方法调用轨迹如下：
 * 1、ReentrantLock : lock()
 * 2、NonfairSync : lock()
 * 3、AbstractQueuedSynchronizer : compareAndSetState(int expect, int update)	这步真正开始加锁
 * 
 * 在使用非公平锁时释放锁的方式和公平锁一样
 * 
 * 现在对公平锁和非公平锁的内存语义做个总结：
 * 公平锁和非公平锁释放时，最后都要写一个volatile变量state。
 * 公平锁获取时，首先会去读这个volatile变量。
 * 非公平锁获取时，首先会用CAS更新这个volatile变量,这个操作同时具有volatile读和volatile写的内存语义。
 */
public class ReentrantLockExample {
	private int i = 0;
	ReentrantLock lock = new ReentrantLock();
	
	public void writer() {
		lock.lock();	// 获取锁
		try {
			i++;
		} finally {
			lock.unlock();	// 释放锁
		}
	}
	
	public void reader() {
		lock.lock();	// 获取锁
		try {
			int i2 = i;
			System.out.println(i2);
		} finally {
			lock.unlock();	// 释放锁
		}
	}
}
