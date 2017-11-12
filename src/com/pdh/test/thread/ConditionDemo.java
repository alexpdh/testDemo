package com.pdh.test.thread;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * Condition 示例
 *
 * @author pengdh
 * @date 2017/11/12
 */
public class ConditionDemo {

	public static void main(String[] args) {
		Lock lock = new ReentrantLock();
		Condition condition = lock.newCondition();
		new Thread(() -> {
			lock.lock();
			System.out.println("thread 1 is waiting");
			try {
				condition.await();
				System.out.println("thread 1 is wake up");
			} catch (InterruptedException e) {
				e.printStackTrace();
			} finally {
				lock.unlock();
			}
		}).start();

		new Thread(() -> {
			lock.lock();
			try {
				System.out.println("thread 2 is running");
				Thread.sleep(3000);
				condition.signal();
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				lock.unlock();
			}
		}).start();
	}
}
