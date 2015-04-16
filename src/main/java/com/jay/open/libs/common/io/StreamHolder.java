package com.jay.open.libs.common.io;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-10-31
 * Time: 11:35
 * To change this template use File | Settings | File Templates.
 */
public interface StreamHolder {

    public void openStream();

    public boolean isStreamOpened();

    public void closeStream();
}
