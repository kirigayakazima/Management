package com.qym;

import java.util.ArrayList;
import java.util.List;

public class test {
    private int i;
    public List<String> index() {
        List<String> a=new ArrayList<>();
        String b;
        for (i = 0; i < 7000; i++) {
            b="[em]e"+i+"[\\em]";
            a.add(b);
        }
        return a;
    }
}
