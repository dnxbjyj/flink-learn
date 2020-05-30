// AC
package com.m2fox.dataset.join;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.operators.UnionOperator;
import org.apache.flink.api.java.tuple.Tuple2;
import org.apache.flink.api.java.tuple.Tuple3;

import java.util.ArrayList;

public class BatchJoinDemo {
    public static void main(String[] argv) throws Exception{
        // - 创建运行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // - 创建两个源数据集
        // 数据集data1：有2个字段，表示id、姓名
        ArrayList<Tuple2<Integer, String>> data1 = new ArrayList<>();
        data1.add(new Tuple2<>(1001, "John"));
        data1.add(new Tuple2<>(1002, "Tom"));
        data1.add(new Tuple2<>(1003, "Jane"));        

        // 数据集data2：有2个字段，表示id、国籍
        ArrayList<Tuple2<Integer, String>> data2 = new ArrayList<>();
        data2.add(new Tuple2<>(1002, "US"));
        data2.add(new Tuple2<>(1001, "China"));
        data2.add(new Tuple2<>(1003, "UK"));

        // - 从数据集创建数据源dataset
        DataSource<Tuple2<Integer, String>> dataSet1 = env.fromCollection(data1);
        DataSource<Tuple2<Integer, String>> dataSet2 = env.fromCollection(data2);

        // - 打印两个数据集的原始数据
        System.out.println("原始数据：");
        dataSet1.print();
        dataSet2.print();
        
        // - 进行join操作
        DataSet<Tuple3<Integer, String, String>> joinDataSet = dataSet1.join(dataSet2)
            // 指定第一个数据集中需要比较的字段下标
            .where(0)
            // 指定第二个数据集中需要比较的字段下标
            .equalTo(0)
            // 指定join的具体业务逻辑
            .with(new JoinFunction<Tuple2<Integer,String>, Tuple2<Integer,String>, Tuple3<Integer,String,String>>() {
                @Override
                public Tuple3<Integer, String, String> join(Tuple2<Integer, String> first, Tuple2<Integer, String> second)
                        throws Exception {
                    return new Tuple3<>(first.f0,first.f1,second.f1);
                }
            });

        // - 打印join后的合并数据
        System.out.println("join后的数据：");
        joinDataSet.print();
    }
}
