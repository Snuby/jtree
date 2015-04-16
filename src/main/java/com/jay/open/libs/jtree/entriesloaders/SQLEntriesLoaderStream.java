package com.jay.open.libs.jtree.entriesloaders;

import com.jay.open.libs.common.Entry;
import com.jay.open.libs.common.io.EntriesLoaderStream;
import com.jay.open.libs.common.validate.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-10-31
 * Time: 13:45
 * To change this template use File | Settings | File Templates.
 */
public class SQLEntriesLoaderStream implements EntriesLoaderStream<String,String>{
    private Logger log = LoggerFactory.getLogger(SQLEntriesLoaderStream.class);

    private DataSource dataSource;
    private String currentSQL;
    private ResultSet resultSet;
    private Connection conn;

    public void setDataSource(DataSource dataSource){
        Assert.notNull(dataSource);
        this.dataSource = dataSource;
    }

    public void switchSQL(String sql){
        Assert.notNull(sql);
        this.currentSQL = sql;
    }

    @Override
    public boolean hasMoreElements() {
        try {
            if(this.resultSet==null) return false;
            return this.resultSet.next();  //To change body of implemented methods use File | Settings | File Templates.
        } catch (SQLException e) {
            log.error(e.getMessage(),e);  //To change body of catch statement use File | Settings | File Templates.
        }
        return false;
    }

    @Override
    public List<Entry<String, String>> nextElement() {
        try {
            if(this.resultSet==null)return null;
            ResultSetMetaData rsmd = this.resultSet.getMetaData();
            List<Entry<String,String>> data = new ArrayList<Entry<String, String>>();
            int cols = rsmd.getColumnCount();
            for(int i=1;i<=cols;i++){
                String name = rsmd.getColumnName(i);
                String value = resultSet.getString(i);
                Entry<String,String> entry = new Entry<String, String>();
                entry.setKey(name);
                entry.setValue(value);
                data.add(entry);
            }
            return data;
        } catch (SQLException e) {
            log.error(e.getMessage(),e);  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void openStream() {
        //To change body of implemented methods use File | Settings | File Templates.
        try{
            if(dataSource==null){
                throw new IllegalAccessException("DataSource can't be null!");
            }
            if(currentSQL==null){
                throw new IllegalAccessException("Please invoke switchSQL() first!");
            }
            this.conn = dataSource.getConnection();
            Statement stm = conn.createStatement();
            this.resultSet = stm.executeQuery(this.currentSQL);

        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }

    @Override
    public boolean isStreamOpened() {
        return dataSource!=null && currentSQL!=null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void closeStream() {
        //To change body of implemented methods use File | Settings | File Templates.
        try{
            if(this.conn!=null){
                this.conn.close();
                this.conn = null;
                this.resultSet = null;
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
    }
}
