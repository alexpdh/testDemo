package com.pdh.testThread;

public class TestSynchronizedThread implements Runnable {
	Timer timer = new Timer();
	public static void main(String[] args) {
		TestSynchronizedThread test = new TestSynchronizedThread();
		Thread t1 = new Thread(test);
		Thread t2 = new Thread(test);
		t1.setName("t1");
		t2.setName("t2");
		t1.start();
		t2.start();
	}
	@Override
	public void run() {
		timer.add(Thread.currentThread().getName());
		
	}
	
}

class Timer {
	private static int num;
	public synchronized void add(String name){
		num++;
		try {
			Thread.sleep(1);
		} catch (InterruptedException e) {
		}
		System.out.println(name+"你是第"+num+"个使用timer的线程");
	}
}