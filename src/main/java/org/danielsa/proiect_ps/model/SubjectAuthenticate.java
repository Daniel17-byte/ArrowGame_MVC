package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.view.ObserverAuthenticate;

public interface SubjectAuthenticate {
    void attach(ObserverAuthenticate o);
    void detach(ObserverAuthenticate o);
    void notifyObservers(boolean success);
}