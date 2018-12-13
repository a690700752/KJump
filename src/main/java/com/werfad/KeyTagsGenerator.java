package com.werfad;

import com.werfad.utils.ArrayUtils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class KeyTagsGenerator {
    // maybe has a math method
    public static List<String> createTagsTree(int targetCount, String keys) {
        List<String> res = new ArrayList<>();
        _createTagsTree(targetCount, keys, "", res);
        return res;
    }

    private static void _createTagsTree(int targetCount, String keys, String prefix, List<String> res) {
        int keysLen = keys.length();
        HashMap<Character, Integer> keysCountMap = new HashMap<>();

        char[] chars = keys.toCharArray();
        for (char c : chars) {
            keysCountMap.put(c, 0);
        }

        /* Calculate counts each branch stored. */
        ArrayUtils.revertChars(chars);
        int targetsLeft = targetCount;
        int level = 0;

        while (targetsLeft > 0) {
            int childsLen = (level == 0 ? 1 : (keysLen - 1));

            for (char key : chars) {
                keysCountMap.put(key, keysCountMap.get(key) + childsLen);

                targetsLeft -= childsLen;
                if (targetsLeft <= 0) {
                    keysCountMap.put(key, keysCountMap.get(key) + targetsLeft);
                    break;
                }
            }
            level++;
        }

        /* Create tree ( represent by String array ). */
        ArrayUtils.revertChars(chars);
        for (char key : chars) {
            int count = keysCountMap.get(key);
            if (count > 1) {
                _createTagsTree(count, keys, prefix + key, res);
            } else if (count == 1) {
                res.add(prefix + key);
            }
        }
    }
}
