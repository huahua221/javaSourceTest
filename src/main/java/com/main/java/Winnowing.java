package com.main.java;

import com.google.common.base.Splitter;
import com.google.common.hash.Hasher;
import com.google.common.hash.Hashing;
import org.apache.commons.collections4.MapUtils;

import java.nio.charset.Charset;
import java.text.DecimalFormat;
import java.util.*;

public class Winnowing {
    // 指纹提取算法（是基于KR贪心算法的）
    // 基于 k-gram 的哈希码，以最小规则提取部分gram 的哈希码作为文档特征指纹，并记录gram的位置
    // 一个长度为n的字符串，当将它分成k-grams后，将会形成n-k+1个子串，且每个子串长度为k
    // 如果将所有哈希值作为文件的指纹用于比较，显然是不高效的，也没有那个必要，所以只需选取哈希值的一个子集最为文件指纹即可。
    // 一种方法是选取所有满足0 mod p（模p余0）的哈希值
    // 可以定义一个大小为w的窗口（w值自定义）来分割哈希值，窗口内的内容也是“高度邻接”的
    // 长度为w的窗口实际上对应了原文中长度t=k+w-1的子串
    // 哈希值是JDK根据对象的地址或者字符串或者数字算出来的int类型的数值

    // 在经典的MOSS系统中，对生成的文本指纹进行公共子串匹配，在上传的代码集中寻找匹配程度最高的代码文本作为匹配文本
    // LCS??
    // 或者直接通过相同指纹的计数公式来计算

    /**
     * 子串匹配至少与噪声阈值一样长，才能被检测到（用于过滤）
     */
    public static int minDetectedLength = 0;
    /**
     * 滑动窗口的大小
     */
    public static int windowSize;

    /**
     * 初始化参数，滑动窗口大小 = minDetectedLength - noiseThreshold + 1
     *
     * @param minDetectedLength 子串能被监测到的最短长度
     * @param noiseThreshold 噪声阈值，不检测比这个值小的匹配
     */
    public Winnowing(int minDetectedLength, int noiseThreshold) {
        this.minDetectedLength = minDetectedLength;
        if (noiseThreshold > minDetectedLength) {
            System.out.println("噪声阈值不能大于最小匹配保证阈值！");
        }
        this.windowSize = minDetectedLength - noiseThreshold + 1;
    }

    /**
     * 用Winnowing(8, 4)初始化
     */
    public Winnowing() {
        this(8, 4);
    }

    /**
     * ----计算用空格分割的单词组成的N-Grams的数字指纹----
     */
    public Set<Integer> winnowUsingWords(String text) {
        List<Integer> nh = getHashesForNGramsOfWords(text, " ");
        return buildFingerprintSet(nh);
    }

    // 先使用给定的分隔符对给定文本进行标记，以获取单词列表。
    // 然后计算每个由单词组成的N-Grams/shingle的哈希值，存入一个列表并返回
    public List<Integer> getHashesForNGramsOfWords(String text, String delimiter) {
        // 基于分隔符delimiter对文本text进行划分并移除结果中的空格（trimResults方法）和空字符串（omitEmptyStrings方法）
        Iterator<String> tok = Splitter.on(delimiter).trimResults()
                .omitEmptyStrings().split(text).iterator();
        List<Integer> n_grams = new ArrayList<>();
        List<String> list = new ArrayList<>();
        while (tok.hasNext()) {
            list.add(tok.next());
            if (list.size() == this.minDetectedLength) {
                n_grams.add(getHash(String.join(" ", list)));
                list.remove(0);
            }
        }
        /* 当tokens比minDetectedLength短 */
        if (n_grams.isEmpty() && list.size() > 0) {
            n_grams.add(getHash(String.join(" ", list)));
        }
        return n_grams;
    }

    /**
     * ----计算由字符组成的N-Grams的数字指纹. 预处理：所以字母变为小写且去除空格----
     */
    public Set<Integer> winnowUsingCharacters(String text) {
        text = pretreatment(text);//预处理
//        System.out.println("预处理后：" + text);
        List<Integer> nh = getHashesForNGramsOfChars(text);
        return buildFingerprintSet(nh);
    }

