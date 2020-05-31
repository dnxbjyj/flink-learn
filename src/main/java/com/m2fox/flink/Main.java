// AC
// 测试kafka作为数据源
package com.m2fox.flink;

import org.apache.flink.api.common.serialization.SimpleStringSchema;
import org.apache.flink.streaming.api.datastream.DataStreamSource;
import org.apache.flink.streaming.api.environment.StreamExecutionEnvironment;
import org.apache.flink.streaming.connectors.kafka.FlinkKafkaConsumer011;

import java.util.Properties;

public class Main {
    public static void main(String[] argv) throws Exception {
        // - 获取env
        final StreamExecutionEnvironment env = StreamExecutionEnvironment.getExecutionEnvironment();

        // - 配置属性
        Properties props = new Properties();
        props.put("bootstrap.servers", "localhost:9092");
        props.put("zookeeper.connect", "localhost:2181");
        props.put("group.id", "metric-group");
        props.put("key.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("value.deserializer", "org.apache.kafka.common.serialization.StringDeserializer");
        props.put("auto.offset.reset", "latest");

        // - 添加数据源
        DataStreamSource<String> source = env.addSource(
            new FlinkKafkaConsumer011<>("metric",  // kafka topics
                                        new SimpleStringSchema(),  // String序列化
                                        props)).setParallelism(1);  // 设置并行度

        // - 打印数据源数据到控制台
        source.print();

        // - 执行
        env.execute("Flink add data source");
    }
}
