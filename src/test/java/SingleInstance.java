/**
 * @Title: SingleInstance
 * @Description:
 * @author: zhuge
 * @date: 2019/3/8 19:31
 */
public class SingleInstance {
    private static volatile SingleInstance instance;
    private static final Object INSTANCE_LOCK = new Object();

    public static SingleInstance getInstance() {
        if (null == instance) {
            synchronized (INSTANCE_LOCK) {
                if (null == instance) {
                    instance = new SingleInstance();
                }
            }
        }
        return instance;
    }

    private SingleInstance() {
    }
}
