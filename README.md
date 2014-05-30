# java-sdk-for-UPYUN 一个简单易用易扩展的Java sdk
---

基于 [又拍云存储HTTP REST API接口](http://wiki.upyun.com/index.php?title=HTTP_REST_API接口) 开发，适用于Java 6及以上版本。

## 目录
* [文件处理接口](#文件处理接口)
  * [准备](#准备)
  * [上传文件](#上传文件)
  * [下载文件](#下载文件)
  * [删除文件](#删除文件)
  * [获取目录文件列表](#获取目录文件列表)
  * [获取使用量情况](#获取使用量情况)

<a name="文件处理接口"></a>
## 文件处理接口

<a name="准备"></a>
### 准备

##### 创建空间
大家可通过[又拍云主站](https://www.upyun.com/login.php)创建自己的个性化空间。具体教程请参见[“创建空间”](http://wiki.upyun.com/index.php?title=创建空间)。

##### 初始化UpYunClient
    UpYunClient client = UpYunClient.newClient("空间名称", "授权操作员名称", "操作员密码");

若不了解`授权操作员`，请参见[“授权操作员”](http://wiki.upyun.com/index.php?title=创建操作员并授权)


<a name="上传文件"></a>
### 上传文件

    有三种方式可以上传文件
    1.通过文件的绝对路径进行上传
        client.uploadFile("e:/upyuntest/cs-4-3-management-nfs.txt");
    
    2.通过文件对象上传
        File file = new File("e:/upyuntest/cs-4-3-management-nfs.txt");
        client.uploadFile(file);
        
    3.通过流上传
        File file = new File("e:/upyuntest/cs-4-3-management-nfs.txt");
        FileInputStream fis = new FileInputStream(file);
        client.uploadFile("cs-4-3-management-nfs.txt", fis, fis.available());
        
<a name="下载文件"></a>
### 下载文件

    client.downloadFile("d:/upyuntest/", "cs-4-3-management-nfs.txt");
    
<a name="删除文件"></a>
### 删除文件

    client.delete("cs-4-3-management-nfs.txt");
    



        
