package org.danielsa.proiect_ps.utils;

import org.danielsa.proiect_ps.model.GameBoardModel;
import org.danielsa.proiect_ps.model.MoveModel;

import java.util.*;
import java.util.stream.IntStream;

public class MinMaxStrategy {
    private final int maxDepth;
    private final int maxMoves;

    public MinMaxStrategy(int maxDepth, int maxMoves) {
        this.maxDepth = maxDepth;
        this.maxMoves = maxMoves;
    }

    private int evaluationFunction(GameBoardModel board) {
        return board.noValidMoves();
    }

    public MoveModel makeMove(GameBoardModel board) {
        return board.getValidMoves().stream()
                .limit(maxMoves)
                .map(move -> {
                    board.makeMove(move);
                    int val = maxValue(board, 0);
                    board.undoMove(move);
                    return new AbstractMap.SimpleEntry<>(move, val);
                })
                .max(Map.Entry.comparingByValue())
                .map(Map.Entry::getKey)
                .orElse(null);
    }

    private int minValue(GameBoardModel board, int level) {
        if (level == maxDepth) return evaluationFunction(board);

        return getNElements(board.getValidMoves(), maxMoves).stream()
                .mapToInt(move -> {
                    board.makeMove(move);
                    int val = maxValue(board, level + 1);
                    board.undoMove(move);
                    return val;
                })
                .min()
                .orElse(64);
    }

    private int maxValue(GameBoardModel board, int level) {
        if(level == maxDepth) return evaluationFunction(board);
        return getNElements(board.getValidMoves(), maxMoves).stream()
                .mapToInt(move -> {
                    board.makeMove(move);
                    int val = minValue(board, level + 1);
                    board.undoMove(move);
                    return val;
                })
                .max()
                .orElse(0);
    }

    private ArrayList<MoveModel> getNElements(ArrayList<MoveModel> source, int n) {
        Random rand = new Random();
        ArrayList<MoveModel> destination = new ArrayList<>(n);
        ArrayList<Integer> indices = new ArrayList<>(source.size());

        for (int i = 0; i < source.size(); i++) {
            indices.add(i);
        }

        IntStream.range(0, n)
                .filter(i -> !indices.isEmpty())
                .forEach(i -> {
                    int randomIndex = rand.nextInt(indices.size());
                    int sourceIndex = indices.get(randomIndex);
                    destination.add(source.get(sourceIndex));
                    indices.remove(randomIndex);
                });

        return destination;
    }

}