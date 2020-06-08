package com.m2fox.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

// 词频统计model类
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class Wc {
    public String word;
    public int count;
}
