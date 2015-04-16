package com.jay.open.libs.common;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-13
 * Time: 18:33
 * To change this template use File | Settings | File Templates.
 */
public class TimeCounter {

    private long createTime = 0;

    public TimeCounter(){
        reset();
    }

    public void reset(){
        createTime = System.currentTimeMillis();
    }

    public long elapse(){
        return System.currentTimeMillis() - createTime;
    }

    public void showElapse(){
        System.out.println("---elapse:"+elapse()+"ms---");
    }
}
