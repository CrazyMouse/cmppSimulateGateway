import com.google.common.base.Charsets;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-18 下午11:04
 */
public class TestStringBytes {
    public static void main(String[] args) {
        System.out.println("DELIVRD".getBytes(Charsets.US_ASCII).length);
        DateFormat df = new SimpleDateFormat("yyMMddHHmm");
        System.out.println(df.format(new Date()).getBytes(Charsets.US_ASCII).length);
        System.out.println(df.format(new Date()));


    }
}
