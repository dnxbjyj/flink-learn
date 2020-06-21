// AC
package com.m2fox.dataset.join;

import org.apache.flink.api.common.functions.JoinFunction;
import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.operators.DataSource;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.java.operators.UnionOperator;
import org.apache.flink.api.java.functions.KeySelector;

import java.util.List;
import java.util.ArrayList;
import java.util.Arrays;

import com.m2fox.model.Record;

public class JoinWithKeySelectorDemo {
    public static void main(String[] argv) throws Exception{
        // - 创建运行环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // - 创建两个源数据集
        // 数据集data1：有2个字段，表示id、姓名
        List<Record> data1 = new ArrayList<>();
        Record rec1 = new Record();
        rec1.setValues(Arrays.asList(new Object[]{1001, "John"}));
        data1.add(rec1);
        Record rec2 = new Record();
        rec2.setValues(Arrays.asList(new Object[]{1002, "Tom"}));
        data1.add(rec2);
        Record rec3 = new Record();
        rec3.setValues(Arrays.asList(new Object[]{1003, "Jane"}));
        data1.add(rec3);

        // 数据集data2：有2个字段，表示id、国籍
        List<Record> data2 = new ArrayList<>();
        Record rec4 = new Record();
        rec4.setValues(Arrays.asList(new Object[]{1002, "US"}));
        data2.add(rec4);
        Record rec5 = new Record();
        rec5.setValues(Arrays.asList(new Object[]{1001, "China"}));
        data2.add(rec5);
        Record rec6 = new Record();
        rec6.setValues(Arrays.asList(new Object[]{1003, "UK"}));
        data2.add(rec6);        
        Record rec7 = new Record();
        rec7.setValues(Arrays.asList(new Object[]{1005, "India"}));
        data2.add(rec7);        
        
        // - 从数据集创建数据源dataset
        DataSource<Record> dataSet1 = env.fromCollection(data1);
        DataSource<Record> dataSet2 = env.fromCollection(data2);

        // - 两个数据源用于join的字段下标
        final int dataSet1JoinKey = 0;
        final int dataSet2JoinKey = 0;

        // - 打印两个数据集的原始数据
        System.out.println("原始数据：");
        System.out.println("dataSet1：");
        dataSet1.print();
        System.out.println("dataSet2：");
        dataSet2.print();
        
        // - 进行join操作
        DataSet<Record> joinDataSet = dataSet1.join(dataSet2)
            // 指定第一个数据集中连接的字段key
            .where(new KeySelector<Record, String>() {
                @Override
                public String getKey(Record value) throws Exception{
                    return value.getValues().get(dataSet1JoinKey).toString();
                }
            })
            // 指定第二个数据集中连接的字段key
            .equalTo(new KeySelector<Record, String>() {
                @Override
                public String getKey(Record value) throws Exception{
                    return value.getValues().get(dataSet2JoinKey).toString();
                }
            })
            // 指定join的具体业务逻辑
            .with(new JoinFunction<Record, Record, Record>() {
                @Override
                public Record join(Record first, Record second)
                        throws Exception {
                    List<Object> values1 = first.getValues();
                    List<Object> values2 = second.getValues();
                    
                    List<Object> values = new ArrayList<>();
                    values.add(values1.get(0));
                    values.add(values1.get(1));
                    values.add(values2.get(1));

                    Record newRecord = new Record();
                    newRecord.setValues(values);
                    return newRecord;
                }
            });

        // - 打印join后的合并数据
        System.out.println("join后的数据joinDataSet：");
        joinDataSet.print();
    }
}

