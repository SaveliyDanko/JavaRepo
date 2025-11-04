package com.savadanko.strategy;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class Person {
    private DoSomething action;

    public void doAction(){
        this.action.doSomething();
    }
}
