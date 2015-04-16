package com.jay.open.libs.jtree;

import java.io.Serializable;
import java.util.Collection;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-10-23
 * Time: 15:24
 * To change this template use File | Settings | File Templates.
 */
public interface Tree extends Serializable {

    public Node getRoot();

    public void setRoot(Node root);

    public Node getOffspring(long offNodeId);

    public boolean containsOffspring(long offNodeId);

    public boolean isOffspringOf(long child,long ancestor);

    public Collection<Node> getAllNodesList();

    public Map<Long,Node> getAllNodesMap();

    public Collection<Node> getOffspringNodes(Node node,boolean includeSelf);
}
