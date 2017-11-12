package com.pdh.test.thread;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * 读写锁示例
 *
 * @author pengdh
 * @date 2017/11/13
 */
public class ReadWriteLockDemo {

	public static void main(String[] args) {
		ReadWriteLock readWriteLock = new ReentrantReadWriteLock();
		new Thread(() -> {
			readWriteLock.readLock().lock();
			try {
				System.out.println("the first read lock begin");
				Thread.sleep(1000);
				System.out.println("the first read lock end");
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				readWriteLock.readLock().unlock();
			}
		}).start();

		new Thread(() -> {
			readWriteLock.readLock().lock();
			try {
				System.out.println("the second read lock begin");
				Thread.sleep(1000);
				System.out.println("the second read lock end");
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				readWriteLock.readLock().unlock();
			}
		}).start();

		new Thread(() -> {
			readWriteLock.writeLock().lock();
			try {
				System.out.println("the write lock begin");
				Thread.sleep(1000);
				System.out.println("the write read lock end");
			} catch (Exception e) {
				e.printStackTrace();
			}finally {
				readWriteLock.writeLock().unlock();
			}
		}).start();
	}
}
