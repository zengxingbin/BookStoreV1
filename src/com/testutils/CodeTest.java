package com.testutils;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.collections.map.HashedMap;
import org.junit.Test;

public class CodeTest {
    @Test
    public void testConvert() {
        Object[] array = new String[10];
        Map<String,Object[]> map = null;
        Map<String,String[]> map2 = new HashMap<String, String[]>();
        //map = map2;报错
    }
}
