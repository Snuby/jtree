package com.jay.open.libs.jtree;

import com.jay.open.libs.common.LogicEqual;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-10-23
 * Time: 15:14
 * To change this template use File | Settings | File Templates.
 */
public interface Node extends LogicEqual,Serializable {

    /**
     * 所有的节点都应该是正数
     * @return
     */
    public long getId();

    /**
     * 如果是根节点，应该返回0
     * @return
     */
    public long getPid();

    public Collection<Node> getChildrenList();

    public Map<Long,Node> getChildrenMap();

    public boolean isLeaf();

    public void addChild(Node node);

    public void removeChild(Node node);

    public Node getChildNode(long childNodeId);

    public boolean containsChildNode(long childNodeId);

}
