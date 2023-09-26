#!/usr/bin/env sh

javadoc -d docs ./src/main/java/my/pack/Main.java
javadoc -d docs ./src/main/java/my/pack/Heapsort.java

mkdir -p build
javac ./src/main/java/my/pack/Main.java ./src/main/java/my/pack/Heapsort.java -d build
# shellcheck disable=SC2164
cd build
java my.pack.Main
