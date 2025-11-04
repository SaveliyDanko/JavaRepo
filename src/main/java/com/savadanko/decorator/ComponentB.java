package com.savadanko.decorator;

public final class ComponentB implements Component {
    @Override
    public void handle() {
        System.out.println("Hello from ComponentB");
    }
}
