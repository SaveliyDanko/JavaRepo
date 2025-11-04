package com.savadanko.strategy;

public class ActionA implements DoSomething{
    @Override
    public void doSomething() {
        System.out.println("Hello from ActionA");
    }
}
