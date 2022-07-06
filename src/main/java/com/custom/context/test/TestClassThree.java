package com.custom.context.test;

import com.custom.context.annotation.CustomBean;
import lombok.Data;

import java.util.List;

@CustomBean("lastClassBean")
@Data
public class TestClassThree implements Duplicate {
    private String fieldOne;
    private int fieldTwo;
    private List<String> fieldThree;

    @Override
    public void test() {
        System.out.println("TestClassThree");
    }
}
