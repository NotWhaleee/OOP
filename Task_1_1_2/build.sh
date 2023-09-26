#!/usr/bin/env sh

javadoc -d docs ./src/main/java/org/example/Main.java
javadoc -d docs ./src/main/java/org/example/Polynomial.java

mkdir -p build
javac ./src/main/java/org/example/Main.java ./src/main/java/org/example/Polynomial.java -d build
# shellcheck disable=SC2164
cd build
java org.example.Main