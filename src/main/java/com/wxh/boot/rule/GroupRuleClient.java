package com.wxh.boot.rule;

import org.jeasy.rules.api.Rule;
import org.jeasy.rules.api.Rules;
import org.jeasy.rules.api.RulesEngine;
import org.jeasy.rules.core.DefaultRulesEngine;

import java.util.Map;

public class GroupRuleClient {
   private static RulesEngine rulesEngine;
   private static Rules rules;
   static {
      rules = new Rules();
      rules.register(new YoungGroupRule());
      rules.register(new OldGroupRule());
      rules.register(new AdultGroupRule());
      rulesEngine = new DefaultRulesEngine();
   }
   public static Integer matchGroup(User user){
      GroupFacts facts = new GroupFacts(user);
      rulesEngine.fire(rules, facts);
      if(null != facts.getGroup()){
         return facts.getGroup();
      }
      System.out.println("do not match any rule");
      return 0;
   }

   public static void main(String[] args) {
      User user1 = new User("test1", 10);
      User user2 = new User("test2", 30);
      User user3 = new User("test3", 60);
      System.out.println(GroupRuleClient.matchGroup(user1));
      System.out.println(GroupRuleClient.matchGroup(user2));
      System.out.println(GroupRuleClient.matchGroup(user3));
   }
}
