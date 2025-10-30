package com.savadanko.observer;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

public class Subject {
    @Setter @Getter
    private Event event;

    private final List<Observer> observers;

    public Subject() {
        observers = new ArrayList<>();
    }

    public void notifyObservers() {
        observers.forEach(observer -> observer.update(event));
    }

    public boolean addObserver(Observer observer) {
        if (observers.contains(observer)) {
            return false;
        }
        return observers.add(observer);
    }

    public boolean removeObserver(Observer observer) {
        return observers.remove(observer);
    }
}
