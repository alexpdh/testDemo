package com.pdh.test.thread;

import java.util.concurrent.Semaphore;

/**
 * 信号量示例
 *
 * Semaphore信号量并发工具类，其提供了aquire()和release()方法来进行并发控制。
 * Semaphore一般用于资源限流，限量的工作场景，例如数据库连接控制。
 * 假设数据库的最大负载在10个连接，而现在有100个客户端想进行数据查询，
 * 显然我们不能让100个客户端同时连接上来，找出数据库服务的崩溃。
 * 那么我们可以创建10张令牌，想要连接数据库的客户端，都必须先尝试获取令牌（Semaphore.aquire()），
 * 当客户端获取到令牌后便可以进行数据库连接，并在完成数据查询后归还令牌（Semaphore.release()），
 * 	这样就能保证同时连接数据库的客户端不超过10个，因为只有10张令牌，这里给出该场景的模拟代码。
 *
 * @auther: alexpdh
 * @create: 2017-10-10 1:03
 */
public class SemaphoreDemo {
	private static final Semaphore semaphoreToken = new Semaphore(10);

	public static void main(String[] args) {
		for (int i = 0; i < 100; i++) {
			new Thread(new Runnable() {
				@Override
				public void run() {
					try {
						// 获取令牌
						semaphoreToken.acquire();
						System.out.println("execute sql");
						// 归还令牌
						semaphoreToken.release();
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}).start();
		}
	}
}
