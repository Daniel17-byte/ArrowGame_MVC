package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.view.Observer;

public interface Subject {
    void attach(Observer o);
    void detach(Observer o);
    void notifyObservers(boolean success);
}