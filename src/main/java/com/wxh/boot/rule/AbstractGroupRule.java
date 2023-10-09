package com.wxh.boot.rule;

import org.jeasy.rules.annotation.Action;
import org.jeasy.rules.annotation.Condition;
import org.jeasy.rules.annotation.Fact;
import org.jeasy.rules.annotation.Priority;

public abstract class  AbstractGroupRule {

    @Condition
    public boolean when(@Fact("user") User user) {
        return check(user);
    }

    @Priority
    public int priority() {
        return order();
    }

    @Action
    public void then(GroupFacts facts) {
        facts.setGroup(group());
        System.out.println(facts.asMap() + " matched rule:" + this.getClass().getSimpleName() + ", priority:" + priority());
    }

    protected abstract boolean check(User user);

    protected abstract Integer group();

    protected abstract Integer order();

}
