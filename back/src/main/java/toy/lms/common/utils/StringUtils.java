package toy.lms.common.utils;

import java.util.Random;

public class StringUtils extends org.apache.commons.lang3.StringUtils {
    public static String randomStr(int num) {
        Random random = new Random();
        StringBuffer buffer = new StringBuffer();

        for (int i = 0; i < num; i++) {
            if (random.nextBoolean()) {
                buffer.append((char) ((int) (random.nextInt(26)) + 97));
            } else {
                buffer.append((random.nextInt(10)));
            }
        }

        return buffer.toString();
    }
}
