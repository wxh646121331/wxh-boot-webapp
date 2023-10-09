package com.wxh.boot.rule;

import org.jeasy.rules.annotation.Rule;

@Rule(name = "YoungGroupRule", description = "age <= 18")
public class YoungGroupRule extends AbstractGroupRule{
    @Override
    protected boolean check(User user) {
        return null != user.getAge() && user.getAge() <= 18;
    }

    @Override
    protected Integer group() {
        return 1;
    }

    @Override
    protected Integer order() {
        return 10;
    }
}
