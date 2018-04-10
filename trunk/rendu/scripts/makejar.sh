#!bin/sh

cd $(dirname $0)/..
sh scripts/compile.sh
[ -d jar ] || mkdir jar
cd build
jar cfe ../jar/Sokoban.jar graphique.Main .
chmod +x ../jar/Sokoban.jar
