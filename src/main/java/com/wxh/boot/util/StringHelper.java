package com.wxh.boot.util;

import org.springframework.data.util.Pair;

import java.util.Arrays;
import java.util.List;

/**
 * @author wuxinhong
 * @date 2023/5/30 2:34 PM
 */
public class StringHelper {
    // convert a list to string with separator
    // remove the last separator
    // if ids is null or empty,return ""
    public static String listLong2String(List<Long> ids, char separator) {
        if (ids == null || ids.isEmpty()) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (Long id : ids) {
            sb.append(id).append(separator);
        }
        return sb.substring(0, sb.length() - 1);
    }



    // 使用shell命令读取文件
//    public static String readDataByShell(String filePath) {
//        String[] cmd = new String[]{"/bin/sh", "-c", "cat " + filePath};
//        return ShellHelper.runShell(cmd).getSecond();
//    }

    /**
     * 使用shell命令合并文件
     * 校验filePath1是否存在，不存在则返回
     * 校验filePath2是否存在，不存在则创建
     * 将filePath1的内容追加到filePath2
     * 返回合并后文件内容的行数
     * @param filePath1
     * @param filePath2
     * @return
     */
//    public static Integer mergeFile(String filePath1, String filePath2) {
//        String[] cmd = new String[]{"/bin/sh", "-c", "cat " + filePath1 + " >> " + filePath2};
//        Pair<Boolean, String> result = ShellHelper.runShell(cmd);
//        if (result.getFirst()) {
//            String[] cmd2 = new String[]{"/bin/sh", "-c", "wc -l " + filePath2};
//            String s = ShellHelper.runShell(cmd2);
//            return Integer.valueOf(s.split(" ")[0]);
//        }
//        return 0;
//    }

//    public static void main(String[] args) {
//        String s = readDataByShell("/Users/wuxinhong/1.txt");
//        System.out.println(s);
//    }
}
