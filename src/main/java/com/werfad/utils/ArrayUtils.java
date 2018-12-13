package com.werfad.utils;

public class ArrayUtils {
    public static void revertChars(char[] chars) {
        int l = 0;
        int r = chars.length - 1;
        while (l < r) {
            char tmp = chars[l];
            chars[l] = chars[r];
            chars[r] = tmp;

            l++;
            r--;
        }
    }
}
