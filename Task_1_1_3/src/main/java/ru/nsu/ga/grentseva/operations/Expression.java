package ru.nsu.ga.grentseva.operations;

import ru.nsu.ga.grentseva.parser.SeparatedVariables;

import java.util.Map;

public abstract class Expression {
    public abstract void print();
    public abstract Expression derivative(String var);
    public abstract int eval(Map<String, Integer> vars);  // штучка ключ - значение (попарный массив)

    public int eval(String varsStr){
        SeparatedVariables temp = new SeparatedVariables(varsStr);
        Map<String,Integer> vars = temp.getVars();
        return this.eval(vars);
    }

    /*public Expression simplify() {
        return this; // по умолчанию ничего не упрощаем
    }*/
}
