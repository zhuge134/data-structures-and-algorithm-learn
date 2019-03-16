import java.util.concurrent.atomic.AtomicIntegerArray;
import java.util.concurrent.atomic.AtomicIntegerFieldUpdater;
import java.util.concurrent.atomic.AtomicStampedReference;

/**
 * @Title: AtomicTest
 * @Description:
 * @author: zhuge
 * @date: 2019/3/8 23:41
 */
public class AtomicTest {
    public static void main(String[] args) {
        int[] nums = {1, 3, 3, 4, 34, 5};
        AtomicIntegerArray atomicIntegerArray = new AtomicIntegerArray(nums);
        System.out.println(String.format("value of index 0 before update: %d", atomicIntegerArray.get(0)));
        System.out.println(String.format("value of index 0 before update: %d", atomicIntegerArray.addAndGet(0, 1)));

        A a = new A();
        AtomicIntegerFieldUpdater integerFieldUpdater = AtomicIntegerFieldUpdater.newUpdater(A.class, "a");
        integerFieldUpdater.set(a, 0);
        boolean updateSuccess = integerFieldUpdater.compareAndSet(a, 0, 10);
        System.out.println("update success: " + updateSuccess);

        AtomicStampedReference<A> stampedReference = new AtomicStampedReference<>(null, 1);
        System.out.println(stampedReference.attemptStamp(a, 2));
        System.out.println(stampedReference.attemptStamp(null, 2));
        System.out.println(stampedReference.compareAndSet(null, a, 2, 3));
        System.out.println(stampedReference.getStamp());

    }

    static class A {
        public volatile int a;

        public A() {
        }
    }
}
