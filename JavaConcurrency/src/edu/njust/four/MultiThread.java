package edu.njust.four;

import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;

public class MultiThread {
	public static void main(String[] args) {
		ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
		//java线程管理
		ThreadInfo[] threadInfos=threadMXBean.dumpAllThreads(false, false);
		//不需要获取同步的monitor和synchronized信息 仅获取线程和线程堆栈的信息
		for (ThreadInfo threadInfo:threadInfos) {
			System.out.println("{"+threadInfo.getThreadId()+"}"+threadInfo.getThreadName());
		}
	}
}
