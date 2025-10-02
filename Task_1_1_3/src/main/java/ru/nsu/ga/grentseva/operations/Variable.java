package ru.nsu.ga.grentseva.operations;

import java.util.Map;

public class Variable extends Expression {

    private final String variable;

    public Variable(String var){
        variable = var;
    }

    @Override
    public void print(){
        System.out.print(variable);
    }

    @Override
    public int eval(Map<String, Integer> vars){
        if (vars.containsKey(variable)){
            return vars.get(variable);
        }
        else{
            throw new IllegalArgumentException("Error: No value for variable");
        }

    }

    @Override
    public Expression derivative(String var){
        if (variable.equals(var)) {
            return new Number(1);
        } else {
            return new Number(0);
        }
    }
}
