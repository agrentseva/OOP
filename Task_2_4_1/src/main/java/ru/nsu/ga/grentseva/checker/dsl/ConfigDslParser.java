package ru.nsu.ga.grentseva.checker.dsl;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.ga.grentseva.checker.model.Config;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ConfigDslParser {

    public static Config parse(Path scriptPath) throws IOException {
        Config config = new Config();
        parseFile(scriptPath, config);
        return config;
    }

    public static void parseFile(Path path, Config config) throws IOException {
        File file = path.toFile();

        if (!file.exists()) {
            throw new IOException("Файл конфигурации не найден: " + file.getAbsolutePath());
        }

        CompilerConfiguration compilerConfig = new CompilerConfiguration();
        compilerConfig.setScriptBaseClass(ConfigScript.class.getName());
        compilerConfig.setSourceEncoding("UTF-8");

        GroovyShell shell = new GroovyShell(
                ConfigDslParser.class.getClassLoader(),
                new Binding(),
                compilerConfig
        );

        ConfigScript script = (ConfigScript) shell.parse(file);

        script.setConfig(config);
        script.setBasePath(path.getParent());

        script.run();
    }
}