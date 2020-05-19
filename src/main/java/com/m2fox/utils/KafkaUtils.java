package com.m2fox.utils;

import com.alibaba.fastjson.JSON;
import com.m2fox.model.Metric;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

// kafka工具类
public class KafkaUtils {
    public static final String BROKER_LIST = "localhost:9092";

    public static final String TOPIC = "metric";

    // 写数据到kafka
    public static void writeToKafka () throws Exception {
        // - 配置项
        Properties props = new Properties();
        props.put("bootstrap.servers", BROKER_LIST);
        // key序列化
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        // value序列化
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

        // - 生产者
        KafkaProducer producer = new KafkaProducer<String, String>(props);

        // - 度量数据
        Metric metric = new Metric();
        metric.setTimestamp(System.currentTimeMillis());
        metric.setName("mem");

        Map<String, String> tags = new HashMap<>();
        tags.put("cluster", "m2fox");
        tags.put("host_ip", "100.45.67.89");
        metric.setTags(tags);
        
        Map<String, Object> fields = new HashMap<>();
        fields.put("used_percent", 90d);
        fields.put("max", 27245642d);
        fields.put("used", 17894242d);
        fields.put("init", 27245642d);
        metric.setFields(fields);

        // - 构造生产者数据
        ProducerRecord record = new ProducerRecord<String, String>(TOPIC, null, null, JSON.toJSONString(metric));
        // - 发送数据
        producer.send(record);

        System.out.println("发送数据： " + JSON.toJSONString(metric));

        // - 刷新
        producer.flush();
    }

    public static void main(String[] argv) throws Exception {
        // - 循环往kafka写数据，模拟输出产生
        while (true) {
            Thread.sleep(300);
            writeToKafka();
        }
    }
}
