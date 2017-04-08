package edu.njust.four;
/**
 * 
 * @author sampson
 *	join方法 
 *	while(isAlive()){
 *		wait(0);
 *	}
 *	xx.join()
 *	等到xx终止时，调用自身的notifyAll()方法
 */
public class Join {
	static class Domino implements Runnable{
		private Thread thread;
		public Domino(Thread thread) {
			// TODO Auto-generated constructor stub
			this.thread=thread;
		}
		
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				thread.join();
			} catch (Exception e) {
				// TODO: handle exception
			}
			System.out.println(Thread.currentThread()+" terminate.");
		}
	}
	
	public static void main(String[] args) {
		Thread pre=Thread.currentThread();
		for (int i = 0; i < 10; i++) {
			Thread thread=new Thread(new Domino(pre),"Thread"+i);
			thread.start();
			pre=thread;
		}
	}
}
