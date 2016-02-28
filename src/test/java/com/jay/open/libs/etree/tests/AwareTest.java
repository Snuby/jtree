package com.jay.open.libs.etree.tests;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-12
 * Time: 19:45
 * To change this template use File | Settings | File Templates.
 */
public class AwareTest {

    public static void main(String[] args){
        //GetterMethodsAware g = new GetterMethodsAware(DefaultNode.class);
        //SetterMethodsAware s = new SetterMethodsAware(DefaultNode.class);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        baos.write(95);
        try {
            baos.close();
        } catch (IOException e) {
            e.printStackTrace();  //To change body of catch statement use File | Settings | File Templates.
        }
        System.out.println(baos.toString());
    }
}
