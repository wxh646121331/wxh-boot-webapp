package com.wxh.boot.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileAppendExample {
    public static void main(String[] args) {
        String sourceFilePath = "/Users/wuxinhong/test/file01.txt";  // 原始文件路径
        String targetFilePath = "/Users/wuxinhong/test/file02.txt";  // 目标文件路径
        String countFilePath = "/Users/wuxinhong/test/file03.txt";    // 行数统计文件路径

        // 追加文件内容
        appendFile(sourceFilePath, targetFilePath);

        // 统计行数并保存到新文件中
        int lineCount = countLines(targetFilePath);
        saveLineCount(countFilePath, lineCount);
    }

    // 将一个文件的内容追加到另一个文件中
    private static void appendFile(String sourceFilePath, String targetFilePath) {
        try (
                BufferedReader reader = new BufferedReader(new FileReader(sourceFilePath));
                BufferedWriter writer = new BufferedWriter(new FileWriter(targetFilePath, true))
        ) {
            String line;
            while ((line = reader.readLine()) != null) {
                writer.write(line);
                writer.newLine();
            }
            System.out.println("内容已成功追加到目标文件中。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    // 统计文件的行数
    private static int countLines(String filePath) {
        int lineCount = 0;
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath))) {
            while (reader.readLine() != null) {
                lineCount++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lineCount;
    }

    // 将行数保存到文件中
    private static void saveLineCount(String countFilePath, int lineCount) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(countFilePath))) {
            writer.write(String.valueOf(lineCount));
            System.out.println("行数已保存到新文件中。");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
