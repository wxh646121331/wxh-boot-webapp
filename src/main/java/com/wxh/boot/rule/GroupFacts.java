package com.wxh.boot.rule;

import lombok.Getter;
import lombok.Setter;
import org.jeasy.rules.api.Facts;

public class GroupFacts extends Facts {

    @Getter
    @Setter
    private Integer group;

    GroupFacts(User user){
        this.put("user",user);
    }

    public static void main(String[] args) {
        User user = new User("test", 10);
        GroupFacts facts = new GroupFacts(user);
    }

}
