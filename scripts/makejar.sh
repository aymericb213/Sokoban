#!bin/sh

cd $(dirname $0)/..
sh scripts/compile.sh
[ -d jar ] || mkdir jar
cd build
jar cf ../jar/ia.jar
jar cf ../jar/graphique.jar
jar cf ../jar/sokoban.jar .
