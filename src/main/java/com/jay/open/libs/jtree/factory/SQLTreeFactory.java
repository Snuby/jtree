package com.jay.open.libs.jtree.factory;

import com.jay.open.libs.jtree.entriesloaders.SQLEntriesLoaderStream;
import com.jay.open.libs.jtree.parsers.NodeParser;
import com.jay.open.libs.common.io.EntriesLoader;
import com.jay.open.libs.common.validate.Assert;
import com.jay.open.libs.jtree.Node;
import com.jay.open.libs.jtree.Tree;
import com.jay.open.libs.jtree.parsers.NodeStreamParser;

import javax.sql.DataSource;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-14
 * Time: 11:18
 * To change this template use File | Settings | File Templates.
 */
public class SQLTreeFactory extends AbstractTreeFactory {
    private SQLEntriesLoaderStream loaderStream;
    private NodeStreamParser nodeStreamParser;

    public SQLTreeFactory(Class<? extends Tree> treeClazz, Class<? extends Node> nodeClazz) {
        super(treeClazz, nodeClazz);
    }

    public void setDataSource(DataSource dataSource) {
        Assert.notNull(dataSource);
        loaderStream.setDataSource(dataSource);
    }

    public void switchSQL(String sql){
        Assert.notNull(sql);
        this.loaderStream.switchSQL(sql);
    }

    @Override
    protected EntriesLoader getEntriesLoader() {
        loaderStream = new SQLEntriesLoaderStream();
        return loaderStream;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected NodeParser getNodeParser(Class<? extends Node> nodeClazz) {
        nodeStreamParser = new NodeStreamParser(nodeClazz);
        return nodeStreamParser;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
