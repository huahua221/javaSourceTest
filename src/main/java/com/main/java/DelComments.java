package com.main.java;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.ArrayList;

// 删除注释、换行以及空格
public class DelComments {
    // java中一共有三种类型的注释：//,/* */..多行注释
    private static int count = 0; // 用于计数已经删除了注释的文件个数

    /**
     * 仅删除文件中的各种注释，包含//、/* * /等
     * @param charset 文件编码
     * @param file    文件
     */
    public static ArrayList<String> clearCommentOnly(File file, String charset) {
        String s = "";
        ArrayList<String> strArray = new ArrayList<String>();
        try {
            //递归处理文件夹
            if (!file.exists()) {
                return strArray;
            }
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    strArray.addAll(clearCommentOnly(f, charset));
                }
                return strArray;
            } else if (!file.getName().endsWith(".java")) {
                //非java文件直接返回
                return strArray;
            }

            //根据对应的编码格式读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            StringBuffer content = new StringBuffer();
            String tmp = null;
            while ((tmp = reader.readLine()) != null) {
                content.append(tmp);
                content.append("\n");
            }
            String target = content.toString();
            s = target.replaceAll("\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*+\\/", "");
            strArray.add(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strArray;
    }

    /**
     * 删除文件中的各种注释、空格、空行等
     * @param charset 文件编码
     * @param file    文件
     */
    public static ArrayList<String> clearCommentandBlank(File file, String charset) {
        String s = "";
        ArrayList<String> strArray = new ArrayList<String>();
        try {
            //递归处理文件夹
            if (!file.exists()) {
                return strArray;
            }
            if (file.isDirectory()) {
                File[] files = file.listFiles();
                for (File f : files) {
                    strArray.addAll(clearCommentandBlank(f, charset));
                }
                return strArray;
            } else if (!file.getName().endsWith(".java") && !file.getName().endsWith(".txt")) {
                //非java或者c文件直接返回
                return strArray;
            }

            //根据对应的编码格式读取
            BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), charset));
            StringBuffer content = new StringBuffer();
            String tmp = null;
            while ((tmp = reader.readLine()) != null) {
                content.append(tmp);
                content.append("\n");
            }
            String target = content.toString();
            s = target.replaceAll("\\/\\/[^\\n]*|\\/\\*([^\\*^\\/]*|[\\*^\\/*]*|[^\\**\\/]*)*\\*+\\/", "");

            // 删除所有的空格和换行
            s = s.replaceAll("\\s", "");
            strArray.add(s);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return strArray;
    }


    public static ArrayList<String> clearCommentOnly(String filePath, String charset) {
        return clearCommentOnly(new File(filePath), charset);
    }

    public static ArrayList<String> clearCommentOnly(String filePath) {
        return clearCommentOnly(new File(filePath), "UTF-8");
    }

    public static ArrayList<String> clearCommentOnly(File file) {
        return clearCommentOnly(file, "UTF-8");
    }

    public static ArrayList<String> clearCommentandBlank(String filePath, String charset) {
        return clearCommentandBlank(new File(filePath), charset);
    }

    public static ArrayList<String> clearCommentandBlank(String filePath) {
        return clearCommentandBlank(new File(filePath), "UTF-8");
    }

    public static ArrayList<String> clearCommentandBlank(File file) {
        return clearCommentandBlank(file, "UTF-8");
    }

}
