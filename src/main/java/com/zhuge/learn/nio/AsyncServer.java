package com.zhuge.learn.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.AsynchronousServerSocketChannel;
import java.nio.channels.AsynchronousSocketChannel;
import java.nio.channels.CompletionHandler;
import java.nio.charset.StandardCharsets;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.atomic.AtomicReference;

/**
 * @Title: AsyncServer
 * @Description:
 * @author: zhuge
 * @date: 2019/3/16 22:29
 */
public class AsyncServer {
    private static final Logger log = LoggerFactory.getLogger(AsyncServer.class);

    public static void main(String[] args) throws IOException {
        AsynchronousServerSocketChannel serverSocketChannel = AsynchronousServerSocketChannel.open();
        serverSocketChannel.bind(new InetSocketAddress("127.0.0.1", 11201), 1204);
        new Thread() {
            @Override
            public void run() {
                while (true) {
                    Future<AsynchronousSocketChannel> future = serverSocketChannel.accept();
                    AtomicReference<AsynchronousSocketChannel> socketChannel = new AtomicReference<>();
                    try {
                        socketChannel.set(future.get());
                    } catch (InterruptedException | ExecutionException e) {
                        log.error("", e);
                    }
                    ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                    socketChannel.get().read(byteBuffer, (Object) null, new CompletionHandler<Integer, Object>() {
                        @Override
                        public void completed(Integer readNum, Object attachment) {
                            if (readNum > 0) {
                                byteBuffer.flip();
                                byte[] bytes = new byte[byteBuffer.remaining()];
                                byteBuffer.get(bytes);
                                String msg = new String(bytes, StandardCharsets.UTF_8);
                                log.info("服务端收到消息：" + msg);
                                String resp = "服务端收到消息：" + msg;
                                socketChannel.get().write(ByteBuffer.wrap(resp.getBytes(StandardCharsets.UTF_8)), null, new CompletionHandler<Integer, Object>() {
                                    @Override
                                    public void completed(Integer writeNum, Object attachment) {
                                        log.info("服务端完成相应数据发送！");
                                    }

                                    @Override
                                    public void failed(Throwable exc, Object attachment) {

                                        log.error("服务端处理写数据失败：", exc);
                                    }
                                });
                            }
                        }

                        @Override
                        public void failed(Throwable exc, Object attachment) {
                            log.error("服务端处理读数据失败：", exc);
                        }
                    });
                }
            }
        }.start();
    }

}
