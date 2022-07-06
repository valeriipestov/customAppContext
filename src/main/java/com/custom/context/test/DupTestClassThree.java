package com.custom.context.test;

import com.custom.context.annotation.CustomBean;
import lombok.Data;

@CustomBean("thirdClassBean")
@Data
public class DupTestClassThree implements Duplicate {
    @Override
    public void test() {
        System.out.println("DupTestClassThree");
    }
}
