# jtree
快速导入和导出树形结构的工具

一、目的

很多时候我们需要在程序中对树进行操作，本工具编写一个可以灵活解析和存储树结构数据的jar包，提取树结构的一些共性，并提供一定的扩展和移植能力，以便我们以后能够进行代码复用。

该jar包命名为“jtree”，表示“java tree”，应该提供的简易功能如下：

1、从多种数据源读取存储树的数据（可扩展多个数据源），并解析为基本的Tree类型（以及RD自定义的Tree子类）

2、Tree内部保存了一个树形数据结构，由Node类型构成（或者RD自定义的Node子类）

3、Tree和Node是etree中的基本类型（可继承移植），提供了基本的对树的一些操作

4、可以将内存中Tree类型的节点数据存储到多种数据源中，供以后读取。

这里的数据源可以包括：普通字符串、JSON对象、文件甚至是SQL语句。

二、架构

2.1、概述

我们的软件包主要分两部分功能，一部分是读取解析，一部分是反解析存储。 读取解析意味着有两个步骤，第一步从多个数据源读取含树结构的中间数据，第二步将这些数据装配成一个树的bean。反解析存储是逆过程，其第一步将bean反解析成中间数据，然后根据选择的数据源格式存储这些数据。整个过程的示意图如下：

引入中间数据entries的作用在于可以灵活适配多种数据源，因为从entries到Tree的路径2可以是指定的，因而不管多少数据格式，只要能通过路径1转化成entries，就能够解析为Tree。同理，Tree不关心要存储成何种数据格式，只要能够通过路径3反解析成entries，剩余工作有路径4完成。

2.2、设计原理

2.2.1、TreeFactory

树的构造流程，其实封装了2.1中路径1和路径2的实现，是调用的api，类似于“工厂模式”和“装饰模式”，它封装了实现细节，并提供了构建类实例的方法。子类使用了“模版模式”，所有TreeFactory子类都继承至AbstractTreeFactory模版，并需要指定一个EntriesLoader和一个NodeParser。

路径1的实际工作由EntriesLoader及其子类完成，它实现了多种数据源转化为Entries的工作，例如JSONEntriesLoader能够将json串转化为entries，有一些数据源以流的形式提供，这时候需要提供开启流和关闭流的接口，StreamHolder提供了这个接口，这个接口也可以用来做一些初始化和收尾工作，例如SQLEntriesLoaderStream就用它来开启数据库连接、查询、关闭数据库连接等工作。 路径2的实际工作由NodeParser及其子类完成，它实现了将entries解析成Node，并组装成树的工作。NodeStreamParser用于流式数据源，完成开启和关闭流的工作。

2.2.2、TreeStorage

类似域TreeFactory，TreeStorage是树的存储过程，其实封装了2.1中路径3和路径4的实现，也是调用api，类似于“装饰模式”，它封装存储的实现细节，并提供了存储树的方法。子类使用了“模版模式”，所有TreeStorage继承至AbstractTreeStorage模版，并需要指定一个EntriesStorer和一个NodeDeparser。

路径3的实际工作由NodeDeparser完成，它实现了将树的节点数据转化成Entries的工作。

路径4的实际工作由EntriesStorer及其子类完成，它实现了将entries存储到多数据源的工作。

2.2.3、Tree 和Node

TreeFactory构造的就是一个Tree类，Tree中的数据就是Node类。我们提供了基本的实现，DefaultNode和DefaultTree，并给出了一些基本的接口，这两个类都是可以扩展的，因为我们不能强制RD使用固定的Tree和Node，当RD继承了这两个类时，应该在选择TreeFactory时指定这两个class。TreeStorage也要指定这两个class。

2.2.4、ClazzStackAware

这个类及其子类其实提供了类反射的一些工具，这些反射方法将在装配和解析类实例时使用。此外，我们使用注解FieldMapping来帮助指定节点的属性字段。FieldMappingFieldsAware的作用就是解释这些带注解的属性。

三、范例

3.1、简易api

    //这是由RD继承的Node节点，可以配置自己的属性，并添加注解FieldMapping，映射数据源里的字段
    public static class A extends DefaultNode{
    //映射数据源的节点属性到本字段
        @FieldMapping(target = "org_id")
        private long id;
        public long getId(){
            return this.id;
        }
        @FieldMapping(target = "parent_id")
        private long pid;
        public long getPid(){
            return this.pid;
        }
    }

    public static void testStringEntriesReader(){
        //字符串类型的数据源，一行表示一个节点
        String text = "org_id:2  parent_id:1\n" +
                "org_id:213 parent_id:2";
    
        //声明一个StringTreeFactory，用于从字符串中构造树
        StringTreeFactory treeFactory = new StringTreeFactory(DefaultTree.class,A.class);
        treeFactory.setContent(text2);
        //默认根节点是0，这里指定为1
        treeFactory.setRootId(1);
    
        //调用build即可生成树，需要指定Tree和Node的具体子类
        Tree tree = treeFactory.buildTree();
        A root = (A)tree.getRoot();
    
        System.out.println(root.getId() + ";" + root.getPid());
        System.out.println("end");
    
        //将树存储到storage中，转换成字符串
        StringTreeStorage storage = new StringTreeStorage(DefaultTree.class,A.class);
        storage.storeTree();
        System.out.println(storage.getContent());
    }


    public static void testDBEntriesLoader(){
        //sql类型的数据源，需要指定一个DataSource
        SQLTreeFactory sqlTreeFactory = new SQLTreeFactory(DefaultTree.class,A.class);
        MySqlDataSource mds = new MySqlDataSource();
        sqlTreeFactory.setDataSource(mds);
    
        //在构造树前，要切换sql语句，查询出来的一行表示一个节点
        sqlTreeFactory.switchSQL("select parent_id,org_id from view_node where view_id=5 and node_state=1");
        DefaultTree tree = sqlTreeFactory.buildTree();
        A root = (A) tree.getRoot();
    
        System.out.println(root.getId() + ";" + root.getPid());
    
        //可以将树转成字符串
        StringTreeStorage storage = new StringTreeStorage(DefaultTree.class,A.class);
        storage.storeTree();
        System.out.println(storage.getContent());
    }

    public static void testJSONEntriesLoader(){
        //JSON类型的数据源
        JSONArray array = new JSONArray();
        JSONObject obj = new JSONObject();
        obj.put("org_id","2");
        obj.put("parent_id","1");
        array.add(obj);
        obj = new JSONObject();
        obj.put("org_id","23");
        obj.put("parent_id","2");
        array.add(obj);
    
        //需要指定一个JSONArray对象
        JSONTreeFactory treeFactory = new JSONTreeFactory(DefaultTree.class,A.class);
        treeFactory.setRootId(1);
        treeFactory.setJsonArray(array);
    
        Tree tree = treeFactory.buildTree();
        A root = (A)tree.getRoot();
        //System.out.println(root.getId() + ";" + root.getPid());
        //System.out.println("end");
    
        //将树转化为json串
        JSONTreeStorage storage = new JSONTreeStorage(DefaultTree.class,A.class);
        storage.storeTree();
        System.out.println(storage.getJsonArray());
    }
