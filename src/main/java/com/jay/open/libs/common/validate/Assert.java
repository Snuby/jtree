package com.jay.open.libs.common.validate;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-10-22
 * Time: 19:49
 * To change this template use File | Settings | File Templates.
 */
public class Assert {

    public static void notNull(Object obj){
        if(obj==null) throw new IllegalStateException("Target is null!");
    }

    public static void notNull(Object... objs){
        for(int i=0;i<objs.length;i++){
            if(objs[i]==null) throw new IllegalStateException(String.format("Target[%d] is null!",i));
        }
    }
}
