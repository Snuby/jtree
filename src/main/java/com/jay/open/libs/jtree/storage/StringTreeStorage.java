package com.jay.open.libs.jtree.storage;

import com.jay.open.libs.jtree.deparsers.NodeDeparser;
import com.jay.open.libs.common.io.EntriesStorer;
import com.jay.open.libs.jtree.Node;
import com.jay.open.libs.jtree.deparsers.NodeStreamDeparser;
import com.jay.open.libs.jtree.entriesstorers.TextEntriesStorerStream;

import java.io.ByteArrayOutputStream;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-13
 * Time: 19:12
 * To change this template use File | Settings | File Templates.
 */
public class StringTreeStorage extends AbstractTreeStorage {

    protected NodeDeparser nodeDeparser;
    protected EntriesStorer entriesStorer;

    protected ByteArrayOutputStream baos;

    @Override
    protected NodeDeparser getNodeDeparser(Class<? extends Node> nodeClazz) {
        nodeDeparser = new NodeStreamDeparser(nodeClazz);
        return nodeDeparser;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected EntriesStorer getEntriesStorer() {
        TextEntriesStorerStream tess = new TextEntriesStorerStream();
        baos = new ByteArrayOutputStream();
        tess.setOutputStream(baos);
        entriesStorer = tess;
        return entriesStorer;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getContent(){
        if(baos==null)return "";
        return baos.toString();
    }
}
