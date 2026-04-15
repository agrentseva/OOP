package ru.nsu.ga.grentseva.snake.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class BotSnake extends Snake {

    private final BotType type;
    private final Random random = new Random();

    public BotSnake(Cell start, BotType type) {
        super(start);
        this.type = type;
    }

    public BotType getType() {
        return type;
    }

    public Direction decideDirection(GameModel model) {
        if (type != BotType.RANDOM && random.nextDouble() < 0.1) {
            setDirection(randomDirection(model));
            return consumeDirection();
        }

        Direction direction = switch (type) {
            case RANDOM -> randomDirection(model);
            case GREEDY -> greedyDirection(model);
            case HUNTER -> hunterDirection(model);
        };

        setDirection(direction);
        return consumeDirection();
    }

    private Direction randomDirection(GameModel model) {
        List<Direction> safeDirections = new ArrayList<>();

        for (Direction d : Direction.values()) {
            if (d.isOpposite(getCurrentDirection())) continue;

            Cell next = model.wrap(head().move(d));

            if (!isDanger(next, model)) {
                safeDirections.add(d);
            }
        }

        if (!safeDirections.isEmpty()) {
            return safeDirections.get(random.nextInt(safeDirections.size()));
        }

        return safeFallback(model);
    }

    private Direction greedyDirection(GameModel model) {
        List<Cell> targets = model.getFoods()
                .stream()
                .map(Food::position)
                .toList();

        return bestDirection(model, targets);
    }

    private Direction hunterDirection(GameModel model) {
        List<Cell> targets = model.getSnakes()
                .stream()
                .filter(s -> s != this)
                .map(Snake::head)
                .toList();

        return bestDirection(model, targets);
    }

    private Direction bestDirection(GameModel model, List<Cell> targets) {
        Cell currentHead = head();

        Direction bestDirection = getCurrentDirection();
        double bestScore = Double.MAX_VALUE;

        for (Direction d : Direction.values()) {
            if (d.isOpposite(getCurrentDirection())) continue;

            Cell next = model.wrap(currentHead.move(d));

            if (isDanger(next, model)) continue;

            double score = distance(next, targets);

            if (score < bestScore) {
                bestScore = score;
                bestDirection = d;
            }
        }

        if (bestScore == Double.MAX_VALUE) {
            return safeFallback(model);
        }

        return bestDirection;
    }

    private double distance(Cell from, List<Cell> targets) {
        if (targets.isEmpty()) return Double.MAX_VALUE;

        double minDistance = Double.MAX_VALUE;

        for (Cell target : targets) {
            double dx = from.x() - target.x();
            double dy = from.y() - target.y();
            double dist = dx * dx + dy * dy;

            if (dist < minDistance) {
                minDistance = dist;
            }
        }

        return minDistance;
    }

    private boolean isDanger(Cell cell, GameModel model) {
        if (model.getField().getObstacles().contains(cell)) {
            return true;
        }

        for (Snake s : model.getSnakes()) {
            if (s.contains(cell)) {
                return true;
            }
        }

        return false;
    }

    private Direction safeFallback(GameModel model) {
        for (Direction d : Direction.values()) {
            if (d.isOpposite(getCurrentDirection())) continue;

            Cell next = model.wrap(head().move(d));

            if (!isDanger(next, model)) {
                return d;
            }
        }

        return getCurrentDirection();
    }
}