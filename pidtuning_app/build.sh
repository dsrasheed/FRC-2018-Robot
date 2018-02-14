#!/bin/sh

# Temporary build script
cd src
javac -cp '.:../lib/ntcore.jar' *.java
cd ..
if [ ! -d 'bin/' ]; then
    mkdir bin
fi
mv src/*.class bin/
cp src/*.fxml  src/*.css bin/

