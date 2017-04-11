package edu.njust.five;

import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class BoundedQueue<T> {
	private Object[] items;
	private int addIndex,removeIndex,count;
	private Lock lock=new ReentrantLock();
	private Condition notFull=lock.newCondition();
	private Condition notEmpty=lock.newCondition();
	
	public BoundedQueue(int size) {
		items=new Object[size];
	}
	
	public void add(T t) throws InterruptedException{
		lock.lock();
		try {
			while(count==items.length){
				notFull.await();
			}
			items[addIndex]=t;
			if(++addIndex==items.length){
				addIndex=0;
			}
			count++;
			notEmpty.signalAll();
		} catch (Exception e) {
			// TODO: handle exception
		}finally{
			lock.unlock();
		}
	}
	
	public T remove() throws InterruptedException{
		lock.lock();
		try {
			while(count==0){
				notEmpty.await();
			}
			@SuppressWarnings("unchecked")
			T t = (T) items[removeIndex];
			if(++removeIndex==items.length){
				removeIndex=0;
			}
			count--;
			notFull.signalAll();
			return t;
			
			
		} finally{
			lock.unlock();
		}
	}
	static class AddThread extends Thread{
		private   BoundedQueue<Integer> b;
		private   int i;
		public AddThread(BoundedQueue<Integer>  b,int i) {
			this.b=b;
			this.i=i;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub

			try {
				b.add(i);
				System.out.println(i+" ++ 添加成功 " +Thread.currentThread());
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println(i+ "添加失败");
				e.printStackTrace();
			}
		}
	}
	static class RemoveThread extends Thread{
		private   BoundedQueue<Integer> b;
		
		public RemoveThread(BoundedQueue<Integer>  b) {
			this.b=b;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			
			try {
				int x=b.remove();
				System.out.println(x+" 移除成功  --");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				System.out.println( "移除失败");
				e.printStackTrace();
			}
		}
	}
	public static void main(String[] args) {
		 BoundedQueue<Integer>  bo=new BoundedQueue<Integer>(10);
		for (int i = 0; i < 5; i++) {
			AddThread addThread=new AddThread(bo,i);
			RemoveThread removeThread=new RemoveThread(bo);
			addThread.start();
			removeThread.start();
		}
		try {
			TimeUnit.SECONDS.sleep(5);
		} catch (Exception e) {
			// TODO: handle exception
		}
	}
}
