package com.example.labgui.observer;

import java.util.List;

public interface Observable {

    void addObserver(Observer o);
    void notifyObservers();
}
