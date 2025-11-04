package com.savadanko.decorator;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
@AllArgsConstructor
public class DecoratorA implements Component{
    private Component component;

    @Override
    public void handle() {
        component.handle();
        System.out.println("Hello from DecoratorA");
    }
}
