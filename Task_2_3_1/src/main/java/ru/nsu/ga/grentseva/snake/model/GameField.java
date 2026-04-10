package ru.nsu.ga.grentseva.snake.model;

import java.util.*;

public class GameField {

    private final int w;
    private final int h;

    private final Set<Cell> occupied = new HashSet<>();
    private final List<Cell> freeCells = new ArrayList<>();
    private final Set<Cell> obstacles = new HashSet<>();

    private final Random random = new Random();


    public GameField(int w, int h) {
        this.w = w;
        this.h = h;

        for (int x = 0; x < w; x++) {
            for (int y = 0; y < h; y++) {
                freeCells.add(new Cell(x, y));
            }
        }
    }


    public void generateWalls(int count, Cell start) {

        List<Cell> safeZone = getSafeZone(start);

        List<Cell> candidates = new ArrayList<>(freeCells);

        candidates.removeAll(safeZone);

        Collections.shuffle(candidates);

        int wallsToPlace = Math.min(count, candidates.size());

        for (int i = 0; i < wallsToPlace; i++) {
            Cell c = candidates.get(i);
            obstacles.add(c);
            occupy(c);
        }
    }


    public void occupy(Cell c) {
        occupied.add(c);
        freeCells.remove(c);
    }

    public void free(Cell c) {
        occupied.remove(c);
        freeCells.add(c);
    }


    public List<Cell> freeCells() {
        return freeCells;
    }

    public Set<Cell> getObstacles() {
        return obstacles;
    }


    private List<Cell> getSafeZone(Cell start) {
        List<Cell> zone = new ArrayList<>();

        for (int dx = -2; dx <= 2; dx++) {
            for (int dy = -2; dy <= 2; dy++) {

                int x = start.x() + dx;
                int y = start.y() + dy;

                if (isInside(x, y)) {
                    zone.add(new Cell(x, y));
                }
            }
        }

        return zone;
    }

    private boolean isInside(int x, int y) {
        return x >= 0 && x < w && y >= 0 && y < h;
    }
}