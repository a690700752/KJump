package com.werfad.utils;

import java.util.ArrayList;
import java.util.List;

public class StringUtils {
    public static List<Integer> findAll(String s, char c, boolean ignoreCase) {
        List<Integer> res = new ArrayList<>();
        char upper = Character.toUpperCase(c);
        char lower = Character.toLowerCase(c);

        for (int i = 0; i < s.length(); i++) {
            char charAt = s.charAt(i);
            if (ignoreCase) {
                if (charAt == upper || charAt == lower) {
                    res.add(i);
                }
            } else {
                if (s.charAt(i) == c) {
                    res.add(i);
                }
            }
        }

        return res;
    }
}
