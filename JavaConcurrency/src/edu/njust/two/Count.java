package edu.njust.two;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * 利用循环CAS实现原子操作
 * @author sampson
 *
 */
public class Count {
	private int i=0;
	private AtomicInteger atomic=new AtomicInteger(0);

	public void count(){
		i++;
	}
	/**
	 * a.compareAndSet(b,c)
	 * 若a==b,a换成c，返回true
	 */
	public void safeCount(){
		while(true){
			int i=atomic.get();
			if(atomic.compareAndSet(i, ++i)){
				break;
			}
		}
	}
	
	public static void main(String[] args) {
		final Count c=new Count();
		List<Thread> list=new ArrayList<Thread>();
		for(int i=0;i<10000;i++){
			Thread t=new Thread(new Runnable() {
				
				@Override
				public void run() {
					// TODO Auto-generated method stub
					c.count();
					c.safeCount();
				}
			});
			list.add(t);
		}
		
		for (Thread thread : list) {
			thread.start();
		}
		/**
		 * 让线程在输出i前都能完成
		 */
		for (Thread thread : list) {
			try {
				thread.join();
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		System.out.println("i: "+c.i);
		System.out.println("atomic:  "+c.atomic.get());
	}
}
