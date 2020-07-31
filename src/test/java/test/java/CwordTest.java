package test.java;

import com.main.java.DelComments;
import com.main.java.GetVectorDistence;
import com.main.java.KeyStatistic;
import groovy.json.JsonOutput;
import org.junit.Test;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Map;

public class CwordTest {
    // c++关键字
    public static final String CkeyWords = "and|asm|auto|bad_cast|bad_typeid|bool|break|case|catch|char|class|const|const_cast" +
            "|continue|default|delete|do|double|dynamic_cast|else|enum|except|explicit|extern|false|finally|float|for" +
            "|friend|goto|if|inline|int|long|mutable|namespace|new|operator|or|private|protected|public|register|reinterpret_cast" +
            "|return|short|signed|sizeof|static|static_cast|struct|switch|template|this|throw|true|try|type_info|typedef" +
            "|typeid|typename|union|unsigned|using|virtual|void|volatile|wchar_t|while";
    // 符号（操作符）
    public static final String Symbol = "-|/|=|>|+|*|%|==|<|.|:|,|'|[|]|{|}|(|)|!";

    public static final HashSet<String> ckeyWordSet = new HashSet<String>();
    public static final HashSet<String> symbolWordSet = new HashSet<String>();
    public CwordTest() {
        String clist[] = CkeyWords.split("\\|");
        String symbollist[] = Symbol.split("\\|");
        for (String keyword : clist) {
            ckeyWordSet.add(keyword);
        }
        for (String symbol : symbollist) {
            symbolWordSet.add(symbol);
        }
    }

    @Test
    public void cwordTest() {
        String file1 = "/Users/huahuadepro/源代码相似性检测/源码/AllSubmitC/1.txt";
        String file2 = "/Users/huahuadepro/源代码相似性检测/源码/AllSubmitC/2.txt";
        // 预处理：删除注释、空格、换行
        ArrayList<String> codeArray = DelComments.clearCommentandBlank(file1);
        ArrayList<String> codeArray2 = DelComments.clearCommentandBlank(file2);
//        System.out.println(codeArray);
        // 统计关键字个数（特征向量提取）
        Map<String, Integer> ckeyWordMap = KeyStatistic.keyStatistic(codeArray, ckeyWordSet, symbolWordSet);
        Map<String, Integer> ckeyWordMap2 = KeyStatistic.keyStatistic(codeArray2, ckeyWordSet, symbolWordSet);
//        System.out.println(JsonOutput.prettyPrint(groovy.json.JsonOutput.toJson(ckeyWordMap)));
        // 相似度
        double sim = GetVectorDistence.getVectorDistence(ckeyWordMap, ckeyWordMap2);
        System.out.println("相似度" + ":" + sim + "%");
    }
}
