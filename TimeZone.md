1. Date和时区
https://blog.csdn.net/qq_28988969/article/details/103480911
2. LocalDateTime和时区
https://www.cnblogs.com/mosakashaka/p/13260708.html
   https://zhuanlan.zhihu.com/p/415688125

说起java中的时间，大家或许都很熟悉，朗朗上口的Date、Calendar、SimpleDateFormat等。但是大家对他们的认识真的很深刻吗？今天我要想大家说的是TimeZone,即时区。

经常有人发现时间不对，比如相差8个小时等等，其真实原因便是TimeZone.只有正确合理的运用TimeZone,才能保证系统时间无论何时都是准确的。

一、影响TimeZone的因素：
1、操作系统的时区设置,TZ环境变量设置。
2、数据传输时时区设置。
3、代码中调用TimeZone.setDefault()
4、JVM参数user.timezone,例如-Duser.timezone=
5、根据JVM参数中的user.country和java.home自动选择

第一个原因其实是根本原因，当数据在不同操作系统间流转时，就有可能因为操作系统的差异造成时间偏差，而JVM默认情况下获取的就是操作系统的时区设置。因此在项目中最好事先设置好时区，


jvm在读取linux的系统时区时：
如果存在环境变量TZ，则jvm最优先读取环境变量TZ中的内容；
如果不存在环境变量TZ，会读取/etc/sysconfig/clock文件中的ZONE内容（需要引号）；
若都不存在，则读取使用/etc/localtime文件内容所对应的时区。
如果设置了-Duser.timezone，则使用指定的时区；


1、在程序中使用java的函数设定时区。
2、在启动java程序时加参数-Duser.timezone=GMT+8

附设置为东八区的配置
1、java -Duser.timezone=Asia/Shanghai
2、/etc/sysconfig/clock文件内容ZONE=”Asia/Shanghai”
3、ln -s /etc/localtime /usr/share/zoneinfo/Asia/Shanghai
4、export TZ=Asia/Shanghai



https://www.cnblogs.com/yourbatman/p/14307194.html#%E8%AE%BE%E7%BD%AE%E9%BB%98%E8%AE%A4%E6%97%B6%E5%8C%BA


https://chanjarster.github.io/post/oracle-timezone/

https://blog.csdn.net/Hehuyi_In/article/details/104883708

https://www.cnblogs.com/5icuke/articles/7155459.html

https://www.cnblogs.com/mosakashaka/p/13260708.html

https://pdai.tech/md/java/java8/java8-localdatetime.html

https://zhuanlan.zhihu.com/p/415688125

https://www.cnblogs.com/chengxuxiaoyuan/p/11802241.html

