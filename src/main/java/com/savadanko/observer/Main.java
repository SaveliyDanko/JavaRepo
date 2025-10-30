package com.savadanko.observer;

public class Main {
    public static void main(String[] args) {
        Event event = new Event("INFO 1");
        Subject subject = new Subject();
        subject.setEvent(event);

        ObserverA observerA = new ObserverA();
        ObserverB observerB = new ObserverB();

        subject.addObserver(observerA);
        subject.addObserver(observerB);

        subject.notifyObservers();

        event.setInfo("INFO 2");

        subject.notifyObservers();

        subject.removeObserver(observerA);

        event.setInfo("INFO 3");

        subject.notifyObservers();
    }
}
