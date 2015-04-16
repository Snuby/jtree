package com.sankuai.xm.eas.libs.etree.tests;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.jay.open.libs.common.TimeCounter;
import com.jay.open.libs.common.annotations.FieldMapping;
import com.jay.open.libs.jtree.DefaultNode;
import com.jay.open.libs.jtree.DefaultTree;
import com.jay.open.libs.jtree.Tree;
import com.jay.open.libs.jtree.factory.JSONTreeFactory;
import com.jay.open.libs.jtree.factory.SQLTreeFactory;
import com.jay.open.libs.jtree.factory.StringTreeFactory;
import com.jay.open.libs.jtree.storage.JSONTreeStorage;
import com.jay.open.libs.jtree.storage.StringTreeStorage;

/**
 * Created with IntelliJ IDEA.
 * User: suweijie
 * Date: 14-10-24
 * Time: 13:20
 * To change this template use File | Settings | File Templates.
 */
public class ETTest {

    public static class A extends DefaultNode {

        @FieldMapping(target = "parent_id")
        private long pid;

        @FieldMapping(target = "id")
        private long id;

        @FieldMapping
        private String name;

        @FieldMapping(target = "name_path")
        private String namePath;

        public long getId(){
            return id;
        }

        public void setId(long id){
            this.id = id;
        }

        public long getPid() {
            return pid;
        }

        public void setPid(long pid) {
            this.pid = pid;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getNamePath() {
            return namePath;
        }

        public void setNamePath(String namePath) {
            this.namePath = namePath;
        }
    }


    public static void testStringEntriesReader(){
        String text = "id:2  parent_id:1\n" +
                "id:213 parent_id:2";
        String text2 = "id:2  pid:1 \n" +
                "id:213 pid:2 \n";

        StringTreeFactory treeFactory = new StringTreeFactory(DefaultTree.class,DefaultNode.class);
        treeFactory.setContent(text2);
        treeFactory.setRootId(1);

        Tree tree = treeFactory.buildTree();

        System.out.println(tree.toString());
        System.out.println("end");

        StringTreeStorage storage = new StringTreeStorage();
        storage.storeTree(tree,DefaultNode.class);
        System.out.println(storage.getContent());

    }

    public static void testDBEntriesLoader(){

        SQLTreeFactory sqlTreeFactory = new SQLTreeFactory(DefaultTree.class,A.class);
        MySqlDataSource mds = new MySqlDataSource();
        sqlTreeFactory.setDataSource(mds);
        sqlTreeFactory.setRootId(0);
        sqlTreeFactory.switchSQL("select * from org where status=1 and parent_id!=99999 and id_path like '0-1%'");

        DefaultTree tree = (DefaultTree) sqlTreeFactory.buildTree();
        A root = (A) tree.getRoot();
        System.out.println(tree.toString());

        //StringTreeStorage storage = new StringTreeStorage();
        //storage.storeTree(tree,A.class);
        //System.out.println(storage.getContent());
    }

    public static void testJSONEntriesLoader(){
        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("id","2");
        obj.put("parent_id","1");
        array.add(obj);
        obj = new JSONObject();
        obj.put("id","23");
        obj.put("parent_id","2");
        array.add(obj);

        JSONTreeFactory treeFactory = new JSONTreeFactory(DefaultTree.class,A.class);
        treeFactory.setRootId(1);
        treeFactory.setJsonArray(array);

        Tree tree = treeFactory.buildTree();

        System.out.println(tree.toString());
        System.out.println("end");

        JSONTreeStorage storage = new JSONTreeStorage();
        storage.storeTree(tree,A.class);
        System.out.println(storage.getJsonArray());

    }

    public static void main(String[] args)throws Exception{

        testStringEntriesReader();
        testJSONEntriesLoader();
        //counter.showElapse();
        //testClassA();
        //System.out.println(StringParser.parseString("123",Character.class).getClass());
        //testDBEntriesLoader();
    }
}
