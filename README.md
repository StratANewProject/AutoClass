# AutoClass简介
通过发包，能够在湖南大学虚拟创业学院网站上瞬间刷完任何一门指定的课

# 使用说明
登录界面，用湖南大学虚拟创业学院的账号登录，若服务器响应速度和网速很慢，会需要一段时间才会跳转到主界面<br>
![Image text](https://github.com/StratANewProject/AutoClass/blob/master/%E7%99%BB%E5%BD%95%E7%95%8C%E9%9D%A2.JPG)

登录后的主界面，下方的方框可以输入<br>
![Image text](https://github.com/StratANewProject/AutoClass/blob/master/%E5%88%9D%E5%A7%8B%E4%B8%BB%E7%95%8C%E9%9D%A2.JPG)
<br>在输入框中输入课程对应的编号，用空格分隔。如：157 158<br>
课程编号查看方法：课程界面浏览器地址的id值。<br>
如:http://acadol.hnu.edu.cn/LMS/study/course/onlineCourse/detail.do?id=158 的课程编号是158。<br>
若服务器爆满或网速不佳，点击提交按钮后需要等待一段时间<br>

输入编号后点提交按钮<br>
![Image text](https://github.com/StratANewProject/AutoClass/blob/master/%E4%B8%BB%E7%95%8C%E9%9D%A2%E8%BF%90%E8%A1%8C%E7%BB%93%E6%9E%9C.jpg)

登录网站看刷课效果，可以看见课程是已完成状态，每个视频的观看时间是不同的随机数，范围是一到两小时<br>
![Image text](https://github.com/StratANewProject/AutoClass/blob/master/%E6%95%88%E6%9E%9C.JPG)

# 编译环境
win10,MyEclipse 2016 CI

# 编程语言
java

# 关于代码
关键发包代码在httpUtils.java中。学习抓包和发包的过程中使用了以下两个chrome插件：Postman Interceptor和Postman。<br>
这两个插件相配合能抓到从chrome发出的包。Postman对抓到的已发出的包可以将其直接转化为java代码,也能模拟发包。<br>
以上插件不能看到接收到的包，这时候可以用wireshark。

# 本人联系方式
szwpang@qq.com
