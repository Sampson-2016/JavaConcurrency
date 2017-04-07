package edu.njust.four;

import java.util.concurrent.TimeUnit;
/**
 * 
 * @author sampson
 * 中断是线程的一个标志位属性，表示一个运行中的线程是否被其他线程进行中断操作
 * 其他线程通过调用xx.interrupt()对xx线程进行中断，可以通过xx.isInterrupted()
 * 来判断xx是否被中断, xx线程可以调用静态方法Thread.interrupted()对当前线程的中断
 * 标志进行复位。如果该线程已经处于终结状态，及时该线程被中断过，在调用对象的isInterrupted()
 * 时依然会返回false。
 * output：
 *java.lang.InterruptedException: sleep interrupted
	at java.lang.Thread.sleep(Native Method)
	at java.lang.Thread.sleep(Thread.java:340)
	at java.util.concurrent.TimeUnit.sleep(TimeUnit.java:386)
	at edu.njust.four.Interrupted$SleepRunner.run(Interrupted.java:33)
	at java.lang.Thread.run(Thread.java:745)
		sleepThread interrupted is :false
		busyThread interrupted is :true
	从结果可以看出sleepThread的中断标志位被清除了，而一直运行的busyThread的中断标志位没有被清楚
 */
public class Interrupted {
	public static void main(String[] args) throws InterruptedException {
		Thread sleepThread=new Thread(new SleepRunner(),"sleep Thread");
		sleepThread.setDaemon(true);
		
		Thread busyThread =new Thread(new BusyRunner(),"busy thread");
		busyThread.setDaemon(true);
		
		sleepThread.start();
		busyThread.start();
		
		TimeUnit.SECONDS.sleep(2);
		
		sleepThread.interrupt();
		busyThread.interrupt();
		
		System.out.println("sleepThread interrupted is :" +sleepThread.isInterrupted());
		System.out.println("busyThread interrupted is :" +busyThread.isInterrupted());
	
		TimeUnit.SECONDS.sleep(2);
	}
	
	static class SleepRunner implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while(true){
				try {
					TimeUnit.SECONDS.sleep(10);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					System.out.println();
					e.printStackTrace();
				}
			}
		}
	}
	
	static class BusyRunner implements Runnable{
		
		int i=0;
		public void run() {
			// TODO Auto-generated method stub
			while (true) {
				///System.out.println(i++);
			}
		}
	}
	
}
