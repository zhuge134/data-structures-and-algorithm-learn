import com.zhuge.learn.dsaa.algorithm.sort.Sorts;
import org.junit.Test;

import java.util.Arrays;

/**
 * @Title: SortsTest
 * @Description:
 * @author: zhuge
 * @date: 2019/2/23 20:59
 */
public class SortsTest {

    @Test
    public void insertSortTest() {
        Integer[] nums = {555, 40, 5, 8, 6, 78, 323, 2, 4, 3, 4, 3, 35, 3, 5, 45, 7, 8, 9, 76, 80, 14, 32, 5, 6, 47, 45};
        Integer[] tmp=new Integer[30];
        Sorts.mergeSort(nums,2,200,null,tmp);
        Arrays.stream(nums).forEach(i->System.out.print(i+" "));
    }
}
