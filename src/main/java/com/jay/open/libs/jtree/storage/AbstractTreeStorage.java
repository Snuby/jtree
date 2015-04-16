package com.jay.open.libs.jtree.storage;

import com.jay.open.libs.common.io.EntriesStorer;
import com.jay.open.libs.jtree.Tree;
import com.jay.open.libs.jtree.deparsers.NodeDeparser;
import com.jay.open.libs.common.validate.Assert;
import com.jay.open.libs.jtree.Node;

import java.util.Collection;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-13
 * Time: 15:18
 * To change this template use File | Settings | File Templates.
 */
public abstract class AbstractTreeStorage implements TreeStorage{

    protected abstract NodeDeparser getNodeDeparser(Class<? extends Node> nodeClazz);

    protected abstract EntriesStorer getEntriesStorer();

    @Override
    public void storeTree(Tree tree, Class<? extends Node> nodeClazz) {
        //To change body of implemented methods use File | Settings | File Templates.
        Assert.notNull(tree);
        Assert.notNull(nodeClazz);

        NodeDeparser nodeDeparser = getNodeDeparser(nodeClazz);
        EntriesStorer<String,String> entriesStorer = getEntriesStorer();

        Assert.notNull(nodeDeparser);
        Assert.notNull(entriesStorer);
        nodeDeparser.bindEntriesStorer(entriesStorer);

        Collection<Node> nodes = tree.getAllNodesList();
        nodeDeparser.deparseAndStoreNodes(nodes);
    }


}
