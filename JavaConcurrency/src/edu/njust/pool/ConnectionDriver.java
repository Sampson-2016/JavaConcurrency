package edu.njust.pool;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.sql.Connection;
import java.util.concurrent.TimeUnit;

public class ConnectionDriver {
	static class ConnectionHander implements InvocationHandler{
		@Override
		public Object invoke(Object proxy, Method method, Object[] args)
				throws Throwable {
			if(method.getName().equals("commit")){
				TimeUnit.SECONDS.sleep(2);
				System.out.println(Thread.currentThread().getName()+System.currentTimeMillis());
			}
			return null;
		}
	}
	public static final Connection createConnection(){
		return (Connection) Proxy.newProxyInstance(ConnectionDriver.class.getClassLoader(),
				new Class<?>[]{Connection.class}, new ConnectionHander());
	}
}
