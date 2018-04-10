#!/bin/sh

cd $(dirname $0)/..
[ -d doc ] || mkdir doc
javadoc -d doc src/*/*.java