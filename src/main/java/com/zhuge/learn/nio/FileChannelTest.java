package com.zhuge.learn.nio;

import java.io.RandomAccessFile;
import java.nio.channels.FileChannel;

/**
 * @Title: FileChannelTest
 * @Description:
 * @author: zhuge
 * @date: 2019/3/15 19:34
 */
public class FileChannelTest {
    public static void main(String[] args) throws Exception {
        String path = "E:\\fileChannelTest.txt";
        RandomAccessFile randomAccessFile = new RandomAccessFile(path, "r");
        FileChannel fileChannel = randomAccessFile.getChannel();
        //String toWrite = "bablalalabas\n";
        //ByteBuffer buffer = ByteBuffer.wrap(toWrite.getBytes(StandardCharsets.UTF_8));
        //fileChannel.position(fileChannel.size());
        //fileChannel.write(buffer);
        //fileChannel.position(0);
        //ByteBuffer read = ByteBuffer.allocate(1024);
        //fileChannel.read(read);
        //read.flip();
        //System.out.println(new String(read.array(), read.position(), read.limit(), StandardCharsets.UTF_8));
        //

        String path2 = "E:\\fileChannelTest2.txt";
        RandomAccessFile randomAccessFile1 = new RandomAccessFile(path2, "rw");
        FileChannel channel = randomAccessFile1.getChannel();
        channel.position(channel.size());
        fileChannel.transferTo(0, fileChannel.size(), channel);
        channel.close();
        fileChannel.close();
    }
}
