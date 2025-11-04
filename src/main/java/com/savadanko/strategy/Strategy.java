package com.savadanko.strategy;

public class Strategy {
    public static void main(String[] args) {
        DoSomething actionA = new ActionA();
        DoSomething actionB = new ActionB();

        Person person = new Person();

        person.setAction(actionA);
        person.doAction();

        person.setAction(actionB);
        person.doAction();
    }
}
