package ru.nsu.ga.grentseva.snake.model;

import ru.nsu.ga.grentseva.snake.config.GameConfig;
import ru.nsu.ga.grentseva.snake.render.RenderCommand;
import ru.nsu.ga.grentseva.snake.render.RenderType;
import ru.nsu.ga.grentseva.snake.service.FoodGenerator;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class GameModel {

    private final GameField field;
    private final Snake player;
    private final List<Snake> snakes = new ArrayList<>();

    private final FoodGenerator generator = new FoodGenerator();
    private final List<Food> foods = new ArrayList<>();

    private final GameConfig config;
    private final boolean isEndless;
    private GameState state = GameState.RUNNING;

    private int score = 0;
    private final List<AnimationEffect> animationEffects = new ArrayList<>();

    public GameModel(GameConfig config, boolean isEndless) {
        this.config = config;
        this.isEndless = isEndless;

        field = new GameField(config.width, config.height);

        Cell start = new Cell(config.width / 2, config.height / 2);
        player = new Snake(start);
        snakes.add(player);
        field.occupy(start);

        spawnBot(new ArrayList<>());

        field.generateWalls(config.wallCount, start);

        spawnFood();
    }

    public List<RenderCommand> update() {
        if (state != GameState.RUNNING) return List.of();

        List<RenderCommand> commands = new ArrayList<>();

        for (int i = 0; i < snakes.size(); i++) {
            Snake s = snakes.get(i);
            if (s.head() == null) {
                if (s == player) {
                    state = GameState.GAME_OVER;
                    return commands;
                }
            }
        }

        List<Direction> directions = new ArrayList<>();
        for (Snake s : snakes) {
            if (s == player) {
                directions.add(player.consumeDirection());
            } else {
                directions.add(((BotSnake) s).decideDirection(this));
            }
        }

        List<Cell> nextPositions = new ArrayList<>();
        for (int i = 0; i < snakes.size(); i++) {
            Snake s = snakes.get(i);
            if (s.head() == null) {
                nextPositions.add(null);
            } else {
                nextPositions.add(wrap(s.head().move(directions.get(i))));
            }
        }

        boolean[] alive = new boolean[snakes.size()];
        for (int i = 0; i < alive.length; i++) alive[i] = true;

        for (int i = 0; i < snakes.size(); i++) {
            if (nextPositions.get(i) == null) {
                alive[i] = false;
                continue;
            }

            Cell next = nextPositions.get(i);

            if (field.getObstacles().contains(next)) {
                alive[i] = false;
                continue;
            }

            for (Snake other : snakes) {
                if (other.contains(next)) {
                    alive[i] = false;
                    break;
                }
            }
        }

        for (int i = 0; i < snakes.size(); i++) {
            for (int j = i + 1; j < snakes.size(); j++) {
                if (nextPositions.get(i) != null &&
                        nextPositions.get(i).equals(nextPositions.get(j))) {
                    alive[i] = false;
                    alive[j] = false;
                }
            }
        }

        if (!alive[0]) {
            state = GameState.GAME_OVER;
            return commands;
        }

        for (int i = 0; i < snakes.size(); i++) {
            if (!alive[i]) continue;

            Snake s = snakes.get(i);
            Cell next = nextPositions.get(i);

            boolean grow = false;
            boolean doubleGrow = false;
            Food eaten = findFood(next);
            FoodEffect effect = null;

            if (eaten != null) {
                effect = applyFood(eaten);
                grow = effect.grow;
                doubleGrow = effect.bonus;

                if (s == player) {
                    score += switch (eaten.type()) {
                        case NORMAL -> 10;
                        case BONUS -> 20;
                        case POISON -> -5;
                    };
                }

                updateFood(commands, eaten);
            }

            var move = s.move(next, grow);

            field.occupy(move.newHead());
            RenderType type = getRenderType(s);
            commands.add(new RenderCommand(move.newHead(), type, null));

            if (move.removedTail() != null && !s.contains(move.removedTail())) {
                field.free(move.removedTail());
                commands.add(new RenderCommand(move.removedTail(), RenderType.EMPTY, null));
            }

            if (effect != null && effect.poison) {
                if (s.size() <= 1) {
                    alive[i] = false;

                    if (s == player) {
                        state = GameState.GAME_OVER;
                        return commands;
                    }
                } else {
                    Cell tail = s.removeTail();
                    if (tail != null && !s.contains(tail)) {
                        field.free(tail);
                        commands.add(new RenderCommand(tail, RenderType.EMPTY, null));
                    }
                }
            }


            if (doubleGrow) {
                Cell extraHead = wrap(s.head().move(directions.get(i)));

                boolean danger = false;
                if (field.getObstacles().contains(extraHead)) {
                    danger = true;
                } else {
                    for (Snake other : snakes) {
                        if (other.contains(extraHead)) {
                            danger = true;
                            break;
                        }
                    }
                }

                if (danger) {
                    alive[i] = false;
                } else {
                    var move2 = s.move(extraHead, true);
                    field.occupy(move2.newHead());
                    commands.add(new RenderCommand(move2.newHead(), type, null));
                }
            }
        }

        int currentBots = snakes.size() - 1;
        for (int i = snakes.size() - 1; i >= 1; i--) {
            if (!alive[i]) {
                Snake dead = snakes.get(i);

                if (dead.head() != null) {
                    animationEffects.add(new AnimationEffect("БУМ!", dead.head(), 2.0));
                }

                for (Cell c : dead.getBody()) {
                    field.free(c);
                    commands.add(new RenderCommand(c, RenderType.EMPTY, null));
                }

                snakes.remove(i);
                currentBots--;
            }
        }

        int targetBots = getTargetBotCount();
        while (currentBots < targetBots) {
            spawnBot(commands);
            currentBots++;
        }

        if (!isEndless && player.size() >= config.targetLength) {
            state = GameState.WIN;
        }

        return commands;
    }

    public void updateEffects(double deltaTime) {
        animationEffects.forEach(e -> e.update(deltaTime));
        animationEffects.removeIf(AnimationEffect::isFinished);
    }

    private void spawnBot(List<RenderCommand> commands) {
        List<Cell> possibleCells = new ArrayList<>(field.freeCells());
        if (possibleCells.isEmpty()) return;

        possibleCells.removeAll(getPlayerSafeZone());

        if (possibleCells.isEmpty()) return;

        Cell pos = possibleCells.get((int)(Math.random() * possibleCells.size()));

        BotType type = BotType.values()[(int)(Math.random() * BotType.values().length)];
        BotSnake bot = new BotSnake(pos, type);

        snakes.add(bot);
        field.occupy(pos);

        RenderType renderType = getRenderType(bot);
        commands.add(new RenderCommand(pos, renderType, null));
    }

    private int getTargetBotCount() {
        int base = 1;
        int extra = (player.size() - 1) / 5;
        int fieldLimit = (config.width * config.height) / 50;
        return Math.min(base + extra, fieldLimit);
    }

    private Set<Cell> getPlayerSafeZone() {
        Set<Cell> safeZone = new HashSet<>();
        int radius = 2;

        for (Cell snakeCell : player.getBody()) {
            for (int dx = -radius; dx <= radius; dx++) {
                for (int dy = -radius; dy <= radius; dy++) {
                    safeZone.add(new Cell(snakeCell.x() + dx, snakeCell.y() + dy));
                }
            }
        }
        return safeZone;
    }

    private void spawnFood() {
        int targetFoodCount = getTargetFoodCount();
        int attempts = 0;
        int maxAttempts = targetFoodCount * 10;

        while (foods.size() < targetFoodCount && attempts < maxAttempts) {
            Food f = generator.generate(field);
            if (f != null && !foods.contains(f)) {
                foods.add(f);
            }
            attempts++;
        }
        ensureSafeFood();
    }

    private void updateFood(List<RenderCommand> commands, Food eaten) {
        foods.remove(eaten);
        commands.add(new RenderCommand(eaten.position(), RenderType.EMPTY, null));
        spawnFood();
        for (Food f : foods) {
            if (!commands.stream().anyMatch(cmd -> cmd.cell().equals(f.position()))) {
                commands.add(new RenderCommand(f.position(), RenderType.FOOD, f.type()));
            }
        }
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
            case NORMAL -> grow = true;
            case BONUS -> {
                grow = true;
                bonus = true;
            }
            case POISON -> poison = true;
        }
        return new FoodEffect(grow, bonus, poison);
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

    private int getTargetFoodCount() {
        int base = config.foodCount;
        int bonus = 0;
        for (Snake snake : snakes) {
            if (snake instanceof BotSnake bot) {
                if (bot.getType() == BotType.GREEDY) {
                    bonus += 2;
                } else {
                    bonus += 1;
                }
            }
        }
        return base + bonus;
    }

    private RenderType getRenderType(Snake s) {
        if (s == player) return RenderType.SNAKE_PLAYER;
        BotType t = ((BotSnake) s).getType();
        return switch (t) {
            case RANDOM -> RenderType.SNAKE_RANDOM;
            case GREEDY -> RenderType.SNAKE_GREEDY;
            case HUNTER -> RenderType.SNAKE_HUNTER;
        };
    }

    public Cell wrap(Cell c) {
        int x = c.x();
        int y = c.y();
        if (x < 0) x = config.width - 1;
        if (x >= config.width) x = 0;
        if (y < 0) y = config.height - 1;
        if (y >= config.height) y = 0;
        return new Cell(x, y);
    }

    public List<AnimationEffect> getAnimationEffects() { return animationEffects; }
    public Snake getPlayer() { return player; }
    public List<Snake> getSnakes() { return snakes; }
    public Snake getSnake() { return player; }
    public GameState getState() { return state; }
    public GameField getField() { return field; }
    public List<Food> getFoods() { return foods; }
    public int getScore() { return score; }
    public GameConfig getConfig() { return config; }

    private record FoodEffect(boolean grow, boolean bonus, boolean poison) {}
}
