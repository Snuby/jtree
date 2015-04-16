package com.jay.open.libs.jtree.deparsers;

import com.jay.open.libs.common.io.EntriesStorer;
import com.jay.open.libs.common.io.EntriesStorerStream;
import com.jay.open.libs.jtree.exceptions.NodeDeparserException;
import com.jay.open.libs.jtree.Node;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-13
 * Time: 11:16
 * To change this template use File | Settings | File Templates.
 */
public class NodeStreamDeparser<T extends Node> extends NodeDeparser<T> {
    private Logger log = LoggerFactory.getLogger(NodeStreamDeparser.class);

    private EntriesStorerStream<String,String> entriesStorerStream = null;

    public NodeStreamDeparser(Class<T> clazz) {
        super(clazz);
    }

    @Override
    public void bindEntriesStorer(EntriesStorer<String,String> entriesStorer){
        super.bindEntriesStorer(entriesStorer);
        if(!(entriesStorer instanceof EntriesStorerStream)){
            throw new NodeDeparserException("an instance of EntriesStorerStream is expected!");
        }
        this.entriesStorerStream = (EntriesStorerStream) entriesStorer;
    }

    @Override
    public void deparseAndStoreNodes(List<T> nodes){
        try{
            this.entriesStorerStream.openStream();
            super.deparseAndStoreNodes(nodes);
        }finally {
            this.entriesStorerStream.closeStream();
        }
    }
}
