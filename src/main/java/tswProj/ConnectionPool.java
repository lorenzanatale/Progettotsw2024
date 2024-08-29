package tswProj;

import java.sql.*;
import java.util.Enumeration;
import java.util.LinkedList;
import java.util.List;

public class ConnectionPool {

    private static final String JDBC_URL = "jdbc:mysql://localhost:3306/ecommercestrumenti"
            + "?useUnicode=true&useJDBCCompliantTimezoneShift=true"
            + "&useLegacyDatetimecode=false&serverTimezone=UTC";

    private static final String USERNAME = "root";
    private static final String PASSWORD = "root";
    private static List<Connection> freeDbConnections;

    public static void init(int poolSize) {
        freeDbConnections = new LinkedList<>();
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            for (int i = 0; i < poolSize; i++) {
                freeDbConnections.add(createDBConnection());
            }
        } catch (ClassNotFoundException e) {
            System.out.println("DB driver not found:"+ e.getMessage());
        } catch (SQLException e) {
            System.out.println("DB access error:"+ e.getMessage());
        }
        System.out.println("Created " + freeDbConnections.size() + " DB connections");
    }

    private static synchronized Connection createDBConnection() throws SQLException {
        return DriverManager.getConnection(JDBC_URL, USERNAME, PASSWORD);
    }

    public static synchronized Connection getConnection() throws EmptyPoolException {
        if (freeDbConnections.isEmpty()) {
            throw new EmptyPoolException("Connection pool vuota");
        }
        System.out.println("Getting connection from pool(" + (freeDbConnections.size() - 1) + ")");
        return freeDbConnections.remove(0);
    }

    public static synchronized void releaseConnection(Connection connection) {
        if (connection != null) {
            freeDbConnections.add(connection);
            System.out.println("Releasing connection from pool(" + freeDbConnections.size() + ")");
        }
    }

    public static void releaseResources() {
        for (Connection connection : freeDbConnections) {
            try {

                Enumeration<Driver> drivers = DriverManager.getDrivers();

                Driver driver = null;

                while(drivers.hasMoreElements()) {
                    try {
                        driver = drivers.nextElement();
                        DriverManager.deregisterDriver(driver);

                    } catch (SQLException ex) {
                    }
                }

                try {
                    Class<?> clazz = Class.forName("com.mysql.cj.jdbc.AbandonedConnectionCleanupThread");
                    java.lang.reflect.Method method = clazz.getMethod("checkedShutdown");
                    method.invoke(null);
                } catch (ClassNotFoundException | NoSuchMethodException | IllegalAccessException | java.lang.reflect.InvocationTargetException e) {
                    System.out.println("Non riesco a trovare la classe");
                }
                connection.close();
            } catch (SQLException ignored) {}
        }
    }
}