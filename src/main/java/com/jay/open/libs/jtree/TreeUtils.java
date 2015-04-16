package com.jay.open.libs.jtree;

import com.jay.open.libs.jtree.exceptions.InvalidTreeException;
import com.jay.open.libs.common.validate.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-10-23
 * Time: 15:29
 * To change this template use File | Settings | File Templates.
 */
public class TreeUtils {

    /**
     * 检查subtree是否为parentTree的绝对子树(从根节点开始)
     * @param parentTree
     * @param subtree
     * @return
     */
    public static boolean isAbsoluteSubtree(Tree parentTree,Tree subtree){
        Assert.notNull(parentTree,subtree);

        Node parRoot = parentTree.getRoot();
        Node subRoot = subtree.getRoot();

        return isAbsoluteSubtree(parRoot, subRoot);
    }

    /**
     * 检查subtree是否为parentTree的子树
     * @param parentTree
     * @param subtree
     * @return
     */
    public static boolean isSubtree(Tree parentTree,Tree subtree){
        Assert.notNull(parentTree,subtree);

        Node subRoot = subtree.getRoot();
        Node parNode = parentTree.getOffspring(subRoot.getId());
        if(parNode == null){
            return false;
        }

        return isAbsoluteSubtree(parNode, subRoot);
    }

    private static boolean isAbsoluteSubtree(Node ptNode,Node stNode){
        if(!ptNode.logicEquals(stNode)){
            return false;
        }
        if(stNode.isLeaf())return true;
        if(ptNode.isLeaf())return false;

        Map<Long,Node> ptcNodes = ptNode.getChildrenMap();
        for(Node stcNode:stNode.getChildrenList()){
            if(!ptcNodes.containsKey(stcNode.getId())){
                return false;
            }
            Node ptcNode = ptcNodes.get(stcNode.getId());
            if(!isAbsoluteSubtree(ptcNode, stcNode))return false;
        }
        return true;
    }

    /**
     * 校验是否为一棵合法的树：
     * <br/>
     * 1）所有子节点的pid都在本棵树内
     * 2）一个子节点只能有一个父节点
     * @param targetTree
     * @return
     */
    public static void validateTree(Tree targetTree)throws InvalidTreeException{
        Assert.notNull(targetTree);

        Map<Long,Node> allNodesMap = targetTree.getAllNodesMap();
        Node targetRoot = targetTree.getRoot();

        List<Long> checkNodes = new ArrayList<Long>();
        checkNodes.add(targetRoot.getId());

        validateTree(allNodesMap,checkNodes,targetRoot);
    }

    private static void validateTree(Map<Long,Node> allNodesMap,List<Long> checkNodes,Node targetNode)throws InvalidTreeException{
        if(targetNode.getId()<0){
            throw new InvalidTreeException("id["+targetNode.getId()+"] should be positive(>0)!");
        }
        if(targetNode.isLeaf())return;

        for(Node node:targetNode.getChildrenList()){
            if(!allNodesMap.containsKey(node.getPid())){
                throw new InvalidTreeException(String.format("pid[%d] of node[%d] doesn't exsit",node.getPid(),node.getId()));
            }
            if(checkNodes.contains(node.getId())){
                throw new InvalidTreeException("id["+node.getId()+"] already exist");
            }
            checkNodes.add(node.getId());
            validateTree(allNodesMap, checkNodes, node);
        }
    }
}
