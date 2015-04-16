package com.jay.open.libs.common.reflect;

import com.jay.open.libs.common.Aware;
import com.jay.open.libs.common.validate.Assert;

import java.lang.reflect.Field;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-10-24
 * Time: 14:50
 * To change this template use File | Settings | File Templates.
 */
public class FieldsAware extends ClazzStackAware {
    protected final Map<String,Field> fieldMap;

    public FieldsAware(Class clazz){
        super(clazz);
        Map<String,Field> fieldMap = new HashMap<String, Field>();

        for(int i = clazzList.size()-1; i>=0 ; i--){
            Class cls = clazzList.get(i);
            Field[] fields = cls.getDeclaredFields();
            for(Field field:fields){
                fieldMap.put(field.getName(),field);
            }
        }
        this.fieldMap = Collections.unmodifiableMap(fieldMap);
    }

    public Field getField(String name) {
        return this.fieldMap.get(name);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Collection<String> getFieldNames() {
        return this.fieldMap.keySet();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public boolean containsField(String name){
        return this.fieldMap.containsKey(name);
    }
}
