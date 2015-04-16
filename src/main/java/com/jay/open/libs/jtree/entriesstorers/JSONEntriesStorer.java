package com.jay.open.libs.jtree.entriesstorers;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jay.open.libs.common.Entry;
import com.jay.open.libs.common.io.EntriesStorer;

import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-13
 * Time: 15:40
 * To change this template use File | Settings | File Templates.
 */
public class JSONEntriesStorer implements EntriesStorer<String,String> {

    private JSONArray json = new JSONArray();

    public JSONArray getJsonArray(){
        return json;
    }

    @Override
    public void storeEntries(List<Entry<String, String>> entries) {
        //To change body of implemented methods use File | Settings | File Templates.
        JSONObject obj = new JSONObject();
        for(Entry<String,String> entry:entries){
            obj.put(entry.getKey(),entry.getValue());
        }
        json.add(obj);
    }

    @Override
    public Object flush() {
        //To change body of implemented methods use File | Settings | File Templates.
        JSONArray copy = new JSONArray(json);
        json = new JSONArray();
        return copy;
    }

    public static void main(String[] args){
        List<Entry<String,String>> list = new ArrayList<Entry<String, String>>();
        for(int i=0;i<2;i++){
            Entry<String,String> entry = new Entry<String, String>();
            entry.setKey("k"+i);
            entry.setValue("v" + i);
            list.add(entry);
        }
        System.out.println(((JSONArray)JSON.toJSON(list)).toJSONString());

        JSONEntriesStorer storer = new JSONEntriesStorer();
        storer.storeEntries(list);
        System.out.println(storer.getJsonArray().toJSONString());
    }

}
