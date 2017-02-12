package com.pdh.testThread;

import java.util.ArrayList;
import java.util.List;

/**
 * 生产者消费者
 * @author Administrator
 *
 */
public class ProducerConsumer {
	/** 定义一个盘子，一个专门放鸡蛋的线程，一个专门拿鸡蛋的线程 ,假设盘子只能放一个鸡蛋*/
	List<Object> eggs = new ArrayList<Object>();
	//取鸡蛋
	public synchronized Object getEgg(){
		while(eggs.size()==0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		Object egg = eggs.get(0);
		eggs.clear();// 清空盘子  
        notify();// 唤醒阻塞队列的某线程到就绪队列  
        System.out.println("拿到鸡蛋");
		return egg;
	}
	//放雞蛋
	public synchronized void putEgg(Object egg){
		while(eggs.size()>0){
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		 eggs.add(egg);// 往盘子里放鸡蛋  
	     notify();// 唤醒阻塞队列的某线程到就绪队列  
	     System.out.println("放入鸡蛋"); 
	}
	
	static class PutThread implements Runnable{
		private ProducerConsumer pc;
		Object egg = new Object();
		public PutThread (ProducerConsumer pc){
			this.pc = pc;
		}
		@Override
		public void run() {
			pc.putEgg(egg);
			
		}
		
	}
	
	static class GetThread implements Runnable{
		private ProducerConsumer pc;
		public GetThread(ProducerConsumer pc){
			this.pc = pc;
		}
		@Override
		public void run() {
			pc.getEgg();
			
		}
		
	}
	public static void main(String[] args) {
		ProducerConsumer pc = new ProducerConsumer();
		for (int i = 0; i < 10; i++) {
			PutThread t1 = new PutThread(pc);
			GetThread t2 = new GetThread(pc);
			new Thread(t1).start();
			new Thread(t2).start();
		}
	}
}
