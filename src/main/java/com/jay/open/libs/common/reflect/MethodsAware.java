package com.jay.open.libs.common.reflect;

import com.jay.open.libs.common.Aware;
import com.jay.open.libs.common.validate.Assert;

import java.lang.reflect.Method;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-12
 * Time: 19:18
 * To change this template use File | Settings | File Templates.
 */
public class MethodsAware extends ClazzStackAware {

    protected final Map<String,Method> methodMap;

    public MethodsAware(Class clazz){
        super(clazz);
        Map<String,Method> methodMap = new HashMap<String, Method>();
        for(int i = clazzList.size()-1; i>=0 ; i--){
            Class cls = clazzList.get(i);
            Method[] methods = cls.getDeclaredMethods();

            for(Method m:methods){
                methodMap.put(m.getName(),m);
            }
        }
        this.methodMap = Collections.unmodifiableMap(methodMap);
    }

    public Method getMethod(String name){
        return this.methodMap.get(name);
    }

    public Collection<String> getMethodNames(){
        return this.methodMap.keySet();
    }
}
