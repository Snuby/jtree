package com.jay.open.libs.jtree.enums;

import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-9-4
 * Time: 11:49
 * To change this template use File | Settings | File Templates.
 */
public enum CHECKSTATE {
    CHECKED("chk"),//选中
    UNCHECKED("unchk"),//未选中
    HALFCHECKED("halfchk"),//半选中
    UNKNOWN("unknown"); //未知

    private String value;

    private static final Map<String,CHECKSTATE> map = new HashMap<String, CHECKSTATE>();
    static {
        for(CHECKSTATE checkstate:values()){
            map.put(checkstate.value,checkstate);
        }
    }

    private CHECKSTATE(String value){
        this.value = value;
    }
    public String toString(){
        return this.value;
    }

    public static CHECKSTATE parseString(String state){
        if(state==null)return UNKNOWN;
        state = state.toLowerCase();
        if(map.containsKey(state))return map.get(state);
        return UNKNOWN;
    }
}
