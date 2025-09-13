#!/bin/bash

mkdir -p my_build documentation

#compile
javac -d my_build src/main/java/ru/nsu/ga/grentseva/sort/*.java

#documentation
javadoc -d documentation -sourcepath src/main/java ru.nsu.ga.grentseva.sort

#JAR-file
jar cfe app.jar ru.nsu.ga.grentseva.sort.Main -C my_build .

#start
java -jar app.jar