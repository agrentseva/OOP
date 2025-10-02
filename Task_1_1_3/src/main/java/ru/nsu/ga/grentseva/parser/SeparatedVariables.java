package ru.nsu.ga.grentseva.parser;

import java.util.HashMap;
import java.util.Map;

public class SeparatedVariables {

    private final Map<String, Integer> vars = new HashMap<>();

    public SeparatedVariables(String varsStr){
        if (varsStr == null || varsStr.trim().isEmpty()) {
            throw new IllegalArgumentException("Error: The string is empty");
        }

        varsStr = varsStr.replaceAll(" ","");
        String[] pairs = varsStr.split(";");
        for (String pair : pairs){
            if (pair.isEmpty()) continue;

            String[] key_var = pair.split("=");
            String var = key_var[0];
            int number = Integer.parseInt(key_var[1]);
            vars.put(var, number);
        }
    }

    public Map<String, Integer> getVars() {
        return vars;
    }

}
