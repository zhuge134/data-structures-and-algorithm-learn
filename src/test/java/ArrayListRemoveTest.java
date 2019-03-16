import org.junit.Test;

import java.util.ArrayList;

/**
 * @Title: ArrayListRemoveTest
 * @Description:
 * @author: zhuge
 * @date: 2019/3/2 23:59
 */
public class ArrayListRemoveTest {

    @Test
    public void addTest() {
        ArrayList<Integer> list1 = new ArrayList<>();
        int n = 10000000;
        long start1 = System.currentTimeMillis();
        for (int i = 0; i < n; i++) {
            list1.add(i);
        }
        System.out.println("list1 add cost: " + (System.currentTimeMillis() - start1));
        ArrayList<Integer> list2 = new ArrayList<>();
        long start2 = System.currentTimeMillis();
        list2.ensureCapacity(n);
        for (int i = 0; i < n; i++) {
            list2.add(i);
        }
        System.out.println("list2 add cost: " + (System.currentTimeMillis() - start2));

    }

    @Test
    public void removeTest() {
        ArrayList<Equals> list = new ArrayList<>();
        list.add(new Equals("a"));
        list.add(new Equals("b"));
        System.out.println(list);
        list.remove(new Equals("a"));
        System.out.println(list);
    }

    static class Equals {
        private String s;

        @Override
        public String toString() {
            return "Equals{" +
                    "s='" + s + '\'' +
                    '}';
        }

        public Equals(String s) {
            this.s = s;
        }

        @Override
        public boolean equals(Object obj) {
            if (null == obj) {
                return false;
            }
            if (!(obj instanceof Equals)) {
                return false;
            }
            return ((Equals) obj).s != null && ((Equals) obj).s.equals(this.s);
        }
    }
}
