package com.savadanko.decorator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter @Setter
@AllArgsConstructor
public class DecoratorB implements Component{
    private Component component;

    @Override
    public void handle() {
        component.handle();
        System.out.println("Hello from DecoratorB");
    }
}
