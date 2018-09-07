# 1 使用说明

## 1.1 安装

> 本系统基于Spring Boot 2 ，因此请务必使用JDK8，且打开编译选项parameters
> - Eclipse中开启的办法Preferences->java->Compiler下勾选Store information about method parameters选项。
这样在使用eclipse编译java文件的时候就会将参数名称编译到class文件中。
> - Idea中开启的方法File->Settings->Build,Execution,Deployment->Java Compiler下的Additional command line parameters选项中添加-parameters。


1. 通过IDE导入此Maven工程，包含俩个子工程

* core  ，核心包，包含了缓存，数据权限，公用的JS和HTML页面。
* admin-panel, 系统管理功能，包含了用户，组织机构，角色，权限，数据权限，代码生成等管理功能

2. 初始化数据库，位于doc/starter-mysql.sql

3. 修改 admin-panel 下的 application.properties 配置文件,修改你的数据库地址和访问用户

4. 执行 admin-panel/com.ibeetl.admin.CosonleApplication 启动类

5. 如果没有报错，可以在浏览器中访问 localhost:8080 看到登录界面，输入 admin/123456 则可以直接登录进入管理系统

## 1.2 创建应用

> Pandora 适合大系统拆分成小系统的架构，或者是一个微服务系统，因此，如果你需要创建自己的业务系统，比如，一个CMS子系统，建议你不要在框架核心添加代码，应该是新建立一个maven工程。
然后选择依赖 core 或者 admin-panel , 至于具体依赖哪一个看你的系统是否需要后台管理，如果有就使用 admin-panel 

而这一部分的工作可以不用我们手动的完成，在系统中我们直接登录到 admin-panel 后台创建子系统，即可以生成一个简单的具有后台管理的系统。具体步骤：
1. 可以进入代码生成>子系统生成
2. 输入maven项目路径，还有包名，即可生成一个可运行的应用

### 1.2.1 配置应用

新建的应用不需要做任何配置即可在IDE里直接运行

1. 如果你想打包城jar方式运行，则需要添加
~~~xml
<build>
    <plugins>
        <plugin>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-maven-plugin</artifactId>
        </plugin>
    </plugins>
</build>
~~~

2. 如果你想打包成war放到tomcat下运行，需要修改maven打包为war
~~~xmml
<packaging>war</packaging>
~~~


## 1.3 业务代码生成
业务代码生成其实就是对一张表的简单的CURD功能的生成，省去了繁琐的手写CRUD的过程，代码生成针对表进行代码生成，包括JS，JAVA，SQL和HTML，可以通过预览功能直接预览。
在生成代码到本地前，有些参数需要修改，否则，代码生成后显示的都是英文。

* 显示字段 ： 当此实体显示在任何地方的时候，能代表此实体的名称，比如用户名，组织机构名
* 变量名：可以自己设定一个较短的名字，此变量名会用于前后端的变量
* urlBase：你规划的子系统，最后访问路径是urlBase+变量名字
* system: 存放sql目录的的名称

其他修改的地方有

是否包含导入导出，如果选择，则会生成导入导出的代码，导入导出模板则需要参考已有功能(比如数据字典)来完成

是否包含附件管理，如果选择，则业务对象可以关联一组附件，比如客户关联一组附件，或者申请信息关联一组附件。

字段信息的显示名字，这个用于前端列表，表单的显示，应当输入中文名字

作为搜索，可以勾选几个搜索条件，系统自动生成一个搜索配置类

如果字段关联数据字典，那么设置一个数据字典，这样，生成的界面将会变成一个下拉列表