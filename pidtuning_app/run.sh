#!/bin/bash

# Temporary Run Script
cd bin
java -cp '.:../lib/ntcore.jar:../lib/wpiutil.jar' -Djava.library.path='../lib/' PIDTuningApp
