package edu.njust.two;

import java.util.HashMap;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
/**
 * 
 * @author sampson
 *我们使用了一个非线程安全的HashMap作为缓存的时候然后使用读写锁来保证线程安全。
 *Cache使用读写锁提升读操作的并发性，也保证每次写操作对读操作的及时可见性，
 *同时简化了编程方式。
 */


public class Cache {
	static HashMap<String, Object> map= new HashMap<String, Object>();
	static ReentrantReadWriteLock rwlLock=new ReentrantReadWriteLock();
	static Lock rLock=rwlLock.readLock();
	static Lock wLock=rwlLock.writeLock();
	
	public static final Object get(String key){
		rLock.lock();
		try {
			return map.get(key);
		} finally{
			rLock.unlock();
		}
	}
	
	public static final Object put(String key,Object object){
		wLock.lock();
		try {
			return map.put(key, object);
		}finally{
			wLock.unlock();
		}
		
	}
	
	public static final void clear() {
		wLock.lock();
		try {
			map.clear();
		} finally{
			wLock.unlock();
		}
	}
	
}
