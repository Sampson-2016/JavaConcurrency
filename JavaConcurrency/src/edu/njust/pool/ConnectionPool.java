package edu.njust.pool;

import java.sql.Connection;
import java.util.LinkedList;

public class ConnectionPool {
	private LinkedList<Connection> pool = new LinkedList<Connection>();

	public ConnectionPool(int ini) {
		// TODO Auto-generated constructor stub
		if (ini > 0) {
			for (int i = 0; i < ini; i++) {
				pool.add(ConnectionDriver.createConnection());
			}
		}
	}

	public void releaseConnection(Connection connection) {
		if (connection != null) {
			synchronized (pool) {
				pool.add(connection);
				pool.notifyAll();
			}
		}

	}

	public Connection fetchConnection(long mi) throws InterruptedException {
		synchronized (pool) {
			if (mi <= 0) {
				while (pool.isEmpty()) {
					pool.wait();
				}
				return pool.removeFirst();
			} else {
				long future = System.currentTimeMillis() + mi;
				long remain = mi;
				while (remain > 0 && pool.isEmpty()) {
					pool.wait(remain);
					remain = future - System.currentTimeMillis();
				}
				Connection result = null;
				if (!pool.isEmpty()) {
					result = pool.removeFirst();
				}
				return result;
			}
		}
	}
}
