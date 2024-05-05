package org.danielsa.proiect_ps.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PlayerModel {
    private String color;
    private UserModel user;

    public PlayerModel(String color) {
        this.color = color;
    }

}
