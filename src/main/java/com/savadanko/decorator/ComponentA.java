package com.savadanko.decorator;

public final class ComponentA implements Component{
    @Override
    public void handle() {
        System.out.println("Hello from ComponentA");
    }
}
