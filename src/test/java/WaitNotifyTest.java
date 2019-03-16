/**
 * @Title: WaitNotifyTest
 * @Description:
 * @author: zhuge
 * @date: 2019/3/5 22:46
 */
public class WaitNotifyTest {

    static class ThreadA extends Thread {
        private Object lock;

        public ThreadA(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            try {
                synchronized (lock) {
                    System.out.println("threadA: " + currentThread().getName() + "begin wait, time: " + System.currentTimeMillis());
                    lock.wait();
                    System.out.println("threadA: " + currentThread().getName() + "end wait, time: " + System.currentTimeMillis());
                }
            } catch (InterruptedException e) {
                System.out.println("threadA is interrupted !");
                e.printStackTrace();
            }
        }
    }

    static class ThreadB extends Thread {
        private Object lock;

        public ThreadB(Object lock) {
            this.lock = lock;
        }

        @Override
        public void run() {
            synchronized (lock) {
                System.out.println("threadB: " + currentThread().getName() + "begin notify, time: " + System.currentTimeMillis());
                lock.notifyAll();
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("threadB: " + currentThread().getName() + "end notify, time: " + System.currentTimeMillis());
            }
        }
    }

    public static void main(String[] args) throws Exception {
        Object lock = new Object();
        Thread ta = new ThreadA(lock);
        Thread tb = new ThreadB(lock);
        ta.start();
        Thread.sleep(200);
        tb.setDaemon(true);
        tb.start();
        ta.interrupt();
        tb.join(1000);
    }
}

