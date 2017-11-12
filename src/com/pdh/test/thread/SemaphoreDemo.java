package com.pdh.test.thread;

import java.util.concurrent.Semaphore;

/**
 * 信号量示例
 *
 * @author pengdh
 * @date 2017/11/12
 */
public class SemaphoreDemo {
	private static final Semaphore semaphoreToken = new Semaphore(5);

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			new Thread(() -> {
				// 获取令牌
				try {
					semaphoreToken.acquire();
					Thread.sleep(2000);
					System.out.println(Thread.currentThread().getId() + " finished");
					// 归还令牌
					semaphoreToken.release();
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}
	}
}
