package com.wxh.boot.rule;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Priority;
import org.jeasy.rules.annotation.Rule;

@Rule
public class OldGroupRule extends AbstractGroupRule{

    @Override
    protected boolean check(User user) {
        return null != user.getAge() && user.getAge() >= 50;
    }

    @Override
    protected Integer group() {
        return 2;
    }

    @Override
    protected Integer order() {
        return 9;
    }
}
