package ru.nsu.ga.grentseva.checker.dsl;

import groovy.lang.Binding;
import groovy.lang.GroovyShell;
import org.codehaus.groovy.control.CompilerConfiguration;
import ru.nsu.ga.grentseva.checker.model.CourseConfig;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;

public class ConfigDslParser {

    public static CourseConfig parse(Path scriptPath) throws IOException {

        CourseConfig config = new CourseConfig();

        loadFile(scriptPath, config);

        return config;
    }

    public static void loadFile(Path path,
                                 CourseConfig config) throws IOException {

        File file = path.toFile();

        if (!file.exists()) {
            throw new IOException(
                    "Файл конфигурации не найден: "
                            + file.getAbsolutePath()
            );
        }

        CompilerConfiguration configuration =
                new CompilerConfiguration();

        configuration.setScriptBaseClass(
                ConfigScript.class.getName()
        );

        configuration.setSourceEncoding("UTF-8");

        GroovyShell shell = new GroovyShell(
                ConfigDslParser.class.getClassLoader(),
                new Binding(),
                configuration
        );

        ConfigScript script =
                (ConfigScript) shell.parse(file);

        script.setConfig(config);

        Path basePath = path.getParent();

        if (basePath == null) {
            basePath = Path.of(".");
        }

        script.setBasePath(basePath);

        script.run();
    }
}