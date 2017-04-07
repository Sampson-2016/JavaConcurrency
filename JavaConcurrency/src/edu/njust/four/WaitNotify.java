package edu.njust.four;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.concurrent.TimeUnit;
/**
 * 
 * @author sampson
 * output 34可能换行输出
 * Thread[wait thread,5,main] flag is true.wait16:35:37
 * Thread[notify thread,5,main]hold lock.notify16:35:39
 * Thread[notify thread,5,main]hold lock again 16:35:44
 * Thread[wait thread,5,main]flag is false.running16:35:49
 * 等待通知经典
 * 等待方
 * synchronized(对象){			
 * 	while(条件不满足){				
 * 		对象.wait();				
 * 	}							
 * 	对应的处理逻辑					
 * }
 * 1获取对象的锁
 * 2条件不满足时，调用对象的wait方法
 * 被通知后仍检查条件
 * 3执行逻辑
 * 通知方
 * synchronized(对象){			
 * 	改变条件；						
 * 对象.notify();or notifyAll()	
 * }
 * 1获取对象的锁
 * 2改变条件
 * 3通知所有等待在对象上的线程，这个时候等待方从等待队列移到同步队列
 */


public class WaitNotify {
	static boolean flag=true;
	static Object lock=new Object();
	public static void main(String[] args) throws InterruptedException {
		Thread waitThread=new Thread(new Wait(),"wait thread");
		waitThread.start();
		Thread.sleep(2000);
		
		Thread notifyThread=new Thread(new Notofy(),"notify thread");
		notifyThread.start();
	}
	static class Wait implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized (lock) {
				while(flag){
					try {
						System.out.println(Thread.currentThread()+" flag is true.wait"
								+new SimpleDateFormat("HH:mm:ss").format(new Date()));
						lock.wait();
					} catch (Exception e) {
						// TODO: handle exception
					}
				}
				System.out.println(Thread.currentThread()+"flag is false.running"
				+new SimpleDateFormat("HH:mm:ss").format(new Date()));
			}
		}
	}
	
	static class Notofy implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			synchronized(lock){
				System.out.println(Thread.currentThread()+"hold lock.notify"
			+new SimpleDateFormat("HH:mm:ss").format(new Date()));
				lock.notify();
				flag=false;
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
			
			synchronized (lock) {
				System.out.println(Thread.currentThread()+"hold lock again "
			+new SimpleDateFormat("HH:mm:ss").format(new Date()));
				try {
					TimeUnit.SECONDS.sleep(5);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		}
	}
}
