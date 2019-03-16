package com.zhuge.learn.nio;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;
import java.util.Set;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Title: NioClient
 * @Description:
 * @author: zhuge
 * @date: 2019/3/16 18:44
 */
public class NioClient implements AutoCloseable {

    public static void main(String[] args) throws IOException {
        final NioClient client = new NioClient("127.0.0.1");
        try {
            client.start();
        } catch (IOException e) {
            log.error("客户端启动失败！", e);
        }
        ;
        ExecutorService executorService = new ThreadPoolExecutor(100, 100, 60L, TimeUnit.SECONDS,
                new ArrayBlockingQueue<>(100), new ThreadFactory() {
            private AtomicInteger seq = new AtomicInteger(0);

            @Override
            public Thread newThread(Runnable r) {
                Thread t = new Thread(r);
                t.setDaemon(false);
                t.setName("send-msg-thread-" + seq.getAndIncrement());
                return t;
            }
        });
        int count = 200;
        for (int i = 0; i < count; i++) {
            final int seq=i;
            client.send("客户端请求：" + seq);
            /*executorService.execute(() -> {
                try {
                    client.send("客户端请求：" + seq);
                } catch (Exception e) {
                    log.error("利用客户端发送消息失败！", e);
                }
            });*/
        }
        log.info("all send task has been submitted !");
        //executorService.shutdown();
    }

    private static final Logger log = LoggerFactory.getLogger(NioServer.class);

    private static final int DEFAULT_PORT = 11201;

    private Selector selector;
    private String serverIp;
    private int serverPort;
    private volatile boolean running;
    private Object startLock = new Object();

    private Thread loopThread;

    public NioClient(String serverIp) {
        this(serverIp, DEFAULT_PORT);
    }

    public NioClient(String serverIp, int serverPort) {
        this.serverIp = serverIp;
        this.serverPort = serverPort;
    }

    public void send(String msg) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_CONNECT, msg);
        socketChannel.connect(new InetSocketAddress(serverIp, serverPort));
    }

    public void start() throws IOException {
        if (!running) {
            synchronized (startLock) {
                if (!running) {
                    running = true;
                    selector = Selector.open();
                    loopThread = new Thread(new EventLoop());
                    loopThread.setName("client-event-loop-thread");
                    loopThread.start();
                    log.info("client started !");
                    return;
                }
            }
        }
        log.warn("Client has already started !");
    }

    @Override
    public void close() throws Exception {
        if (running) {
            running = false;
            loopThread.interrupt();
            selector.close();
        }
    }

    private class EventLoop implements Runnable {

        @Override
        public void run() {
            log.info("client loop thread started !");
            while (running) {
                int keyNum = 0;
                try {
                    keyNum = selector.select(100);
                } catch (IOException e) {
                    log.error("客户端异常！", e);
                }
                if (keyNum > 0) {
                    Set<SelectionKey> keys = selector.selectedKeys();
                    for (SelectionKey key : keys) {
                        if (key.isValid()) {
                            if (key.isConnectable()) {
                                try {
                                    SocketChannel socketChannel = (SocketChannel) key.channel();
                                    if (socketChannel.isConnectionPending()) {
                                        if (socketChannel.finishConnect()) {
                                            key.interestOps(SelectionKey.OP_WRITE);
                                        }
                                    } else {
                                        //没有发起过连接，无效socketchannel
                                        log.warn("socket channel has not been connect initiated !");
                                        socketChannel.close();
                                    }
                                } catch (Exception e) {
                                    log.error("客户端处理连接事件失败", e);
                                }
                            } else if (key.isReadable()) {
                                //将数据读出
                                try {
                                    SocketChannel socketChannel = (SocketChannel) key.channel();
                                    ByteBuffer readBuffer = ByteBuffer.allocate(1024);
                                    socketChannel.read(readBuffer);
                                    readBuffer.flip();
                                    byte[] bytes = new byte[readBuffer.remaining()];
                                    readBuffer.get(bytes);
                                    String msg = new String(bytes, StandardCharsets.UTF_8);
                                    log.info("客户端收到消息： " + msg);
                                    //完成与服务端交互，主动关闭连接
                                    socketChannel.close();
                                    //不需要额外处理
                                    key.cancel();
                                } catch (Exception e) {
                                    log.error("客户端处理读事件错误", e);
                                }
                                //调用回调方法进行处理
                            } else if (key.isWritable()) {
                                try {
                                    SocketChannel socketChannel = (SocketChannel) key.channel();
                                    //服务端不主动关闭连接，由客户端主动关闭，这里需要判断连接是否关闭
                                    //if (socketChannel.isConnected()) {
                                    Object attach = key.attachment();
                                    if (null != attach) {
                                        socketChannel.write(ByteBuffer.wrap(attach.toString().getBytes(StandardCharsets.UTF_8)));
                                        key.attach(null);
                                    }
                                    key.interestOps(SelectionKey.OP_READ);
                                } catch (Exception e) {
                                    log.error("客户端处理写事件失败", e);
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}
