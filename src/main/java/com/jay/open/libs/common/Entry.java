package com.jay.open.libs.common;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-10-24
 * Time: 13:49
 * To change this template use File | Settings | File Templates.
 */
public class Entry<K,V> {

    private K key;
    private V value;

    public void setKey(K key) {
        this.key = key;
    }

    public void setValue(V value) {
        this.value = value;
    }

    public K getKey(){
        return key;
    }

    public V getValue(){
        return value;
    }
}
