#!/usr/bin/env sh

javadoc -d docs ./src/main/java/ru/nsu/kozorez/pack/Main.java
javadoc -d docs ./src/main/java/my/pack/Heapsort.java

mkdir -p build
javac ./src/main/java/ru/nsu/kozorez/Main.java ./src/main/java/ru/nsu/kozorez/Heapsort.java -d build
# shellcheck disable=SC2164
cd build
java my.pack.Main
