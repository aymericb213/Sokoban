#!/bin/sh

cd $(dirname $0)/..
[ -d doc ] || mkdir doc
javadoc -charset utf8 -d doc src/*/*.java
