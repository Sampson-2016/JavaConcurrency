package edu.njust.four;

import java.util.concurrent.TimeUnit;
/**
 * 
 * @author sampson
 *output
 *count thread 1 Count i: 681790197
 *count thread 2 Count i: 655136592
 *安全终止线程
 *suspend() 进入睡眠而暂停不释放锁  resume（恢复）；
 * stop()不能保证线程的资源能正常释放，不建议使用 
 */
public class Shutdown {
	public static void main(String[] args) throws InterruptedException {
		Runner one=new Runner();
		Thread countThread=new Thread(one,"count thread 1");
		countThread.start();
		TimeUnit.SECONDS.sleep(1);
		countThread.interrupt();
		
		Runner two=new Runner();
		Thread countThread2=new Thread(two,"count thread 2");
		countThread2.start();
		TimeUnit.SECONDS.sleep(1);
		two.cancel();
		TimeUnit.SECONDS.sleep(2);
		
	}
	
	
	private static class Runner implements  Runnable {
		private long i;
		private volatile boolean on=true;
		public void run() {
			while (on&&!Thread.currentThread().isInterrupted()) {
				i++;
			}
			System.out.println(Thread.currentThread().getName()+" Count i: "+i);
		}
		public void cancel(){
			on=false;
		}
	}
}
