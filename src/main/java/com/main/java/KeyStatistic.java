package com.main.java;

import org.omg.Messaging.SYNC_WITH_TRANSPORT;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.*;

import groovy.json.JsonOutput;

// 删除变量名及函数名，只留下关键字
public class KeyStatistic {
    private static int count = 0; // 用于计数已经删除了注释的文件个数

    /**
     * 关键字统计
     *
     * @param codeArray  预处理之后的string矩阵
     * @param keyWordset 关键字集合
     * @return 关键字统计map值
     */
    public static Map<String, Integer> keyStatistic(ArrayList<String> codeArray, HashSet<String> keyWordset, HashSet<String> symbolWordSet) {
        Map<String, Integer> keyWordMap = new HashMap<String, Integer>();
        try {
            // 统计关键字
            for (String key : keyWordset) {
                int count = 0;
                key = key.trim(); // 去掉两端多余的空格
                for (String codelist : codeArray) {
                    codelist = " " + codelist + " ";
                    count = (codelist.split(key).length - 1) + count; // 循环添加
                    keyWordMap.put(key, count);
                }
            }
            // 统计符号
            for (String sym : symbolWordSet) {
                int countsym = 0;
                sym = sym.trim();
                for (String codelist : codeArray) {
                    for (int i = 0; i < codelist.length(); i++) {
                        String s = Character.toString(codelist.charAt(i));
                        if (sym.equals(s)) {
//                            System.out.println("匹配");
                            countsym = countsym + 1;
                        }
                    }
                    keyWordMap.put(sym, countsym);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return keyWordMap;
    }


}
