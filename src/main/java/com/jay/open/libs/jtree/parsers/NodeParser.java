package com.jay.open.libs.jtree.parsers;

import com.jay.open.libs.common.StringParser;
import com.jay.open.libs.common.Entry;
import com.jay.open.libs.common.reflect.FieldMappingFieldsAware;
import com.jay.open.libs.common.io.EntriesLoader;
import com.jay.open.libs.common.validate.Assert;
import com.jay.open.libs.jtree.Node;
import com.jay.open.libs.jtree.exceptions.ETreeException;
import com.jay.open.libs.jtree.exceptions.InvalidTreeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-12
 * Time: 16:17
 * To change this template use File | Settings | File Templates.
 */
public class NodeParser<T extends Node> {
    private Logger log = LoggerFactory.getLogger(NodeParser.class);
    protected EntriesLoader<String,String> entriesLoader;
    protected final FieldMappingFieldsAware fieldMappingFieldsAware;

    public NodeParser(Class<T> clazz){
        Assert.notNull(clazz);
        fieldMappingFieldsAware = new FieldMappingFieldsAware(clazz);
        log.info("NodeParser build ["+clazz+"] fieldMapping!");
    }

    public void bindEntriesLoader(EntriesLoader<String,String> entriesLoader){
        Assert.notNull(entriesLoader);
        this.entriesLoader = entriesLoader;
    }

    protected T parseMore(){
        try{
            if(!this.entriesLoader.hasMoreElements()){
                return null;
            }
            T obj = (T)fieldMappingFieldsAware.getBindClazz().newInstance();
            List<Entry<String,String>> entries = entriesLoader.nextElement();
            if(entries==null)return null;
            for(Entry<String,String> entry:entries){
                //Assert.notNull(entry.getKey(),entry.getValue());
                Field field = fieldMappingFieldsAware.getFieldByAlias(entry.getKey());
                if(field==null) {
                    //throw new UnformattedTreeException("Can't find field:"+entry.getKey()+"(See @FieldMapping)");
                    //忽略entries中未映射的项
                    continue;
                }
                try{
                    field.setAccessible(true);
                    field.set(obj, StringParser.parseString(entry.getValue(), field.getType()));
                }finally {
                    field.setAccessible(false);
                }
            }
            return obj;
        }catch (ETreeException e){
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }

    public T parseTreeRoot(){
        return parseTreeRoot(0);
    }

    public T parseTreeRoot(long rootId){
        T node;
        Map<Long,T> nodeMap = new HashMap<Long, T>();
        while((node=parseMore())!=null){
            nodeMap.put(node.getId(),node);
        }
        T root = null;
        for(T n:nodeMap.values()){
            if(n.getPid()==rootId){
                if(root!=null){
                    throw new InvalidTreeException(String.format("root[id=%d] alreay exists!",root.getId()));
                }
                root = n;
            }else{
                //忽略父节点不存在且不是根节点的节点，因为它无法参与构造一棵树
                if(nodeMap.get(n.getPid())==null){
                    log.warn(String.format("parent_node[%d] of node[%d] doesn't exist, it will be ignored!(if it is root,default is 0,pass rootId as parameter)",n.getPid(),n.getId()));
                    //throw new InvalidTreeException(String.format("parent_node[%d] of node[%d] doesn't exist",n.getPid(),n.getId()));
                }else{
                    nodeMap.get(n.getPid()).addChild(n);
                }
            }
        }
        return root;

    }
}
