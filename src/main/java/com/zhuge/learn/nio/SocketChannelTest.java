package com.zhuge.learn.nio;

import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.concurrent.CyclicBarrier;

/**
 * @Title: SocketChannelTest
 * @Description:
 * @author: zhuge
 * @date: 2019/3/15 20:15
 */
public class SocketChannelTest {
    public static void main(String[] args) throws Exception {
        final CyclicBarrier barrier = new CyclicBarrier(2);
        //创建一个服务端
        new Thread() {
            public void run() {
                try {
                    ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
                    serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 11201));
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                    socketChannel.read(readBuffer);
                    readBuffer.flip();
                    String response = "服务端已收到消息：" + new String(Arrays.copyOfRange(readBuffer.array(), readBuffer.position(), readBuffer.limit()), StandardCharsets.UTF_8);
                    System.out.println(response);
                    ByteBuffer respBuffer = ByteBuffer.wrap(response.getBytes(StandardCharsets.UTF_8));
                    socketChannel.write(respBuffer);
                    socketChannel.close();
                } catch (Exception e) {
                    System.out.println("服务端出错！");
                    e.printStackTrace();
                }
            }
        }.start();

        //创建客户端

        new Thread() {
            @Override
            public void run() {
                try {
                    SocketChannel socketChannel = SocketChannel.open();
                    socketChannel.connect(new InetSocketAddress("127.0.0.1", 11201));
                    String msg = "客户端请求消息";
                    ByteBuffer request = ByteBuffer.wrap(msg.getBytes(StandardCharsets.UTF_8));
                    socketChannel.write(request);
                    ByteBuffer readBuffer = ByteBuffer.allocate(256);
                    socketChannel.read(readBuffer);
                    readBuffer.flip();
                    System.out.println("收到服务端回应：" +
                            new String(Arrays.copyOfRange(readBuffer.array(), readBuffer.position(), readBuffer.limit()), StandardCharsets.UTF_8) + " 中间是响应");
                    socketChannel.close();
                    barrier.await();
                } catch (Exception e) {
                    System.out.println("客户端错误 ！");
                    e.printStackTrace();
                }
            }
        }.start();
        barrier.await();
    }
}
