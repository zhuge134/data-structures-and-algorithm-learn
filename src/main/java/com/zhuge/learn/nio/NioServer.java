package com.zhuge.learn.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Title: NioServer
 * @Description:
 * @author: zhuge
 * @date: 2019/3/16 18:44
 */
public class NioServer implements AutoCloseable {
    public static void main(String[] args) {
        try {
            new NioServer("127.0.0.1").start();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final Logger log = LoggerFactory.getLogger(NioServer.class);

    private static final int DEFAULT_PORT = 11201;

    private Selector selector;
    private ServerSocketChannel serverSocketChannel;
    private String ip;
    private int port;
    private volatile boolean running;
    private Object startLock = new Object();

    private Thread loopThread;
    private ExecutorService eventHandleThreadPool;

    public NioServer(String ip) {
        this(ip, DEFAULT_PORT);
    }

    public NioServer(String ip, int port) {
        this.ip = ip;
        this.port = port;
    }

    public void start() throws IOException {
        if (!running) {
            synchronized (startLock) {
                if (!running) {
                    running = true;
                    selector = Selector.open();
                    serverSocketChannel = ServerSocketChannel.open();
                    serverSocketChannel.bind(new InetSocketAddress(ip, port), 1024);
                    serverSocketChannel.configureBlocking(false);
                    serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
                    eventHandleThreadPool = new ThreadPoolExecutor(50, 100, 60L, TimeUnit.SECONDS,
                            new ArrayBlockingQueue<>(100), new ThreadFactory() {
                        private AtomicInteger seq = new AtomicInteger(0);

                        @Override
                        public Thread newThread(Runnable r) {
                            Thread t = new Thread();
                            t.setDaemon(false);
                            t.setName("server-event-handle-thread-" + seq.getAndIncrement());
                            return t;
                        }
                    });
                    loopThread = new Thread(new EventLoop());
                    loopThread.setName("server-event-loop-thread");
                    loopThread.start();
                    log.info("server started !");
                    return;
                }
            }
        }
        log.warn("Server has already started !");
    }

    @Override
    public void close() throws Exception {
        if (running) {
            running = false;
            loopThread.interrupt();
            eventHandleThreadPool.shutdownNow();
            selector.close();
            serverSocketChannel.close();
        }
    }

    private class EventLoop implements Runnable {

        @Override
        public void run() {
            log.info("server loop thread started !");
            while (running) {
                int keyNum = 0;
                try {
                    keyNum = selector.select();
                } catch (IOException e) {
                    log.error("服务端异常！", e);
                }
                if (keyNum > 0) {
                    Set<SelectionKey> keys = selector.selectedKeys();
                    for (SelectionKey key : keys) {
                        if (key.isValid()) {
                            if (key.isAcceptable()) {
                                try {
                                    ServerSocketChannel serverSocketChannel = (ServerSocketChannel) key.channel();
                                    SocketChannel socketChannel = serverSocketChannel.accept();
                                    if (null != socketChannel) {
                                        socketChannel.configureBlocking(false);
                                        socketChannel.register(selector, SelectionKey.OP_READ);
                                    }
                                } catch (Exception e) {
                                    log.error("服务端处理接收新连接事件失败 ", e);
                                }
                            } else if (key.isReadable()) {
                                //将数据读出
                                try {
                                    SocketChannel socketChannel = (SocketChannel) key.channel();
                                    if (socketChannel.isConnected()) {
                                        ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                                        socketChannel.read(readBuffer);
                                        readBuffer.flip();
                                        byte[] bytes = new byte[readBuffer.remaining()];
                                        readBuffer.get(bytes);
                                        String msg = new String(bytes, StandardCharsets.UTF_8);
                                        String response = "服务端收到消息： " + msg;
                                        log.info(response);
                                        key.interestOps(SelectionKey.OP_WRITE);
                                        key.attach(response);
                                    } else {
                                        log.info("远程客户端关闭了连接");
                                        socketChannel.close();
                                        //不需要额外操作
                                        //key.cancel();
                                    }
                                } catch (Exception e) {
                                    log.error("服务端处理读事件错误", e);
                                }
                                //调用回调方法进行处理
                            } else if (key.isWritable()) {
                                try {
                                    SocketChannel socketChannel = (SocketChannel) key.channel();
                                    if (socketChannel.isConnected()) {
                                        Object attach = key.attachment();
                                        if (null != attach) {
                                            socketChannel.write(ByteBuffer.wrap(attach.toString().getBytes(StandardCharsets.UTF_8)));
                                        }
                                        key.interestOps(SelectionKey.OP_READ);
                                    } else {
                                        log.info("远程客户端关闭了连接");
                                        socketChannel.close();
                                        //通道关闭后自动取消
                                        //key.cancel();
                                    }
                                } catch (Exception e) {
                                    log.error("服务端处理写事件失败", e);
                                }
                            } else if (key.isConnectable()) {
                                log.warn("服务端暂不支持connect事件处理！");
                            }
                        }
                    }
                }
            }
        }
    }

}
