// AC
package com.m2fox.dataset.topn;

import org.apache.flink.api.common.operators.Order;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.tuple.Tuple2;

import java.util.ArrayList;

public class BatchTopnDemo {
    public static void main(String[] argv) throws Exception{
        // - 创建运行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // - 创建一个数据集
        ArrayList<Tuple2<Integer, String>> data = new ArrayList<>();
        data.add(new Tuple2<>(2,"zs"));
        data.add(new Tuple2<>(4,"ls"));
        data.add(new Tuple2<>(3,"ww"));
        data.add(new Tuple2<>(1,"xw"));
        data.add(new Tuple2<>(1,"aw"));
        data.add(new Tuple2<>(1,"mw"));
        DataSource<Tuple2<Integer, String>> source = env.fromCollection(data);

        // - 按照数据插入的顺序获取前3条数据
        System.out.println("按照数据插入的顺序获取前3条数据：");
        source.first(3).print();

        // - 按照第1列分组，获取每组的前2个元素
        System.out.println("按照第1列分组，获取每组的前2个元素：");
        source.groupBy(0).first(2).print();

        // - 按照第1列分组，再按照第2列进行组内升序排序，获取每组的前2个元素
        System.out.println("按照第1列分组，再按照第2列进行组内升序排序，获取每组的前2个元素：");
        source.groupBy(0).sortGroup(1, Order.ASCENDING).first(2).print();

        // - 不分组，全局排序获取集合的前3个元素，对第1列升序排列，对第2列降序排列
        System.out.println("不分组，全局排序获取集合的前3个元素，对第1列升序排列，对第2列降序排列：");
        source.sortPartition(0, Order.ASCENDING).sortPartition(1, Order.DESCENDING).first(3).print();
        
    }
}
