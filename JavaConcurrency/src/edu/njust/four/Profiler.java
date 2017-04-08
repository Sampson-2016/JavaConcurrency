package edu.njust.four;

import java.util.concurrent.TimeUnit;


public class Profiler {
	private static final ThreadLocal<Long> Time_ThreadLocal=new ThreadLocal<Long>(){
		protected Long initialValue(){
			return 	System.currentTimeMillis();
		}
	};
	
	public static final void begin(){
		Time_ThreadLocal.set(System.currentTimeMillis());
	}
	
	public static final long end(){
		return System.currentTimeMillis()-Time_ThreadLocal.get();
	}
	
	public static void main(String[] args) throws InterruptedException {
		Profiler.begin();
		System.out.println("get1: "+Time_ThreadLocal.get());
		TimeUnit.SECONDS.sleep(2);
		System.out.println("Cost1"+Profiler.end());
		Thread t1=new Thread(){
			public void run() {

				Profiler.begin();
				try {
					TimeUnit.SECONDS.sleep(1);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				System.out.println("Cost2"+Profiler.end());
				System.out.println("get2: "+Time_ThreadLocal.get());
				System.out.println("curr: "+System.currentTimeMillis());
				
			};
		};
		t1.start();
		TimeUnit.SECONDS.sleep(1);
		System.out.println("get3: "+Time_ThreadLocal.get());
		System.out.println("Cost3"+Profiler.end());
	}
	
}
