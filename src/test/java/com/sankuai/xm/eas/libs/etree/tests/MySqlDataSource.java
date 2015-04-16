package com.sankuai.xm.eas.libs.etree.tests;

import javax.sql.DataSource;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.SQLFeatureNotSupportedException;
import java.util.logging.Logger;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-10-31
 * Time: 14:26
 * To change this template use File | Settings | File Templates.
 */
public class MySqlDataSource implements DataSource {

    private String dirver = "com.mysql.jdbc.Driver";
    private String url = "jdbc:mysql://192.168.2.174:3306/";
    private String database = "xmparty2";
    private String username = "q3boy";
    private String password = "q3girl";

    @Override
    public Connection getConnection() throws SQLException {
        try {
            Class.forName(dirver);
            return DriverManager.getConnection(url+database,username,password);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }


        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Connection getConnection(String username, String password) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public PrintWriter getLogWriter() throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setLogWriter(PrintWriter out) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void setLoginTimeout(int seconds) throws SQLException {
        //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public int getLoginTimeout() throws SQLException {
        return 0;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public Logger getParentLogger() throws SQLFeatureNotSupportedException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public <T> T unwrap(Class<T> iface) throws SQLException {
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean isWrapperFor(Class<?> iface) throws SQLException {
        return false;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
