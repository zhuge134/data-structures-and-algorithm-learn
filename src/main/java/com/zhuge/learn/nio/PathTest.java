package com.zhuge.learn.nio;

import org.junit.Test;

import java.io.File;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

/**
 * @Title: PathTest
 * @Description:
 * @author: zhuge
 * @date: 2019/3/15 23:34
 */
public class PathTest {

    @Test
    public void test() throws Exception {
        Path path = Paths.get("E:\\fileChannelTest.txt");
        File file = new File("E:\\fileChannelTest.txt");
        System.out.println("文件名：" + path.getFileName());
        System.out.println("名称元素的数量：" + path.getNameCount());
        System.out.println("父路径：" + path.getParent());
        System.out.println("根路径：" + path.getRoot());
        System.out.println("是否是绝对路径：" + path.isAbsolute());
        //startsWith()方法的参数既可以是字符串也可以是Path对象
        System.out.println("是否是以为给定的路径D:开始：" + path.startsWith("D:\\"));
        System.out.println("该路径的字符串形式：" + path.toString());

        Path path1 = Paths.get(".");
        System.out.println("当前目录：" + path1.toAbsolutePath());

        System.out.println(Files.exists(path, LinkOption.NOFOLLOW_LINKS));

        String newFile = "E:\\test\\test\\test.txt";
        String dir = "E:\\test\\test";
        Path newPath = Paths.get(newFile);
        Path newDir = Paths.get(dir);
        //Files.createDirectory(newDir);
        Files.createDirectories(newDir);
        Files.delete(newDir);
    }
}
