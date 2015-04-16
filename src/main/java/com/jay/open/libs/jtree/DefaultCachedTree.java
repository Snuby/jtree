package com.jay.open.libs.jtree;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 15-4-16
 * Time: 23:22
 * To change this template use File | Settings | File Templates.
 */
public class DefaultCachedTree extends DefaultTree {

    private Map<Long,Node> allNodesMap = new HashMap<Long, Node>();

    /**
     * 添加新节点后需要调用一次refresh，更新节点缓存
     */
    public void refreshCache(){
        allNodesMap = getOffspringNodesMap(root,true);
    }

    @Override
    public Node getOffspring(long orgId){
        return allNodesMap.get(orgId);
    }

    public Map<Long,Node> getAllNodesMap(){
        return this.allNodesMap;
    }

    public boolean containsOffspring(long offNodeId){
        return allNodesMap.containsKey(offNodeId);
    }

    @Override
    public Collection<Node> getAllNodesList() {
        return allNodesMap.values();
    }
}
