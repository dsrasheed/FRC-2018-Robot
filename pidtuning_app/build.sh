#!/bin/sh
cd src
javac -cp '.:../lib/ntcore.jar' *.java
cd ..
mkdir bin
mv src/*.class src/*.fxml bin/

