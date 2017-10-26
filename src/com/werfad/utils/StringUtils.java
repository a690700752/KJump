package com.werfad.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {

    public static List<Integer> findAll(String text, String find) {
        List<Integer> res = new ArrayList<>();
        if (find.length() > 2) {
            int index = text.indexOf(find);
            while (index >= 0) {
                res.add(index);
                index = text.indexOf(find, index + 1);
            }
        } else if (find.length() == 1) {
            char c = find.charAt(0);
            int index = text.indexOf(c);
            while (index >= 0) {
                res.add(index);
                index = text.indexOf(c, index + 1);
            }
        }
        return res;
    }
}
