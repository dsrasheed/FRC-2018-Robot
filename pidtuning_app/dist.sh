#!/bin/bash
cp -R lib/* bin/
cd bin
jar -cfm ../PIDTuningApp.jar ../manifest *

