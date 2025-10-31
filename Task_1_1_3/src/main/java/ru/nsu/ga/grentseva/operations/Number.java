package ru.nsu.ga.grentseva.operations;

import java.util.Map;

public class Number extends Expression {

    private final int number;

    public Number(int number) {
        this.number = number;
    }

    @Override
    public void print() {
        System.out.print(number);
    }

    @Override
    public int eval(Map<String, Integer> vars){
        return number;
    }

    @Override
    public Expression derivative(String var){
        return new Number(0);
    }
}
