package com.pdh.test.thread;

/**
 * @author: alexpdh
 * @date: 2017/10/23
 */
public class ThreadLocalTest {
	private static ThreadLocal<Integer> seqNum = new ThreadLocal<Integer>() {
		@Override
		protected Integer initialValue() {
			return 0;
		}
	};

	public static int getNextNum() {
		seqNum.set(seqNum.get() + 1);
		return seqNum.get();
	}

	public static void main(String[] args) {
		for (int i = 0; i < 3; i++) {
			new Thread() {
				@Override
				public void run() {
					for (int j = 0; j < 3; j++) {
						System.out.println("thread[" + Thread.currentThread().getName() + "] --> sn[" + getNextNum() + "]");
					}
				}
			}.start();
		}
	}
}
