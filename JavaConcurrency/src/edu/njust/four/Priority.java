package edu.njust.four;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * 
 * @author sampson
 *	程序正确性不能依赖优先级，操作系统可以完全不用理会java线程对于优先级的设定
 *输出结果
 *Job Priority: 1 Count: 1423919
Job Priority: 1 Count: 1425209
Job Priority: 1 Count: 1426748
Job Priority: 1 Count: 1426702
Job Priority: 1 Count: 1425914
Job Priority: 10 Count: 5495775
Job Priority: 10 Count: 5508343
Job Priority: 10 Count: 5509673
Job Priority: 10 Count: 5492232
Job Priority: 10 Count: 5502381

 */
public class Priority {
	private static volatile boolean notStart=true;
	private static volatile boolean notEnd =true;
	
	public static void main(String[] args) throws InterruptedException {
		List <Job> jobs=new ArrayList<Job>();
		for (int i = 0; i < 10; i++) {
			int pri=i<5?Thread.MIN_PRIORITY:Thread.MAX_PRIORITY;
			Job job=new Job(pri);
			jobs.add(job);
			Thread thread=new Thread(job, "Thread:"+i);
			thread.setPriority(pri);
			thread.start();
		}
		notStart=false;
		TimeUnit.SECONDS.sleep(10);
		notEnd =false;
		
		for (Job job : jobs) {
			System.out.println("Job Priority: "+job.priority+" Count: "+job.jobCount);
		}
	}
	
	
	
	
	static class Job implements Runnable{
		private int priority;
		private long jobCount;
		public Job(int priority) {
			// TODO Auto-generated constructor stub
			this.priority=priority;
		}
		@Override
		public void run() {
			// TODO Auto-generated method stub
			while (notStart) {
				Thread.yield();
			}
			while (notEnd) {
				Thread.yield();
				jobCount++;
			}
		}
	}
}

