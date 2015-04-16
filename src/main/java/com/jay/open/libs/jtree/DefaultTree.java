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

    private Node root;

    @Override
    final public Node getRoot() {
        return root;  //To change body of implemented methods use File | Settings | File Templates.
    }

    final public void setRoot(Node root){
        this.root = root;
    }

    @Override
    public Node getOffspring(long offNodeId) {
        if(root.getId() == offNodeId) return root;
        return getOffspring(root,offNodeId);  //To change body of implemented methods use File | Settings | File Templates.
    }

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
    public List<Node> getAllNodesList() {
        return getOffspringNodes(root,true);
    }

    @Override
    public Map<Long, Node> getAllNodesMap() {
        Map<Long, Node> map = new HashMap<Long, Node>();
        map.put(root.getId(),root);
        getAllNodesMap(map,root);
        return Collections.unmodifiableMap(map);  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Node> getOffspringNodes(Node node,boolean includeSelf) {
        List<Node> offspring = new ArrayList<Node>();
        if(node==null){
            return offspring;
        }
        if(includeSelf){
            offspring.add(node);
        }
        getOffspringNodes(offspring,node);
        return Collections.unmodifiableList(offspring);  //To change body of implemented methods use File | Settings | File Templates.
    }

    private void getOffspringNodes(List<Node> offspring,Node node){
        if(node.isLeaf()){
            return;
        }
        offspring.addAll(node.getChildrenList());
        for(Node child:node.getChildrenList()){
            getOffspringNodes(offspring, child);
        }
    }


    private void getAllNodesMap(Map<Long, Node> map,Node node){
        Map<Long,Node> childMap = node.getChildrenMap();
        map.putAll(childMap);
        for(Long id:childMap.keySet()){
            getAllNodesMap(map,childMap.get(id));
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
