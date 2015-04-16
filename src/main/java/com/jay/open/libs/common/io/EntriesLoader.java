package com.jay.open.libs.common.io;

import com.jay.open.libs.common.Entry;
import com.jay.open.libs.common.Entry;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-12
 * Time: 16:19
 * To change this template use File | Settings | File Templates.
 */
public interface EntriesLoader<K,V> {

    boolean hasMoreElements();

    List<Entry<K,V>> nextElement();
}
