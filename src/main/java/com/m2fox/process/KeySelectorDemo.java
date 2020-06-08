// AC
package com.m2fox.process;

import org.apache.flink.api.java.ExecutionEnvironment;
import org.apache.flink.api.java.functions.KeySelector;
import org.apache.flink.api.java.DataSet;
import org.apache.flink.api.common.functions.ReduceFunction;

import com.m2fox.model.Wc;

import java.util.List;
import java.util.ArrayList;

// 测试keyselector
public class KeySelectorDemo {
    public static void main(String[] argv) throws Exception{
        // - 创建运行时环境
        ExecutionEnvironment env = ExecutionEnvironment.getExecutionEnvironment();

        // - 构造数据集
        List<Wc> wcs = new ArrayList<>();
        wcs.add(new Wc("hello", 12));
        wcs.add(new Wc("hi", 3));
        wcs.add(new Wc("world", 5));
        wcs.add(new Wc("hello", 12));
        wcs.add(new Wc("hello", 14));
        DataSet<Wc> dataset = env.fromCollection(wcs);

        // - 分组执行reduce操作
        DataSet<Wc> words = dataset.groupBy(new SelectWord()).reduce(new WordCounter());

        // - 打印
        words.print();
    }

    // word选择器，返回word字段的值
    private static class SelectWord implements KeySelector<Wc, String> {
        @Override
        public String getKey(Wc wc) {
            return wc.word;
        }
    }

    private static class WordCounter implements ReduceFunction<Wc> {
        @Override
        public Wc reduce(Wc in1, Wc in2) {
            return new Wc(in1.word, in1.count + in2.count);
        }
    }
}

