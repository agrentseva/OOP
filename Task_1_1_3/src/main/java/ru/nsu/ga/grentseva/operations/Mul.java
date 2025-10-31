package ru.nsu.ga.grentseva.operations;

import ru.nsu.ga.grentseva.exceptions.DivisionByZeroException;
import ru.nsu.ga.grentseva.exceptions.MissingVariableException;

import java.util.Map;

public class Mul extends Expression{

    private final Expression left;
    private final Expression right;

    public Mul(Expression left, Expression right){
        this.left = left;
        this.right = right;
    }

    @Override
    public void print(){
        System.out.print("(");
        left.print();
        System.out.print("*");
        right.print();
        System.out.print(")");
    }

    @Override
    public int eval(Map<String, Integer> vars) throws MissingVariableException, DivisionByZeroException {
        return left.eval(vars) * right.eval(vars);
    }

    @Override
    public Expression derivative(String var){
        return new Add(new Mul(left.derivative(var), right),
                new Mul(left, right.derivative(var)));
    }
}
