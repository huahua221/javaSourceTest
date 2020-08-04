package com.main.java;

import org.junit.Test;

public class Winnowing {
    // 指纹提取算法
    // 基于 k-gram 的哈希码，以最小规则提取部分gram 的哈希码作为文档特征指纹，并记录gram的位置
    // 一个长度为n的字符串，当将它分成k-grams后，将会形成n-k+1个子串，且每个子串长度为k
    // 如果将所有哈希值作为文件的指纹用于比较，显然是不高效的，也没有那个必要，所以只需选取哈希值的一个子集最为文件指纹即可。
    // 一种方法是选取所有满足0 mod p（模p余0）的哈希值
    // 可以定义一个大小为w的窗口（w值自定义）来分割哈希值，窗口内的内容也是“高度邻接”的
    // 长度为w的窗口实际上对应了原文中长度t=k+w-1的子串
    // 哈希值是JDK根据对象的地址或者字符串或者数字算出来的int类型的数值

    /**
     * 指纹提取
     *
     * @param window 滑动窗口大小
     * @return
     */
    public static void winnow(int window) {

    }

    @Test
    public void winnowTest() {
        String a = "adoru";
        System.out.println("哈希值" + a.hashCode());
    }
}

