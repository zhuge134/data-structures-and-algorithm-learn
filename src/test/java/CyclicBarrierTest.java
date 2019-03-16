import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * @Title: CyclicBarrierTest
 * @Description:
 * @author: zhuge
 * @date: 2019/3/9 22:14
 */
public class CyclicBarrierTest {
    public static void main(String[] args) {
        int threadCount = 500;
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger actionCount = new AtomicInteger(0);
        ExecutorService executorService = new ThreadPoolExecutor(300, 300,
                0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>(), Executors.defaultThreadFactory(),
                new ThreadPoolExecutor.AbortPolicy());
        CyclicBarrier barrier = new CyclicBarrier(5, actionCount::incrementAndGet);
        barrier.reset();
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    Thread.sleep(1000);
                    System.out.println("thread: " + Thread.currentThread().getName() + " has arrived barrier !");
                    barrier.await();
                    System.out.println("thread: " + Thread.currentThread().getName() + " has finished !");
                } catch (Exception e) {
                    e.printStackTrace();
                } finally {
                    latch.countDown();
                }
            });
        }
        executorService.shutdown();
        try {
            latch.await();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("action count: " + actionCount.get());
    }
}
