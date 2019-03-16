import java.util.concurrent.*;

/**
 * @Title: SemaphoreTest
 * @Description:
 * @author: zhuge
 * @date: 2019/3/9 21:31
 */
public class SemaphoreTest {
    public static void main(String[] args) {
        ExecutorService executorService = new ThreadPoolExecutor(300, 300, 0, TimeUnit.SECONDS,
                new LinkedBlockingQueue<>());
        int threadCount = 600;
        Semaphore semaphore = new Semaphore(200);
        for (int i = 0; i < threadCount; i++) {
            executorService.execute(() -> {
                try {
                    semaphore.acquire();
                    System.out.println("thread: " + Thread.currentThread().getName() + " has acquire one semaphore!");
                    Thread.sleep(1000);
                    semaphore.release();
                    System.out.println("thread: " + Thread.currentThread().getName() + " has release one semaphore!");
                } catch (Exception e) {
                    e.printStackTrace();
                }
            });
        }
        executorService.shutdown();
    }
}
