package ru.nsu.ga.grentseva;

import ru.nsu.ga.grentseva.operations.*;
import ru.nsu.ga.grentseva.operations.Number;
import ru.nsu.ga.grentseva.parser.ParseExpession;

import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Expression e = new Add(new ru.nsu.ga.grentseva.operations.Number(3), new Mul(new Number(2),new Variable("x")));
        e.print();
        System.out.println();
        Expression de = e.derivative("x");
        de.print();
        System.out.println();
        int result = e.eval("x = 10; y = 13");
        System.out.println(result);

        Scanner scanner = new Scanner(System.in);
        String input = scanner.nextLine();

       /* ParseExpession parser = new ParseExpession(input);
       // Expression expr = parser.parse();

        expr.print();
        System.out.println();

        int result_scan = expr.eval("x=10; y=5");
        System.out.println(result_scan);*/
    }
}