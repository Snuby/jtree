package com.jay.open.libs.jtree.factory;

import com.jay.open.libs.common.io.EntriesLoader;
import com.jay.open.libs.jtree.Node;
import com.jay.open.libs.jtree.parsers.NodeParser;
import com.jay.open.libs.jtree.parsers.NodeStreamParser;
import com.jay.open.libs.jtree.Tree;
import com.jay.open.libs.jtree.entriesloaders.TextEntriesLoaderStream;
import com.jay.open.libs.jtree.exceptions.TreeFactoryException;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-11-12
 * Time: 17:20
 * To change this template use File | Settings | File Templates.
 */
public class FileTreeFactory extends AbstractTreeFactory{

    private String filePath;
    private TextEntriesLoaderStream loaderStream;
    private NodeStreamParser nodeStreamParser;

    public FileTreeFactory(Class<? extends Tree> treeClazz, Class<? extends Node> nodeClazz) {
        super(treeClazz, nodeClazz);
    }

    public void setFilePath(String filePath) {
        this.filePath = filePath;
    }

    @Override
    protected EntriesLoader getEntriesLoader() {
        if(this.filePath==null){
            throw new TreeFactoryException("filePath is null, please invoke setFilePath first");
        }
        File file = new File(filePath);
        FileInputStream fis;
        try {
            fis = new FileInputStream(file);
        } catch (FileNotFoundException e) {
            throw new TreeFactoryException("filePath no exist or it is not a file!");  //To change body of catch statement use File | Settings | File Templates.
        }

        loaderStream = new TextEntriesLoaderStream();
        loaderStream.setInputStream(fis);

        return loaderStream;  //To change body of implemented methods use File | Settings | File Templates.
    }

    @Override
    protected NodeParser getNodeParser(Class<? extends Node> nodeClazz) {
        nodeStreamParser = new NodeStreamParser(nodeClazz);
        return nodeStreamParser;  //To change body of implemented methods use File | Settings | File Templates.
    }
}
