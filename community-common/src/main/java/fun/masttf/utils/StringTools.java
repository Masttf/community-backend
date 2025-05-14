package fun.masttf.utils;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang3.RandomStringUtils;

public class StringTools {

    public static boolean isEmpty(String str) {
        return str == null || str.isEmpty() || str.trim().isEmpty() || str.equals("null");
    }

    public static final String getRandomString(Integer count) {
        return RandomStringUtils.random(count, true, true);
    }

    public static final String getRandomNumber(Integer count) {
        return RandomStringUtils.random(count, false, true);
    }

    public static String encodeMd5(String str) {
        return str.isEmpty() ? null : DigestUtils.md5Hex(str);
    }

    public static String getFileSuffix(String fileName) {
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    public static String escapeHtml(String content) {
        if(isEmpty(content)) {
            return content;
        }
        return content.replace(" ", "&nbsp;")
                .replace("<", "&lt;")
                .replace("\n", "<br/>");
                
    }
}
