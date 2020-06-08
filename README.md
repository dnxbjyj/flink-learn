# flink-learn
一步一步学习Apache Flink。

# flink程序本地运行步骤
- 命令行启动本地flink集群：`sh /usr/local/Cellar/apache-flink/1.9.0/libexec/bin/start-cluster.sh`
- 写完代码后Maven编译打包：`mvn clean package -Dmaven.test.skip=true`
- 运行wordcount程序：`flink run -c com.m2fox.wordcount.SocketTextStreamWordCount WordCount.jar 127.0.0.1 9999`，注：`-c`参数一定要写在jar包的前面，否则会报错。
- 查看输出日志：`tail -f /usr/local/Cellar/apache-flink/1.9.0/libexec/log/flink-m2fox-taskexecutor-0-m2foxdeMacBook-Pro.local.out`

# 运行常见问题
- `ProgramInvocationException: Could not retrieve the execution result`：本地集群没有启动。

# API&Docs
- [Apache Flink Home Page](https://flink.apache.org)
- [Flink DataSet API Programming Guide](https://ci.apache.org/projects/flink/flink-docs-release-1.10/dev/batch/index.html)
- [DataSet Transformations](https://ci.apache.org/projects/flink/flink-docs-release-1.10/dev/batch/dataset_transformations.html)
- [官方GitHub](https://github.com/apache/flink)
- [官方博客](https://flink.apache.org/blog/)
- [API样例汇集](https://www.programcreek.com/java-api-examples/?ClassName=flink&action=search&submit=Search)

