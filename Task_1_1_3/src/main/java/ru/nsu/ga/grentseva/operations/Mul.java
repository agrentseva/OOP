package ru.nsu.ga.grentseva.operations;

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
    public int eval(Map<String, Integer> vars){
        return left.eval(vars) * right.eval(vars);
    }

    // (f * g)' = f'*g + f*g'
    @Override
    public Expression derivative(String var){
        return new Add(new Mul(left.derivative(var), right),
                new Mul(left, right.derivative(var)));
    }
}
