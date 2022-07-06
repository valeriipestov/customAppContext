package com.custom.context.test;

import com.custom.context.annotation.CustomBean;
import com.custom.context.annotation.CustomInject;
import com.custom.context.annotation.SimpleQualifier;
import lombok.Data;
import lombok.ToString;

import java.util.List;

@CustomBean("firstClassBean")
@Data
@ToString
public class TestClassOne {
    private String fieldOne;
    private int fieldTwo;
    private List<String> fieldThree;

    @CustomInject
    @SimpleQualifier("thirdClassBean")
    private Duplicate duplicate;
}
