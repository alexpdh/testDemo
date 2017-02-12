package com.pdh.testThread;

public class InterruptTest {
	public static void main(String[] args) throws InterruptedException {
		MyThread t = new MyThread("MyThread");
		t.start();
		Thread.sleep(100);
		t.interrupt();
	}
}

class MyThread extends Thread {
	int i = 0;
	public MyThread(String name){
		super(name);
	}
	public void run(String name){
		while(!isInterrupted()){
			System.out.println(getName()+ "执行了" + ++i + "次");
		}
	}
}