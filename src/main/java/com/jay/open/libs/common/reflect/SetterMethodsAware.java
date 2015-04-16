package com.jay.open.libs.common.reflect;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-12
 * Time: 19:49
 * To change this template use File | Settings | File Templates.
 */
public class SetterMethodsAware extends MethodsAware{

    protected final Map<String,Method> setterMap;

    public SetterMethodsAware(Class clazz) {
        super(clazz);

        Map<String,Method> setterMap = new HashMap<String, Method>();
        for(Method m:this.methodMap.values()){
            String methodName = m.getName();
            if(methodName.startsWith("set")){
                String fieldName = methodName.substring(3);
                Character ch = Character.toLowerCase(fieldName.charAt(0));
                fieldName = ch+fieldName.substring(1);
                setterMap.put(fieldName,m);
            }
        }
        this.setterMap = Collections.unmodifiableMap(setterMap);
    }

    public Method getMethodByFieldName(String fieldName){
        return this.setterMap.get(fieldName);
    }

    public Collection<String> getFieldNames(){
        return this.setterMap.keySet();
    }
}
