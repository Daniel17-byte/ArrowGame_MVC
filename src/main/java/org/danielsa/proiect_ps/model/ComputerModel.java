package org.danielsa.proiect_ps.model;

import lombok.Setter;
import org.danielsa.proiect_ps.utils.MinMaxStrategy;

@Setter
public class ComputerModel extends PlayerModel {
    private MinMaxStrategy strategy;

    public ComputerModel(String color) {
        super(color);
        this.strategy = new MinMaxStrategy(8,16);
    }

    public MoveModel makeMove(GameBoardModel board) {
        return strategy.makeMove(board);
    }

}
