package edu.njust.four;
/**
 * 
 * @author sampson
 *	主线程结束，他的守护线程也马上结束，所以构建Daemon时，
 *   不能依靠finally块中的内容来确保执行关闭或清理资源的逻辑
 */
public class Daemon {
	public static void main(String[] args) throws InterruptedException {
		Thread thread=new Thread(new DaemonDemo());
		thread.setDaemon(true);
		thread.start();
		Thread.sleep(200);
	}
	
	
	static class DaemonDemo implements Runnable{
		@Override
		public void run() {
			// TODO Auto-generated method stub
			try {
				System.out.println("begin");
				Thread.sleep(5000);
				System.out.println("end");
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}finally{
				System.out.println("run finally");
			}
			
		}
	}
}
