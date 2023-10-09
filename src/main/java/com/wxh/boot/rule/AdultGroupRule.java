package com.wxh.boot.rule;

import org.jeasy.rules.annotation.Rule;

@Rule
public class AdultGroupRule extends AbstractGroupRule{
    @Override
    protected boolean check(User user) {
        return null != user.getAge() && user.getAge() >= 18;
    }

    @Override
    protected Integer group() {
        return 3;
    }

    @Override
    protected Integer order() {
        return 8;
    }
}
