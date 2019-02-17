import com.zhuge.learn.dsaa.datastructure.stack.Utils;
import com.zhuge.learn.dsaa.datastructure.tree.BinaryFindTree;
import org.junit.Test;

public class UtilsTest {

    @Test
    public void stackTest() {
        String mediafix = "a+b*c-d/e+f*(g-h)-i";
        String postfix = Utils.media2Post(mediafix);
        System.out.println(postfix);
    }

    @Test
    public void treeTest() {
        Integer[] nums = {3, 4, 5, 6, 8, 78, 323, 2, 4, 3, 4, 3, 35, 3, 5, 45, 7, 8, 9, 76, 80, 14, 32, 5, 6, 47, 45};
        BinaryFindTree<Integer> findTree = com.zhuge.learn.dsaa.datastructure.tree.Utils.buildTree(nums);
        BinaryFindTree<Integer> node = findTree.find(323);
        BinaryFindTree<Integer> node2 = findTree.find(9999);
        System.out.println(findTree.mediaTraversal());
    }
}
