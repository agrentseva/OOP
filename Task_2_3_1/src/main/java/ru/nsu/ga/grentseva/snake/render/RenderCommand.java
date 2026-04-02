package ru.nsu.ga.grentseva.snake.render;

import ru.nsu.ga.grentseva.snake.model.Cell;
import ru.nsu.ga.grentseva.snake.model.FoodType;

public record RenderCommand(Cell cell, RenderType type, FoodType foodType) {}