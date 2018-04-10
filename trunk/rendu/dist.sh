#!/bin/sh

workspace_dir="src"
ressources_dir="ressources"
output_dir="Rendu_beauchamp-mori-chagneux-leblond"

# Cleans up previous version
[ -d ${output_dir} ] || mkdir ${output_dir}
rm -rf ${output_dir}/*

# Copies source files
basedir=$(pwd)
cd ${output_dir}
mkdir src
cd ${basedir}
cd ${workspace_dir}
find . -name "*.java" -exec cp --parents {} ${basedir}/${output_dir}/src \;
cd ${basedir}
cd ${output_dir}
mkdir ressources
cd ${basedir}
cd ${ressources_dir}
find . -name "*" -exec cp --parents {} ${basedir}/${output_dir}/ressources \;
cd - > /dev/null

# Copies scripts
mkdir ${output_dir}/scripts
cp -f scripts/*.sh ${output_dir}/scripts/

# Copies readme
cp README.txt ${output_dir}/

# Makes all files in dist readonly so as to avoid editing them
find ${output_dir} -type f -exec chmod u-w {} \;
