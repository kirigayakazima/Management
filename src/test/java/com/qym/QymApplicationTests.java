package com.qym;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.ArrayList;
import java.util.List;

@SpringBootTest
class QymApplicationTests {

    @Test
    void contextLoads() {
    }
    @Test
    public List<String> index() {
        List<String> a=new ArrayList<>();
        String b;
        int i;
        for (i = 0; i < 7000; i++) {
            b="[em]e"+i+"[\\em]";
            a.add(b);
        }
        System.out.println(a);
        return a;
    }
}
