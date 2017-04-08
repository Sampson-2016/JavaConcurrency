package edu.njust.four;

import java.util.concurrent.TimeUnit;



public class TestLocal {
	private ThreadLocal<Integer> N=new ThreadLocal<Integer>(){
		protected Integer initialValue() {
			return 10;
		};
	};
	public Integer getN(){
		return N.get();
	}
	public void addN(int i){
		N.set(N.get()+i);
	}
	
	public static void main(String[] args) {
		TestLocal testLocal=new TestLocal();
		Thread t1=new Thread(new Run(testLocal,10),"线程1");
		Thread t2=new Thread(new Run(testLocal,100),"线程2");
		t1.start();
		t2.start();
	}
}
class Run implements Runnable{
	TestLocal t;
	int add;
	public Run(TestLocal t,int add) {
		this.t=t;
		this.add=add;
		// TODO Auto-generated constructor stub
	}
	@Override
	public void run() {
		int i=0;
		// TODO Auto-generated method stub
		while(i<5){
			System.out.println(Thread.currentThread().getName()+" n: "+t.getN());
			i++;
			t.addN(add);
			try {
				TimeUnit.SECONDS.sleep(1);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}

