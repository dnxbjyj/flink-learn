// AC
package com.m2fox.dataset.union;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.operators.UnionOperator;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.ArrayList;

public class BatchUnionDemo {
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
        data2.add(new Tuple2<>(1001, "China"));
        data2.add(new Tuple2<>(1002, "US"));
        data2.add(new Tuple2<>(1003, "UK"));

        // - 从数据集创建数据源dataset
        DataSource<Tuple2<Integer, String>> dataSet1 = env.fromCollection(data1);
        DataSource<Tuple2<Integer, String>> dataSet2 = env.fromCollection(data2);

        // - 打印两个数据集的原始数据
        System.out.println("原始数据：");
        dataSet1.print();
        dataSet2.print();
        
        // - 进行union操作
        DataSet<Tuple2<Integer, String>> unionDataSet = dataSet1.union(dataSet2);

        // - 打印union后的合并数据
        System.out.println("union后的数据：");
        unionDataSet.print();
    }
}
