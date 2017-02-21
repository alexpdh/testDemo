/**
 * @Project Name:test
 * @File Name:SynchronizedTest.java
 * @Package Name:com.pdh
 * @Date:2017年2月12日下午5:36:46
 *
*/

package com.pdh.test.thread;
/**
 * @ClassName:SynchronizedTest
 * @Function:  
 * Synchronized 具有原子性和可见性
 * @version
 *
 * @author pengdh
 * @date: 2017年2月12日 下午5:36:46
 */
public class SynchronizedDemo {
	// 共享变量
	private boolean ready = false;
	private int number = 1;
	private int result = 0;
	
	// 写操作
	public synchronized void write() {
		ready = true;
		number = 2;
	}
	
	// 读操作
	public synchronized void read() {
		if(ready) {
			result = number * 3;
		}
		System.out.println("result 的值为：" + result);
	}
	
	// 内部线程类
	private class ReadWriteThread extends Thread {
		// 根据构造方法中传入的flag参数值，确定线程中执行读操作还是写操作
		private boolean flag;
		public ReadWriteThread(boolean flag) {
			this.flag = flag;
		}
		@Override
		public void run() {
			if(flag) {
				// 构造方法中传入true，执行写操作
				write();
			} else {
				read();
			}
		}
	}
	
	public static void main(String[] args) {
		SynchronizedDemo synDemo = new SynchronizedDemo();
		// 通过构造方法传入true执行写操作
		synDemo.new ReadWriteThread(true).start();
		// 通过构造方法传入false执行读操作
		synDemo.new ReadWriteThread(false).start();
	}
}

