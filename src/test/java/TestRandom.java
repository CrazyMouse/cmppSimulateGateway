import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-18 下午4:32
 */
public class TestRandom {
    public static void main(String[] args) {
        Random r = new Random();
        for (int i = 0; i < 10; i++) {
            System.out.println(r.nextLong());
        }


        Integer i = Integer.MAX_VALUE;
        System.out.println(i);

        String x = Integer.toHexString(i);
        System.out.println(x);
        System.out.println(Integer.valueOf(x,16));
        DateFormat df = new SimpleDateFormat("yyyyMMdd");
        System.out.println(df.format(new Date()));
        System.out.println(Integer.valueOf(df.format(new Date())));
    }
}
