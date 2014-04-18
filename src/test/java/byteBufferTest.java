import com.google.common.primitives.UnsignedBytes;

import java.nio.ByteBuffer;

/**
 * Title ：
 * Description :
 * Create Time: 14-4-14 下午3:01
 */
public class byteBufferTest {
    public static void main(String[] args) {
        byte[] head = new byte[12];
        ByteBuffer byteBuffer = ByteBuffer.wrap(head);
        byteBuffer.putInt(62232);
        byteBuffer.putInt(30);
        byteBuffer.putInt(40);
        byteBuffer.rewind();
        byteBuffer.getInt();
        byte[] bytes = byteBuffer.array();

        System.out.println(byteBuffer.position());

        int a = 255;
        int b = UnsignedBytes.toInt((byte) a);
        System.out.println((byte) a);
        System.out.println(b);
    }
}
