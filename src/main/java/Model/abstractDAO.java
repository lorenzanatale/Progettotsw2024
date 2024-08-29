package Model;

import tswProj.ConnectionPool;
import tswProj.EmptyPoolException;

import java.sql.Connection;

public abstract class abstractDAO implements AutoCloseable {

	protected Connection connection;

	public abstractDAO() throws EmptyPoolException {
		this.connection = ConnectionPool.getConnection();
	}

	public void close() {
		if (connection != null) {
			ConnectionPool.releaseConnection(connection);
	    }
	}
}