package fun.masttf.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import fun.masttf.entity.enums.VerifyRegexEnum;

public class VerifyUtils {
    public static Boolean verify(String regs, String value) {
        if(StringTools.isEmpty(value)) {
            return false;
        }
        Pattern pattern = Pattern.compile(regs);
        Matcher matcher = pattern.matcher(value);
        return matcher.matches();
    }
    
    public static Boolean verify(VerifyRegexEnum regs, String value) {
        return verify(regs.getRegex(), value);
    }
}
