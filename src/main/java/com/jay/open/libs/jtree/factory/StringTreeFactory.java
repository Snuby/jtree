package com.jay.open.libs.jtree.factory;

import com.jay.open.libs.jtree.Tree;
import com.jay.open.libs.jtree.exceptions.TreeFactoryException;
import com.jay.open.libs.jtree.parsers.NodeParser;
import com.jay.open.libs.jtree.parsers.NodeStreamParser;
import com.jay.open.libs.common.io.EntriesLoader;
import com.jay.open.libs.jtree.Node;
import com.jay.open.libs.jtree.entriesloaders.TextEntriesLoaderStream;

import java.io.ByteArrayInputStream;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-12
 * Time: 16:38
 * To change this template use File | Settings | File Templates.
 */
public class StringTreeFactory extends AbstractTreeFactory{
    private String content;
    private NodeStreamParser nodeStreamParser;
    private TextEntriesLoaderStream loaderStream;

    public StringTreeFactory(Class<? extends Tree> treeClazz, Class<? extends Node> nodeClazz) {
        super(treeClazz, nodeClazz);
    }

    public void setContent(String content){
        this.content = content;
    }
    @Override
    protected EntriesLoader getEntriesLoader() {
        if(this.content==null){
            throw new TreeFactoryException("content is null, please invoke setContent first");
        }
        ByteArrayInputStream bais = new ByteArrayInputStream(content.getBytes());
        loaderStream = new TextEntriesLoaderStream();
        loaderStream.setInputStream(bais);
        return loaderStream;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected NodeParser getNodeParser(Class<? extends Node> nodeClazz) {
        nodeStreamParser = new NodeStreamParser(nodeClazz);
        return nodeStreamParser;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
