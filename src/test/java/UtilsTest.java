import com.zhuge.learn.dsaa.datastructure.heap.BinaryHeap;
import com.zhuge.learn.dsaa.datastructure.stack.Utils;
import com.zhuge.learn.dsaa.datastructure.tree.AVLTree;
import com.zhuge.learn.dsaa.datastructure.tree.BinaryFindTree;
import com.zhuge.learn.dsaa.datastructure.tree.RedBlackTree;
import org.junit.Test;

import java.util.Arrays;

public class UtilsTest {

    @Test
    public void stackTest() {
        String mediafix = "a+b*c-d/e+f*(g-height)-i";
        String postfix = Utils.media2Post(mediafix);
        System.out.println(postfix);
    }

    @Test
    public void findTreeTest() {
        Integer[] nums = {3, 4, 5, 6, 8, 78, 323, 2, 4, 3, 4, 3, 35, 3, 5, 45, 7, 8, 9, 76, 80, 14, 32, 5, 6, 47, 45};
        BinaryFindTree<Integer> findTree = com.zhuge.learn.dsaa.datastructure.tree.Utils.buildFindTree(nums);
        BinaryFindTree<Integer> node = findTree.find(323);
        BinaryFindTree<Integer> node2 = findTree.find(9999);
        System.out.println(findTree.mediaTraversal());
    }

    @Test
    public void avlTreeTest() {
        Integer[] nums = {3, 4, 5, 6, 8, 78, 323, 2, 4, 3, 4, 3, 35, 3, 5, 45, 7, 8, 9, 76, 80, 14, 32, 5, 6, 47, 45};
        AVLTree<Integer> avlTree = com.zhuge.learn.dsaa.datastructure.tree.Utils.buildAVLTree(nums);
        AVLTree<Integer> node = avlTree.find(323);
        AVLTree<Integer> node2 = avlTree.find(9999);
        System.out.println(avlTree.mediaTraversal());
    }

    @Test
    public void heapTest() {
        Integer[] nums = {3, 4, 5, 6, 8, 78, 323, 2, 4, 3, 4, 3, 35, 3, 5, 45, 7, 8, 9, 76, 80, 14, 32, 5, 6, 47, 45};
        //BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Arrays.copyOfRange(nums, 0, 5));
        BinaryHeap<Integer> heap = new BinaryHeap<Integer>(Arrays.copyOfRange(nums, 0, 5), 30);
        for (int i = 5; i < nums.length; i++) {
            heap.insert(nums[i]);
        }
        for (; ; ) {
            Integer heapTop = heap.deleteMin();
            if (null == heapTop) {
                break;
            }
            System.out.print(heapTop + " ");
        }
    }

    @Test
    public void rbTreeTest() {
        Integer[] nums = {3, 4, 5, 6, 8, 78, 323, 2, 4, 3, 0, 1, 35, 3, 5, 45, 7, 8, 9, 76, 80, 14, 32, 5, 6, 47, 45};
        RedBlackTree<Integer> tree = new RedBlackTree<>();
        for (Integer i : nums) {
            tree.insert(i);
        }
        tree.middleTraversal(i -> System.out.print(" " + i));
    }
}
