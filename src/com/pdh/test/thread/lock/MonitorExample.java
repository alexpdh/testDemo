package com.pdh.test.thread.lock;
/**
 * 
 * @ClassName:MonitorExample
 * @Function: 锁示例
 * @version
 *
 * @author pengdh
 * @date: 2017年2月23日 下午1:47:51
 * 根据happens before规则，这个过程包含的happens before 关系可以分为两类：根据程序次序规则，
 * 1 happens before 2, 
 * 2 happens before 3; 
 * 4 happens before 5, 
 * 5 happens before 6。
 * 根据监视器锁规则，3 happens before 4。
 * 根据happens before 的传递性，2 happens before 5。
 */
public class MonitorExample {
	int a = 0;
	
	public synchronized void writer() {	// 1
		a++;	// 2
	}	// 3
	
	public synchronized void reader() {	//  4
		int i = a;	// 5
		System.out.println(i);	// 6
	}
}
