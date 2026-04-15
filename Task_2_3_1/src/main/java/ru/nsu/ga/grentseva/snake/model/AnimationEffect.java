package ru.nsu.ga.grentseva.snake.model;

public class AnimationEffect {

    private final String text;
    private final Cell position;
    private double lifetime;

    public AnimationEffect(String text, Cell position, double lifetime) {
        this.text = text;
        this.position = position;
        this.lifetime = lifetime;
    }

    public String getText() {
        return text;
    }

    public Cell getPosition() {
        return position;
    }

    public double getLifetime() {
        return lifetime;
    }

    public void update(double deltaTime) {
        lifetime -= deltaTime;
    }

    public boolean isFinished() {
        return lifetime <= 0;
    }
}