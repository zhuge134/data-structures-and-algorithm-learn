import java.io.PipedInputStream;
import java.io.PipedOutputStream;

/**
 * @Title: PipeIOStreamTest
 * @Description:
 * @author: zhuge
 * @date: 2019/3/5 23:05
 */
public class PipeIOStreamTest {
    public static void main(String[] args) throws Exception{
        PipedInputStream is = new PipedInputStream();
        PipedOutputStream os = new PipedOutputStream(is);

    }
}
