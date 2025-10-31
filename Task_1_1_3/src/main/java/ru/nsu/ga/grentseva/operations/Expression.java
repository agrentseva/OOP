package ru.nsu.ga.grentseva.operations;

import ru.nsu.ga.grentseva.exceptions.DivisionByZeroException;
import ru.nsu.ga.grentseva.exceptions.MissingVariableException;
import ru.nsu.ga.grentseva.exceptions.ParseException;
import ru.nsu.ga.grentseva.parser.SeparatedVariables;

import java.util.Map;

public abstract class Expression {
    public abstract void print();
    public abstract Expression derivative(String var);
    public abstract int eval(Map<String, Integer> vars) throws MissingVariableException, DivisionByZeroException;

    public int eval(String varsStr) throws ParseException, MissingVariableException, DivisionByZeroException {
        SeparatedVariables temp = new SeparatedVariables(varsStr);
        Map<String,Integer> vars = temp.getVars();
        return this.eval(vars);
    }
}
