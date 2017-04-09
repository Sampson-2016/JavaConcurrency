package edu.njust.pool;

import java.sql.Connection;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.atomic.AtomicInteger;

public class ConnectionPoolTest {
	static ConnectionPool pool=new ConnectionPool(10);
	
	static CountDownLatch start=new CountDownLatch(1);
	
	static CountDownLatch end;
	
	public static void main(String[] args) throws Exception{
		int threadCount=10;
		end=new CountDownLatch(threadCount);
		int count=20;
		AtomicInteger got=new AtomicInteger();
		AtomicInteger notGot=new AtomicInteger();
		for (int i = 0; i < threadCount; i++) {
			Thread thread =new Thread(new ConnectionRunner(count, got, notGot),"Thread ConnectionRunnerThread"+i);
			thread.start();
		}
		start.countDown();
		end.await();
		System.out.println("total invoke: "+(threadCount*count));
		System.out.println("got connection: "+got);
		System.out.println("not get connection: "+notGot);
	}
	
	static class ConnectionRunner implements Runnable{
		int count;
		AtomicInteger got;
		AtomicInteger notGot;
		
	    public 	ConnectionRunner(int count,AtomicInteger got,AtomicInteger notGot){
			this.count=count;
			this.got=got;
			this.notGot=notGot;
		}
	    
	    @Override
	    public void run() {
	    	try {
				start.wait();
			} catch (Exception e) {
				// TODO: handle exception
			}
	    	while(count>0){
	    		Connection connection;
	    		try {
	    			connection=pool.fetchConnection(1000);
					if(connection!=null){
						try {
							connection.createStatement();
							connection.commit();
						} catch (Exception e) {
							
						}finally{
							pool.releaseConnection(connection);
							got.incrementAndGet();
						}
					}else {
						notGot.incrementAndGet();
					}
				} catch (InterruptedException e) {
					
					e.printStackTrace();
				}finally{
					count--;
				}
	    		
	    	}
	    	end.countDown();
	    }
	    
	}
}
