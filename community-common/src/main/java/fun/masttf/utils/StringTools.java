package fun.masttf.utils;

import org.apache.commons.lang3.RandomStringUtils;

public class StringTools {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty() || str.trim().isEmpty() || str.equals("null");
    }

    public static String getRandomString(Integer count) {
        return RandomStringUtils.random(count, true, true);
    }
}
