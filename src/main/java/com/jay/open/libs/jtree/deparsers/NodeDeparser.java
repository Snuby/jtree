package com.jay.open.libs.jtree.deparsers;

import com.jay.open.libs.common.Entry;
import com.jay.open.libs.common.io.EntriesStorer;
import com.jay.open.libs.common.reflect.FieldMappingFieldsAware;
import com.jay.open.libs.common.reflect.GetterMethodsAware;
import com.jay.open.libs.common.validate.Assert;
import com.jay.open.libs.jtree.Node;
import com.jay.open.libs.jtree.exceptions.ETreeException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-13
 * Time: 11:05
 * To change this template use File | Settings | File Templates.
 */
public class NodeDeparser<T extends Node>{
    private Logger log = LoggerFactory.getLogger(NodeDeparser.class);
    protected EntriesStorer entriesStorer = null;
    protected final FieldMappingFieldsAware fieldMappingFieldsAware;
    protected final GetterMethodsAware getterMethodsAware;
    protected List<T> nodes = null;

    public NodeDeparser(Class<T> clazz){
        Assert.notNull(clazz);
        fieldMappingFieldsAware = new FieldMappingFieldsAware(clazz);
        getterMethodsAware = new GetterMethodsAware(clazz);
        log.info("NodeDeparser build ["+clazz+"] fieldMapping!");
    }

    public void bindEntriesStorer(EntriesStorer<String,String> entriesStorer){
        Assert.notNull(entriesStorer);
        this.entriesStorer = entriesStorer;
    }

    protected List<Entry<String,String>> deparse(T node){
        try{
            List<Entry<String,String>> entries = new ArrayList<Entry<String, String>>();
            for(String fieldName:fieldMappingFieldsAware.getAnnotatedFields()){
                Method m = getterMethodsAware.getMethodByFieldName(fieldName);
                String value = m.invoke(node).toString();
                String key = fieldMappingFieldsAware.getAliasOfField(fieldName);
                Entry<String,String> entry = new Entry<String, String>();
                entry.setKey(key);
                entry.setValue(value);
                entries.add(entry);
            }
            return entries;
        }catch (ETreeException e){
            throw e;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }

    public void deparseAndStoreNodes(List<T> nodes){
        Assert.notNull(nodes);
        this.nodes = nodes;
        for(T node:this.nodes){
            List<Entry<String,String>> entries = deparse(node);
            this.entriesStorer.storeEntries(entries);
        }
    }
}
