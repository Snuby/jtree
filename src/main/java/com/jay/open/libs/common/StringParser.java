package com.jay.open.libs.common;

import com.jay.open.libs.common.validate.Assert;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-10-30
 * Time: 17:33
 * To change this template use File | Settings | File Templates.
 */
public class StringParser {

    private static Map<Class,Method> classMethodMap = new HashMap<Class, Method>();
    static {
        try{
            classMethodMap.put(Long.class,Long.class.getMethod("valueOf", String.class));
            classMethodMap.put(long.class,Long.class.getMethod("valueOf", String.class));

            classMethodMap.put(Integer.class,Integer.class.getMethod("valueOf", String.class));
            classMethodMap.put(int.class,Integer.class.getMethod("valueOf", String.class));
            classMethodMap.put(Byte.class,Byte.class.getMethod("valueOf", String.class));
            classMethodMap.put(byte.class,Byte.class.getMethod("valueOf", String.class));

            classMethodMap.put(Double.class,Double.class.getMethod("valueOf", String.class));
            classMethodMap.put(double.class,Double.class.getMethod("valueOf", String.class));
            classMethodMap.put(Float.class,Float.class.getMethod("valueOf", String.class));
            classMethodMap.put(float.class,Float.class.getMethod("valueOf", String.class));

            classMethodMap.put(Boolean.class,Boolean.class.getMethod("valueOf", String.class));
            classMethodMap.put(boolean.class,Boolean.class.getMethod("valueOf", String.class));

            classMethodMap.put(String.class,String.class.getMethod("valueOf", Object.class));

            classMethodMap.put(Character.class,Character.class.getMethod("valueOf", char.class));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public static <T> T parseString(String value,Class<T> clazz){
        Assert.notNull(value,clazz);
        Method valueOf;
        try{
            valueOf = classMethodMap.get(clazz);
            if(valueOf == null){
                throw new UnsupportedOperationException("No class found!");
            }
            T obj = (T)valueOf.invoke(null,value);
            return obj;
        }catch (Exception e){
            throw new IllegalStateException(e);
        }
    }

}
