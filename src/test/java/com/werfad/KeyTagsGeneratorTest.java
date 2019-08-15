package com.werfad;

import org.junit.Test;

import java.util.List;

import static org.junit.Assert.assertEquals;

public class KeyTagsGeneratorTest {

    @Test
    public void createTagsTree() {
        List<String> res = KeyTagsGenerator.createTagsTree(756,
                "abcdefghijklmnopqrstuvwxyz");

        assertEquals(res.size(), 756);
        System.out.println(res.size());
        System.out.println(res);

        List<String> res1 = KeyTagsGenerator.createTagsTree(0,
                "abcdefghijklmnopqrstuvwxyz");
        assertEquals(res1.size(), 0);
    }
}