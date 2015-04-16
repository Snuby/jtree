package com.jay.open.libs.common.io;

import com.jay.open.libs.common.Entry;
import com.jay.open.libs.common.Entry;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-12
 * Time: 16:21
 * To change this template use File | Settings | File Templates.
 */
public interface EntriesStorer<K,V> {

    public void storeEntries(List<Entry<K,V>> entries);

    public Object flush();
}
