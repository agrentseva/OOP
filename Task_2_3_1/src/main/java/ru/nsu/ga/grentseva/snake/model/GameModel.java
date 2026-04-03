package ru.nsu.ga.grentseva.snake.model;

import ru.nsu.ga.grentseva.snake.config.GameConfig;
import ru.nsu.ga.grentseva.snake.render.RenderCommand;
import ru.nsu.ga.grentseva.snake.render.RenderType;
import ru.nsu.ga.grentseva.snake.service.FoodGenerator;

import java.util.ArrayList;
import java.util.List;

public class GameModel {

    private final GameField field;
    private final Snake snake;
    private final FoodGenerator generator = new FoodGenerator();

    private final GameConfig config;
    private final boolean isEndless;

    private final List<Food> foods = new ArrayList<>();
    private GameState state = GameState.RUNNING;
    private int score = 0;


    public GameModel(GameConfig config, boolean isEndless) {
        this.config = config;
        this.isEndless = isEndless;

        field = new GameField(config.width, config.height);

        Cell start = new Cell(config.width / 2, config.height / 2);
        snake = new Snake(start);

        field.occupy(start);
        field.generateWalls(30, start);

        spawnFood();
    }


    public List<RenderCommand> update() {
        if (state != GameState.RUNNING) return List.of();

        Direction dir = snake.consumeDirection();
        Cell next = wrap(snake.head().move(dir));

        if (isCollision(next)) {
            state = GameState.GAME_OVER;
            return List.of();
        }

        Food eaten = findFood(next);

        FoodEffect effect = eaten != null
                ? applyFood(eaten)
                : new FoodEffect(false, false, false);

        if (state == GameState.GAME_OVER) return List.of();

        List<RenderCommand> commands = new ArrayList<>();

        var move1 = snake.move(next, effect.grow);

        Snake.MoveResult move2 = null;

        if (effect.bonus) {
            Cell extraHead = wrap(snake.head().move(dir));

            if (isCollision(extraHead)) {
                state = GameState.GAME_OVER;
                return List.of();
            }

            move2 = snake.move(extraHead, true);
        }

        commands.addAll(applyMove(move1));

        if (move2 != null) {
            commands.addAll(applyMove(move2));
        }

        if (effect.poison) {
            if (snake.size() > 1) {
                Cell tail = snake.removeTail();

                if (!snake.contains(tail)) {
                    field.free(tail);
                    commands.add(new RenderCommand(tail, RenderType.EMPTY, null));
                }
                field.free(tail);
                commands.add(new RenderCommand(tail, RenderType.EMPTY, null));
            } else {
                state = GameState.GAME_OVER;
            }
        }

        if (eaten != null) {
            updateFood(commands, eaten);
        }

        if (!isEndless && snake.size() >= config.targetLength) {
            state = GameState.WIN;
        }

        return commands;
    }


    private List<RenderCommand> applyMove(Snake.MoveResult move) {
        List<RenderCommand> commands = new ArrayList<>();

        field.occupy(move.newHead());
        commands.add(new RenderCommand(move.newHead(), RenderType.SNAKE, null));

        if (move.removedTail() != null && !snake.contains(move.removedTail())) {
            field.free(move.removedTail());
            commands.add(new RenderCommand(move.removedTail(), RenderType.EMPTY, null));
        }

        return commands;
    }


    private boolean isCollision(Cell next) {
        return field.getObstacles().contains(next) || snake.contains(next);
    }

    private Food findFood(Cell next) {
        for (Food f : foods) {
            if (f.position().equals(next)) {
                return f;
            }
        }
        return null;
    }


    private FoodEffect applyFood(Food food) {
        boolean grow = false;
        boolean bonus = false;
        boolean poison = false;

        switch (food.type()) {

            case NORMAL -> {
                score += 1;
                grow = true;
            }

            case BONUS -> {
                score += 2;
                grow = true;
                bonus = true;
            }

            case POISON -> {
                score -= 1;

                if (score < 0) {
                    state = GameState.GAME_OVER;
                    return new FoodEffect(false, false, false);
                }

                poison = true;
            }
        }

        return new FoodEffect(grow, bonus, poison);
    }


    private void updateFood(List<RenderCommand> commands, Food eaten) {
        foods.remove(eaten);

        spawnFood();

        for (Food f : foods) {
            commands.add(new RenderCommand(f.position(), RenderType.FOOD, f.type()));
        }
    }


    private void spawnFood() {

        int attempts = 0;
        int maxAttempts = config.foodCount * 10;

        while (foods.size() < config.foodCount && attempts < maxAttempts) {
            Food f = generator.generate(field);
            if (f != null) {
                foods.add(f);
            }
            attempts++;
        }

        ensureSafeFood();
    }

    private long countPoison() {
        return foods.stream()
                .filter(f -> f.type() == FoodType.POISON)
                .count();
    }

    private void ensureSafeFood() {
        if (hasSafeFood() || foods.isEmpty()) return;

        Food poison = foods.get(0);

        for (Food f : foods) {
            if (f.type() == FoodType.POISON) {
                poison = f;
                break;
            }
        }

        foods.remove(poison);
        foods.add(new Food(poison.position(), FoodType.NORMAL));
    }


    private boolean hasSafeFood() {
        for (Food f : foods) {
            if (f.type() != FoodType.POISON) return true;
        }
        return false;
    }


    private Cell wrap(Cell c) {
        int x = c.x();
        int y = c.y();

        if (x < 0) x = config.width - 1;
        if (x >= config.width) x = 0;

        if (y < 0) y = config.height - 1;
        if (y >= config.height) y = 0;

        return new Cell(x, y);
    }


    public Snake getSnake() { return snake; }
    public GameState getState() { return state; }
    public GameField getField() { return field; }
    public List<Food> getFoods() { return foods; }
    public int getScore() { return score; }
    public GameConfig getConfig() { return config; }


    private record FoodEffect(boolean grow, boolean bonus, boolean poison) {}
}