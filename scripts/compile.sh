#!/bin/sh

cd $(dirname $0)/..
[ -d build ] || mkdir build
cd build
[ -d ressources ] || mkdir ressources
cd ../src
javac -d ../build */*.java