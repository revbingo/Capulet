#!/bin/bash

rm -rf dist
mkdir dist

javac -cp lib/gson-2.2.2.jar src/com/revbingo/capulet/*.java -d dist

jar cf dist/capulet.jar dist/* 
