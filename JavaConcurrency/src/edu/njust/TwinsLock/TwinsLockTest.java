package edu.njust.TwinsLock;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;

public class TwinsLockTest {
	public void test() {
		final Lock lock=new TwinsLock();
		class Worker extends Thread{
			public void run(){
				while (true) {
					lock.lock();
					try {
						TimeUnit.SECONDS.sleep(1);
						System.out.println(Thread.currentThread().getName());
						TimeUnit.SECONDS.sleep(1);
					} catch (Exception e) {
						// TODO: handle exception
					}finally{
						lock.unlock();
					}
				}
			}
		}
		
		for (int i = 0; i < 10; i++) {
			Worker worker=new Worker();
			worker.setDaemon(true);
			worker.start();
			
		}
		
		for (int i = 0; i < 10; i++) {
			try {
				TimeUnit.SECONDS.sleep(1);
				System.out.println();
			} catch (Exception e) {
				// TODO: handle exception
			}
		}
	}
	public static void main(String[] args) {
		TwinsLockTest t=new TwinsLockTest();
		t.test();
	}
}
