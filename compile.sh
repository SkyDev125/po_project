#!/bin/sh

# Compile Java files
find hva -name "*.java" -print | xargs javac -cp po-uilib.jar:.

# Check if the compilation was successful
if [ $? -eq 0 ]; then
    # Run the Java application
    java -cp po-uilib.jar:. hva.app.App
else
    echo "Compilation failed."
    exit 1
fi