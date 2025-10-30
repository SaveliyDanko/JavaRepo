package com.savadanko.observer;

public class ObserverA implements Observer{
    @Override
    public void update(Event event) {
        System.out.println(event.getInfo());
    }
}
