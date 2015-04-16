package com.jay.open.libs.jtree.entriesloaders;

import com.jay.open.libs.common.Entry;
import com.jay.open.libs.common.io.EntriesLoaderStream;
import com.jay.open.libs.common.io.TextResourceStream;
import com.jay.open.libs.common.validate.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-10-30
 * Time: 9:42
 * To change this template use File | Settings | File Templates.
 */
public class TextEntriesLoaderStream implements EntriesLoaderStream<String,String>,TextResourceStream {
    private Logger log = LoggerFactory.getLogger(TextEntriesLoaderStream.class);
    private InputStream in;
    private BufferedReader reader;

    private String cacheLine = null;

    @Override
    public String readLine() {
        try {
            if(!isStreamOpened())return null;
            return reader.readLine();  //To change body of implemented methods use File | Settings | File Templates.
        } catch (IOException e) {
            log.error(e.getMessage(), e);  //To change body of catch statement use File | Settings | File Templates.
        }
        return null;
    }

    @Override
    public InputStream getInputStream() {
        return in;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setInputStream(InputStream in){
        Assert.notNull(in);
        this.in = in;
    }

    @Override
    public void closeStream() {
        //To change body of implemented methods use File | Settings | File Templates.
        try {
            if(reader!=null)reader.close();
        } catch (IOException e) {
            log.error(e.getMessage(), e);  //To change body of catch statement use File | Settings | File Templates.
        } finally {
            reader = null;
            try {
                if(in!=null)in.close();
            }catch (Exception e){
                log.error(e.getMessage(), e);
            }finally {
                in = null;
            }
        }
    }

    @Override
    public void openStream() {
        //To change body of implemented methods use File | Settings | File Templates.
        try{
            if(getInputStream()==null){
                throw new IllegalAccessException("Please invoke setInputStream first!");
            }
            InputStreamReader isr = new InputStreamReader(getInputStream());
            reader = new BufferedReader(isr);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

    @Override
    public boolean isStreamOpened() {
        return getInputStream()!=null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public boolean hasMoreElements() {
        cacheLine = readLine();
        return cacheLine != null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public List<Entry<String, String>> nextElement() {
        try{
            if(cacheLine==null) return null;
            String[] pairs = cacheLine.split("\\s+");
            List<Entry<String, String>> entries = new ArrayList<Entry<String, String>>();
            for(String pair:pairs){
                String[] tokens = pair.split(":");
                if(tokens==null||tokens.length!=2){
                    throw new IllegalStateException("Unformmated!(key:value\\tkey:value...)");
                }
                Entry entry = new Entry();
                entry.setKey(tokens[0]);
                entry.setValue(tokens[1]);
                entries.add(entry);
            }
            return entries;
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }
        return null;
    }
}
