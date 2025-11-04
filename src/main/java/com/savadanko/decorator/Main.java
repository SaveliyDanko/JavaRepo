package com.savadanko.decorator;

public class Main {
    public static void main(String[] args) {
        Component component1 = new ComponentA();

        Component decorator1 = new DecoratorA(component1);
        Component decorator2 = new DecoratorB(decorator1);

        decorator2.handle();
    }
}
