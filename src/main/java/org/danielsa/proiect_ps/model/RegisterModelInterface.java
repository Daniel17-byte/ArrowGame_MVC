package org.danielsa.proiect_ps.model;

import org.danielsa.proiect_ps.DatabaseService;

public interface RegisterModelInterface {
    boolean register(String username, String password, String usertype);
    DatabaseService getDatabaseService();
}
