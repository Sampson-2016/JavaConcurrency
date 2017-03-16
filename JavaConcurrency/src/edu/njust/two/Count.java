package edu.njust.two;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
/**
 * 利用循环CAS实现原子操作
 * @author sampson
 * 1换个场景（有加有减）可能会出现ABA问题，解决方法，使用版本号
 * AtomicStampedReference atomicStampedRef = new AtomicStampedReference(100, 0);
 * 版本号是0，基础类型值是100（或者存对象引用）。compareAndSet(expectedRerference , newReference , expectStamp ,newStamp), 
 * CAS的时候，就会先检查stamp是否相符！不相等返回false;   
 * 2循环开销大，使用pause指令01可以延迟流水线执行指令02避免在退出循环时候因内存顺序冲突引起的cpu流水线被清空
 * 3只能保证一个共享变量的原子操作，解决方法，把多个变量放在一个对象里进行CAS操作
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
