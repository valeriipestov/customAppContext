package com.custom.context.test;

import com.custom.context.annotation.CustomBean;
import lombok.Data;

import java.util.List;

@CustomBean
@Data
public class TestClassTwo {
    private String fieldOne;
    private int fieldTwo;
    private List<String> fieldThree;
}
