// AC
package com.m2fox.dataset.distinct;

import org.apache.flink.api.common.functions.FlatMapFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.operators.FlatMapOperator;
import org.apache.flink.util.Collector;

import java.util.ArrayList;


public class BatchDistinctDemo {
    public static void main(String[] argv) throws Exception{
        // - 获取运行时环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // - 构建数据集
        ArrayList<String> data = new ArrayList<>();
        data.add("hello world");
        data.add("hello you");
        data.add("hi world");
        DataSource<String> source = env.fromCollection(data);

        // - 单词数据
        DataSet<String> wordData = source.flatMap(
            new FlatMapFunction<String, String>() {
                @Override
                public void flatMap(String value, Collector<String> out) throws Exception{
                    // - 对输入字符串进行单词分割
                    String[] splited = value.toLowerCase().split("\\W");
                    for (String word: splited) {
                        out.collect(word);
                    }
                }
            }
        );

        // - 整体去重并输出
        wordData.distinct().print();
    }
}
