package com.jay.open.libs.jtree.storage;

import com.alibaba.fastjson.JSONArray;
import com.jay.open.libs.jtree.deparsers.NodeDeparser;
import com.jay.open.libs.jtree.entriesstorers.JSONEntriesStorer;
import com.jay.open.libs.common.io.EntriesStorer;
import com.jay.open.libs.jtree.Node;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-13
 * Time: 15:56
 * To change this template use File | Settings | File Templates.
 */
public class JSONTreeStorage extends AbstractTreeStorage {

    protected NodeDeparser nodeDeparser;

    protected EntriesStorer entriesStorer;

    @Override
    protected NodeDeparser getNodeDeparser(Class<? extends Node> nodeClazz) {
        nodeDeparser = new NodeDeparser(nodeClazz);
        return nodeDeparser;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected EntriesStorer getEntriesStorer() {
        entriesStorer = new JSONEntriesStorer();
        return entriesStorer;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public JSONArray getJsonArray(){
        return (JSONArray)entriesStorer.flush();
    }

}
