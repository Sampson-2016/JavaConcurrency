package edu.njust.TwinsLock;

import java.util.concurrent.TimeUnit;

public class test {
	public static void main(String[] args) {
		final Thread t1=new Thread (new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				while (!Thread.currentThread().isInterrupted()) {
					System.out.println("----");
				}
				
				if(Thread.interrupted()){
					System.out.println("中断");
				}
			
				if(Thread.interrupted()){
					System.out.println("中断");
				}	
					
				
				if(Thread.interrupted()){
					System.out.println("中断");
				}
			}
		})  ;
		t1.start();
		
		final Thread t2=new Thread(new Runnable() {
			
			@Override
			public void run() {
				// TODO Auto-generated method stub
				try {
					TimeUnit.SECONDS.sleep(2);
					t1.interrupt();
				} catch (Exception e) {
					// TODO: handle exception
				}
			}
		});
	
		t2.start();
		
		
		try {
			System.out.println("000");
			t1.join();
		} catch (InterruptedException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
			System.out.println("hh");
		}

		
	
		

	}


}
