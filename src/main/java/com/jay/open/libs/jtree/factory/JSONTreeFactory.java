package com.jay.open.libs.jtree.factory;

import com.alibaba.fastjson.JSONArray;
import com.jay.open.libs.jtree.parsers.NodeParser;
import com.jay.open.libs.common.io.EntriesLoader;
import com.jay.open.libs.jtree.Node;
import com.jay.open.libs.jtree.Tree;
import com.jay.open.libs.jtree.entriesloaders.JSONEntriesLoader;
import com.jay.open.libs.jtree.exceptions.TreeFactoryException;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-12
 * Time: 17:00
 * To change this template use File | Settings | File Templates.
 */
public class JSONTreeFactory extends AbstractTreeFactory {

    protected NodeParser nodeParser;
    protected JSONEntriesLoader jsonEntriesLoader;

    public JSONTreeFactory(Class<? extends Tree> treeClazz, Class<? extends Node> nodeClazz) {
        super(treeClazz, nodeClazz);
    }

    public void setJsonArray(JSONArray jsonArray) {
        this.jsonArray = jsonArray;
    }

    public JSONArray jsonArray;

    @Override
    protected EntriesLoader getEntriesLoader() {
        if(this.jsonArray==null){
            throw new TreeFactoryException("jsonArray is null, please invoke setJsonArray first");
        }
        jsonEntriesLoader = new JSONEntriesLoader();
        jsonEntriesLoader.setJsonArray(jsonArray);
        return jsonEntriesLoader;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected NodeParser getNodeParser(Class<? extends Node> nodeClazz) {
        nodeParser = new NodeParser(nodeClazz);
        return nodeParser;  //To change body of implemented methods use File | Settings | File Templates.
    }
 }
