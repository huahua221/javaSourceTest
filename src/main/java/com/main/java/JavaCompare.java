package com.main.java;

import groovy.json.JsonOutput;
import groovy.transform.ASTTest;
import org.junit.Test;

import java.io.File;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class JavaCompare {
    // c++关键字
    public static final String CkeyWords = "and|asm|auto|bad_cast|bad_typeid|bool|break|case|catch|char|class|const|const_cast" +
            "|continue|default|delete|do|double|dynamic_cast|else|enum|except|explicit|extern|false|finally|float|for" +
            "|friend|goto|if|inline|int|long|mutable|namespace|new|operator|or|private|protected|public|register|reinterpret_cast" +
            "|return|short|signed|sizeof|static|static_cast|struct|switch|template|this|throw|true|try|type_info|typedef" +
            "|typeid|typename|union|unsigned|using|virtual|void|volatile|wchar_t|while";
    // java关键字
    public static final String JavakeyWords = "goto|const|class|interface|byte|short|int|lond|float|double" +
            "|char|boolean|void|true|false|null|if|else|switch|case|default|while|do|for|break|continue|return" +
            "|private|protected|public|abstract|final|static|synchronized|extends|implements|new|this|super" +
            "|instanceof|try|catch|finally|throw|throws|package|import|native|strictfp|transient|volatile|assert|enum";
    // 符号（操作符）
    public static final String Symbol = "-|/|=|>|+|*|%|==|<|.|:|,|;|'|[|]|{|}|(|)|!";

    public static final HashSet<String> javakeyWordSet = new HashSet<String>();
    public static final HashSet<String> ckeyWordSet = new HashSet<String>();
    public static final HashSet<String> symbolWordSet = new HashSet<String>();

    private JavaCompare() {
        String clist[] = CkeyWords.split("\\|");
        String javalist[] = JavakeyWords.split("\\|");
        String symbollist[] = Symbol.split("\\|");
        for (String keyword : clist) {
            ckeyWordSet.add(keyword);
        }
        for (String keyword : javalist) {
            javakeyWordSet.add(keyword);
        }
        for (String symbol : symbollist) {
            symbolWordSet.add(symbol);
        }
    }

    public static void main(String[] args) {
        JavaCompare jc = new JavaCompare();
//        String file1 = "/Users/huahuadepro/自动化测试平台数据库/java.antjunit/src";
        String file1 = "/Users/huahuadepro/自动化测试平台数据库/java.antjunit/src/com/ecax/java/SimpleCalculation.java";
        String file2 = "/Users/huahuadepro/自动化测试平台数据库/java.antjunit/src/com/ecax/java/SimpleCalculationCopy.java";
        // 预处理：删除注释、空格、换行
        ArrayList<String> codeArray = DelComments.clearCommentandBlank(file1);
        ArrayList<String> codeArray2 = DelComments.clearCommentandBlank(file2);
//        System.out.println(codeArray);

        // 统计关键字个数（特征向量提取）
        Map<String, Integer> javakeyWordMap = KeyStatistic.keyStatistic(codeArray, javakeyWordSet, symbolWordSet);
        Map<String, Integer> javakeyWordMap2 = KeyStatistic.keyStatistic(codeArray2, javakeyWordSet, symbolWordSet);
//        System.out.println(JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(javakeyWordMap)));

        // 简单计算矢量距离
        double sim = GetVectorDistence.getVectorDistence(javakeyWordMap, javakeyWordMap2);
        System.out.println("相似度" + ":" + sim + "%");

        // 逆文件频率TF-IDF、向量夹脚计算
    }
}
