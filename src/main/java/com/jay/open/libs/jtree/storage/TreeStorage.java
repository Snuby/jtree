package com.jay.open.libs.jtree.storage;

import com.jay.open.libs.jtree.Node;
import com.jay.open.libs.jtree.Tree;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-13
 * Time: 11:03
 * To change this template use File | Settings | File Templates.
 */
public interface TreeStorage {

    public void storeTree(Tree tree,Class<? extends Node> nodeClazz);
}
