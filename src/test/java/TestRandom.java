import java.util.Random;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-18 下午4:32
 */
public class TestRandom {
    public static void main(String[] args) {
        Random r = new Random();
        for (int i = 0; i < 1000; i++) {
            System.out.println(r.nextLong());
        }
    }
}
