package com.custom.context;

import com.custom.context.config.CustomApplicationContext;
import com.custom.context.exceptions.NoSuchBeanException;
import com.custom.context.exceptions.NoUniqueBeanException;
import com.custom.context.test.Duplicate;
import com.custom.context.test.TestClassOne;
import com.custom.context.test.TestClassThree;
import com.custom.context.test.TestClassTwo;

public class Init {

    public static void main(String[] args) {
        var context = new CustomApplicationContext("com.custom.context");
        try {
            var firstBean = context.getBean("firstClassBean", TestClassOne.class);
            System.out.println(firstBean.toString());
            var secondBean = context.getBean(TestClassTwo.class);
            var allBeans = context.getAllBeans(Duplicate.class);
            var noBean = context.getBean("testclassthree", TestClassThree.class);
        } catch (NoSuchBeanException | NoUniqueBeanException e) {
            System.out.println(e.getMessage());
        }


    }
}
