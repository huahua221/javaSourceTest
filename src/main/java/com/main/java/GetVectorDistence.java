package com.main.java;

import java.util.*;

public class GetVectorDistence {

    public static double getVectorDistence(Map<String, Integer> firstkeyWordmap, Map<String, Integer> secondkeyWordmap) {
        // 本函数中采用最简单的距离向量计算
        // 公式：s=sqrt( ∑(xi1-xi2) 2 )
        double vd = 0;
        double sum = 0;
        try {
            // 前后两个map的字段值及位置一样
            for (String sfirst : firstkeyWordmap.keySet()) {
                Integer valuefirst = firstkeyWordmap.get(sfirst);
                Integer valuesecond = secondkeyWordmap.get(sfirst);
                sum = sum + (valuefirst-valuesecond)*(valuefirst-valuesecond);
                vd = 100 - Math.sqrt(sum);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return vd;
    }

    public static void TfIdf(Map<String, Integer> firstkeyWordmap, Map<String, Integer> secondkeyWordmap) {
        // tf:词频
        // idf:逆文档频率
        double tf = 0;
        double idf = 0;

    }


}
