package com.jay.open.libs.common.reflect;

import com.jay.open.libs.common.annotations.FieldMapping;
import com.jay.open.libs.common.annotations.FieldMapping;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.util.*;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-10-24
 * Time: 16:41
 * To change this template use File | Settings | File Templates.
 */
public class FieldMappingFieldsAware extends FieldsAware {

    private Map<String,String> annotatedMap = new HashMap<String, String>();
    private Map<String,String> aliasMap = new HashMap<String, String>();

    public FieldMappingFieldsAware(Class clazz){
        super(clazz);

        for(Field field:this.fieldMap.values()){
            if(field.isAnnotationPresent(FieldMapping.class)){
                FieldMapping mapping = field.getAnnotation(FieldMapping.class);
                String alias = "".equals(mapping.target())?field.getName():mapping.target();
                annotatedMap.put(alias,field.getName());
                aliasMap.put(field.getName(),alias);
            }
        }

    }

    public Field getFieldByAlias(String name){
        String fieldName = getFieldOfAlias(name);
        return super.getField(fieldName);
    }

    public Collection<String> getAnnotatedFields() {
        return this.annotatedMap.values();  //To change body of implemented methods use File | Settings | File Templates.
    }

    public Class<? extends Annotation> getAnnotationClass(){
        return FieldMapping.class;
    }

    public String getFieldOfAlias(String alias) {
        return this.annotatedMap.get(alias);  //To change body of implemented methods use File | Settings | File Templates.
    }

    public String getAliasOfField(String fieldName){
        return this.aliasMap.get(fieldName);
    }
}
