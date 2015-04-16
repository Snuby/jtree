package com.jay.open.libs.common.reflect;

import com.jay.open.libs.common.Aware;
import com.jay.open.libs.common.Aware;
import com.jay.open.libs.common.validate.Assert;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-12
 * Time: 19:20
 * To change this template use File | Settings | File Templates.
 */
public class ClazzStackAware implements Aware {
    protected final Class clazz;
    protected final List<Class> clazzList;

    public ClazzStackAware(Class clazz){
        Assert.notNull(clazz);
        this.clazz = clazz;
        List<Class> stack = new ArrayList<Class>();

        Class cls = clazz;
        stack.add(cls);
        while(cls!=Object.class){
            stack.add(cls.getSuperclass());
            cls = cls.getSuperclass();
        }
        this.clazzList = Collections.unmodifiableList(stack);
    }

    public Class getBindClazz(){
        return this.clazz;
    }
}
