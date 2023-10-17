#!/usr/bin/env sh

javadoc -d docs ./src/main/java/ru/nsu/kozorez/Main.java
javadoc -d docs ./src/main/java/ru/nsu/kozorez/Tree.java

mkdir -p build
javac ./src/main/java/ru/nsu/kozorez/Main.java ./src/main/java/ru/nsu/kozorez/Tree.java -d build
# shellcheck disable=SC2164
cd build
java ru.nsu.kozorez.Main