package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.view.ObserverGame;

public interface SubjectGame {
    void attach(ObserverGame o);
    void detach(ObserverGame o);
    void notifyObservers(boolean success);
}
