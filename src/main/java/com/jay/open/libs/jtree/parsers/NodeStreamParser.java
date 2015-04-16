package com.jay.open.libs.jtree.parsers;

import com.jay.open.libs.common.io.EntriesLoader;
import com.jay.open.libs.common.io.EntriesLoaderStream;
import com.jay.open.libs.jtree.Node;
import com.jay.open.libs.jtree.exceptions.NodeParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-10-24
 * Time: 14:42
 * To change this template use File | Settings | File Templates.
 */
public class NodeStreamParser<T extends Node> extends NodeParser<T>{
    private Logger log = LoggerFactory.getLogger(NodeStreamParser.class);

    private EntriesLoaderStream<String,String> entriesLoaderStream;

    public NodeStreamParser(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public void bindEntriesLoader(EntriesLoader<String,String> entriesLoader){
        super.bindEntriesLoader(entriesLoader);
        if(!(entriesLoader instanceof EntriesLoaderStream)){
            throw new NodeParserException("an instance of EntriesLoaderStream is expected!");
        }
        this.entriesLoaderStream = (EntriesLoaderStream)entriesLoader;
    }

    @Override
    public T parseTreeRoot(long rootId){
        T root = null;
        try{
            this.entriesLoaderStream.openStream();
            root = super.parseTreeRoot(rootId);
        }finally {
            this.entriesLoaderStream.closeStream();
        }
        return root;
    }
}