    /**
     * ----通过文件计算由字符组成的N-Grams的数字指纹. 预处理：所以字母变为小写且去除空格----
     */
    public Set<Integer> winnowUsingCharactersFile(ArrayList<String> codeArray) {
        Set<Integer> setstring = new TreeSet<>();
        Set<Integer> setstringall = new TreeSet<>();
        try {
            for (String text : codeArray) {
                // 对array中的每一个string进行指纹提取
                text = pretreatment(text);//预处理
//                System.out.println("预处理后：" + text);
                List<Integer> nh = getHashesForNGramsOfChars(text);
                setstring = buildFingerprintSet(nh);
//                System.out.println("指纹分项：" + setstring);
                // 将所有的指纹set合并
                setstringall.addAll(setstring);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
//        System.out.println("合并：" + setstringall);
        return setstringall;
    }

    //预处理
    public String pretreatment(String text) {
        String textWithoutPunctuation = text.replaceAll("[\\pP+~$`^=|<>～｀＄＾＋＝｜＜＞￥×]", "");//去除标点符号
        return textWithoutPunctuation.replaceAll("\\s+", "").toLowerCase();// 移除空白字符并将大写字母换成小写字母
    }

    // 计算每个N-Grams（由输入文本中的字符组成）的哈希值，每个N-Grams的大小为minDetectedLength
    public List<Integer> getHashesForNGramsOfChars(String text) {
        List<Integer> hashes = new ArrayList<Integer>();
        if (text.length() < this.minDetectedLength) {
            int h = getHash(text);
//            int h = text.hashCode();
            hashes.add(h);
        } else {
            for (int i = 0; i < text.length() - this.minDetectedLength + 1; i++) {
                String subtext = text.substring(i, i + this.minDetectedLength);
                hashes.add(getHash(subtext));
//                System.out.println("子串：" + subtext + " " + getHash(subtext));
            }
        }
        return hashes;
    }

    /**
     * MD5哈希函数（可用其他hash函数替换）
     */
    // md5都是128bits
    @SuppressWarnings("UnstableApiUsage")
    public int getHash(String token) {
        Hasher hasher = Hashing.md5().newHasher();
        hasher.putString(token, Charset.defaultCharset());
        int h = hasher.hash().asInt();
        return Math.abs(h % 10000);//返回哈希值取余10000后（mod 10000）的绝对值
    }

    // 根据窗口大小提取最小的指纹，并按照牲畜排序
    public Set<Integer> buildFingerprintSet(List<Integer> nHash) {
        Set<Integer> fp = new TreeSet<Integer>();
        List<Map<String, Object>> winlist = new ArrayList<>();
        int winindex = 0; // winlist自己的下标
        for (int i = 0; i < nHash.size() - this.windowSize + 1; i++) {
            List<Integer> s = new ArrayList<Integer>(nHash.subList(i, i + this.windowSize));
            Integer min = Collections.min(s);
            fp.add(min);
            Map<String, Object> winmap = new HashMap<>();
            String fin = Integer.toString(min);
            if (winindex != 0) {
                Map<String, Object> lastmap = winlist.get(winindex - 1);
                String lastfin = MapUtils.getString(lastmap, "finger");
                if (lastfin.equals(fin)) {
                    continue;
                }
            }
            winmap.put("finger", fin);
            winmap.put("position", i + s.indexOf(min));
            winlist.add(winmap);
            winindex++;
        }
//        for (Integer s : fp) {
//            Map<String, Object> winmap = new HashMap<>();
//            String fin = Integer.toString(s);
//            winmap.put("finger", fin);
//            winmap.put("position", nHash.indexOf(s));
//            winlist.add(winmap);
//        }
//        System.out.println("列表：" + winlist);
        return fp;
    }

    /**
     * 返回当前使用的winnowing参数值(minDetectedLength、windowSize)
     */
    public HashMap getParams() {
        HashMap<String, Integer> params = new HashMap<String, Integer>();
        params.put("minDetectedLength", this.minDetectedLength);
        params.put("windowSize", this.windowSize);
        return params;
    }

    /**
     * 根据提取出的指纹，通过公式直接计算相似度
     */
    public String WinSimCalculator(Set<Integer> fingertsetA, Set<Integer> fingertsetB) {
        // 公式：sim(A,B)=2*(A,B交集)/（A,B各自的指纹数相加）
        System.out.println("fingertsetA:" + fingertsetA);
        System.out.println("fingertsetB:" + fingertsetB);
        int countA = fingertsetA.size();
        int countB = fingertsetB.size();
        Set<Integer> intersectionSet = new HashSet<>();
        intersectionSet.addAll(fingertsetA);
        intersectionSet.retainAll(fingertsetB);
        int countInter = intersectionSet.size();
        DecimalFormat df = new DecimalFormat("0.00000");//设置保留位数
//        System.out.println((countA+countB));
        return df.format((2 * (float) countInter / ((float) countA + (float) countB)));
    }

}

