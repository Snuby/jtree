package com.jay.open.libs.jtree;

import com.jay.open.libs.common.annotations.FieldMapping;
import com.jay.open.libs.common.validate.Assert;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-10-22
 * Time: 19:20
 * To change this template use File | Settings | File Templates.
 */
public class DefaultNode implements Node{

    @FieldMapping
    protected long id;
    @FieldMapping
    protected long pid;

    private Map<Long,Node> childrenMap = new HashMap<Long, Node>();

    @Override
    public long getId() {
        return id;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setId(long id) {
        this.id = id;
    }

    @Override
    public long getPid() {
        return pid;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setPid(long pid) {
        this.pid = pid;
    }

    @Override
    final public Collection<Node> getChildrenList() {
        return Collections.unmodifiableCollection(childrenMap.values());  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    final public Map<Long, Node> getChildrenMap() {
        return Collections.unmodifiableMap(childrenMap);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    final public boolean isLeaf() {
        return this.childrenMap.isEmpty();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    final public void addChild(Node node) {
        //To change body of implemented methods use File | Settings | File Templates.
        Assert.notNull(node);
        this.childrenMap.put(node.getId(),node);
    }

    @Override
    final public void removeChild(Node node) {
        //To change body of implemented methods use File | Settings | File Templates.
        Assert.notNull(node);
        this.childrenMap.remove(node.getId());
    }

    @Override
    final public Node getChildNode(long childNodeId){
        return this.childrenMap.get(childNodeId);
    }

    @Override
    final public boolean containsChildNode(long childNodeId){
        return getChildNode(childNodeId)==null;
    }

    @Override
    public boolean logicEquals(Object target) {
        if(!(target instanceof Node)) return false;
        Node node = (Node) target;
        return this.id == node.getId() && this.pid == node.getPid();  //To change body of implemented methods use File | Settings | File Templates.
    }
}
