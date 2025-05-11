package fun.masttf.utils;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

public class CopyTools {
    public static <T, S> List<T> copyList(List<S> source, Class<T> targetClass) {
        List<T> targetList = new ArrayList<>();
        for (S sourceItem : source) {
            T t = null;
            try {
                t = targetClass.newInstance();
            } catch (Exception e) {
                e.printStackTrace();
            }
            if (t != null) {
                BeanUtils.copyProperties(sourceItem, t);
                targetList.add(t);
            }
        }
        return targetList;
    }

    public static <T, S> T copy(S source, Class<T> targetClass) {
        T t = null;
        try {
            t = targetClass.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (t != null) {
            BeanUtils.copyProperties(source, t);
        }
        return t;
    }
}
