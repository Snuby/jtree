package com.jay.open.libs.jtree.entriesloaders;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jay.open.libs.common.Entry;
import com.jay.open.libs.common.io.EntriesLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-12
 * Time: 17:02
 * To change this template use File | Settings | File Templates.
 */
public class JSONEntriesLoader implements EntriesLoader<String,String> {

    private JSONArray json;
    private int index;

    public JSONArray getJsonArray() {
        return json;
    }

    public void setJsonArray(JSONArray json) {
        this.json = json;
        this.index = 0;
    }

    @Override
    public boolean hasMoreElements() {
        if(this.json==null){
            return false;
        }
        return index<this.json.size();  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Entry<String, String>> nextElement() {
        if(this.json==null){
            return null;
        }
        if(index>=this.json.size()) {
            return null;
        }
        JSONObject obj = json.getJSONObject(index++);
        List<Entry<String,String>> list = new ArrayList<Entry<String, String>>();
        for(String key:obj.keySet()){
            Entry<String,String> entry = new Entry<String, String>();
            entry.setKey(key);
            entry.setValue(obj.getString(key));
            list.add(entry);
        }
        return list;  //To change body of implemented methods use File | Settings | File Templates.
    }

}
