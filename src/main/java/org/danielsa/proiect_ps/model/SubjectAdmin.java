package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.view.ObserverAdmin;

public interface SubjectAdmin {
    void attach(ObserverAdmin o);
    void detach(ObserverAdmin o);
    void notifyObservers(boolean success, String op);
}
