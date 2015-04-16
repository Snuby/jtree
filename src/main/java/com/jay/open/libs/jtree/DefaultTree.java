package com.jay.open.libs.jtree;

import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-10-24
 * Time: 11:15
 * To change this template use File | Settings | File Templates.
 */
public class DefaultTree implements Tree{

    protected Node root;

    @Override
    final public Node getRoot() {
        return root;  //To change body of implemented methods use File | Settings | File Templates.
    }

    final public void setRoot(Node root){
        this.root = root;
    }

    /**
     * 获取指定id的节点
     * @param offNodeId
     * @return
     */
    @Override
    public Node getOffspring(long offNodeId) {
        if(root.getId() == offNodeId) return root;
        return getOffspring(root,offNodeId);  //To change body of implemented methods use File | Settings | File Templates.
    }

    /**
     * 是否包含给定节点
     * @param offNodeId
     * @return
     */
    public boolean containsOffspring(long offNodeId){
        return getOffspring(offNodeId)!=null;
    }

    private Node getOffspring(Node node, long offNodeId){
        for(Node child:node.getChildrenList()){
            if(child.getId() == offNodeId){
                return child;
            }
            Node offspring = getOffspring(child,offNodeId);
            if(offspring!=null) return offspring;
        }
        return null;
    }

    /**
     * 判断给定child节点的祖先是否为ancestor节点
     * @param child
     * @param ancestor
     * @return
     */
    public boolean isOffspringOf(long child,long ancestor){
        Node childNode = getOffspring(child);
        if(childNode == null) return false;
        Node ancestorNode = getOffspring(ancestor);
        if(ancestorNode == null) return false;
        Node node = childNode;

        while(node.getPid()!=getRoot().getId()){
            if(node.getPid() == ancestorNode.getId()){
                return true;
            }
            node = getOffspring(node.getPid());
        }
        return false;
    }

    @Override
    public Collection<Node> getAllNodesList() {
        return getOffspringNodes(root,true);
    }

    @Override
    public Map<Long, Node> getAllNodesMap() {
        return getOffspringNodesMap(root,true);
    }

    /**
     * 获取从node以下的所有子孙节点，以list形式返回
     * @param node
     * @param includeSelf 是否包含自身
     * @return
     */
    @Override
    public Collection<Node> getOffspringNodes(Node node,boolean includeSelf) {
        List<Node> offspring = new ArrayList<Node>();
        if(node==null){
            return offspring;
        }
        if(includeSelf){
            offspring.add(node);
        }
        getOffspringNodes(offspring,node);
        return Collections.unmodifiableCollection(offspring);  //To change body of implemented methods use File | Settings | File Templates.
    }
    /**
     * 获取从node以下的所有子孙节点，以map形式返回
     * @param node
     * @param includeSelf 是否包含自身
     * @return
     */
    public Map<Long,Node> getOffspringNodesMap(Node node,boolean includeSelf){
        Map<Long, Node> map = new HashMap<Long, Node>();
        if(node==null){
            return map;
        }
        if(includeSelf){
            map.put(node.getId(),node);
        }
        getOffspringNodesMap(map,node);
        return Collections.unmodifiableMap(map);
    }

    private void getOffspringNodes(List<Node> offspring,Node node){
        Collection<Node> childList = node.getChildrenList();
        offspring.addAll(childList);
        for(Node child:childList){
            getOffspringNodes(offspring, child);
        }
    }

    private void getOffspringNodesMap(Map<Long, Node> map,Node node){
        Map<Long,Node> childMap = node.getChildrenMap();
        map.putAll(childMap);
        for(Long id:childMap.keySet()){
            getOffspringNodesMap(map,childMap.get(id));
        }
    }

    public String toString(){
        StringBuilder builder = new StringBuilder();
        builder.append("----------------------------\n");
        builder.append(root.getPid()+"\n");
        String tab = "";
        recursiveToString(tab,root,builder);
        builder.append("----------------------------\n");
        return builder.toString();
    }

    /**
     * 可以扩展，用于打印tree时显示节点的详细信息，默认只打印ID
     * @param node
     * @return
     */
    protected String printNode(Node node){
        return ""+node.getId();
    }

    private void recursiveToString(String tab,Node node,StringBuilder builder){
        builder.append(tab);
        builder.append("|-");
        builder.append(printNode(node));
        builder.append("\n");
        for(Node child:node.getChildrenList()){
            recursiveToString(tab+"  ",child,builder);
        }
    }
}
