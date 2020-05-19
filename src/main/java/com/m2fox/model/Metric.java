package com.m2fox.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.Map;

// 统计对象
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Metric {
    // 名称
    private String name;
    // 时间戳
    private long timestamp;
    // 字段
    private Map<String, Object> fields;
    // 标签
    private Map<String, String> tags;
}
