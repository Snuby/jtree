package com.jay.open.libs.jtree.entriesstorers;

import com.jay.open.libs.common.Entry;
import com.jay.open.libs.common.io.EntriesStorerStream;
import com.jay.open.libs.common.io.TextStorageStream;
import com.jay.open.libs.common.validate.Assert;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-13
 * Time: 18:50
 * To change this template use File | Settings | File Templates.
 */
public class TextEntriesStorerStream implements EntriesStorerStream<String,String>,TextStorageStream {
    private Logger log = LoggerFactory.getLogger(TextEntriesStorerStream.class);

    private OutputStream os;
    private BufferedWriter writer;

    private String cacheLine = null;

    @Override
    public void storeEntries(List<Entry<String, String>> entries) {
        //To change body of implemented methods use File | Settings | File Templates.
        StringBuilder builder = new StringBuilder();
        for(Entry<String,String> e:entries){
            builder.append(e.getKey());
            builder.append(":");
            builder.append(e.getValue());
            builder.append(" ");
        }
        builder.append("\n");
        cacheLine = builder.toString();
        writeLine();
    }

    @Override
    public Object flush() {
        return null;
    }

    @Override
    public void openStream() {
        //To change body of implemented methods use File | Settings | File Templates.
        try{
            if(getOutputStream()==null){
                throw new IllegalAccessException("Please invoke setOutputStream first!");
            }
            OutputStreamWriter osw = new OutputStreamWriter(getOutputStream());
            writer = new BufferedWriter(osw);
        }catch (Exception e) {
            log.error(e.getMessage(),e);
        }
    }

    @Override
    public boolean isStreamOpened() {
        return getOutputStream()!=null;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    public void closeStream() {
        //To change body of implemented methods use File | Settings | File Templates.
        try {
            if(writer!=null){
                writer.close();
            }
        }catch (Exception e){
            log.error(e.getMessage(),e);
        }finally {
            writer = null;
            try{
                if(os!=null){
                    os.close();
                }
            }catch (Exception e){
                log.error(e.getMessage(),e);
            }finally {
                os = null;
            }
        }
    }

    @Override
    public void writeLine() {
        //To change body of implemented methods use File | Settings | File Templates.
        if(cacheLine==null)return;
        try {
            writer.write(cacheLine);
        } catch (IOException e) {
            log.error(e.getMessage(),e);  //To change body of catch statement use File | Settings | File Templates.
        }
    }

    @Override
    public OutputStream getOutputStream() {
        return os;  //To change body of implemented methods use File | Settings | File Templates.
    }

    public void setOutputStream(OutputStream os){
        Assert.notNull(os);
        this.os = os;
    }
}
