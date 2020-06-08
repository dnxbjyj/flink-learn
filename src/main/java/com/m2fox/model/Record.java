package com.m2fox.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.List;

// 通用记录model类
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Record {
    // 字段列表
    private List<Object> values;
}
