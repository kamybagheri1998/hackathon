#!/bin/bash

echo 'Cleaning old files...'
rm -rf '/workdir/build'
rm -rf '/webapp/www'
rm -f '/webapp/hardloan.jar'

mkdir 'build'

echo 'Building...'
gradle -p '/backend' -I '/workdir/init.gradle.kts' --no-daemon clean build -x test

echo 'Copying jar...'
cp -f '/workdir/build/libs/hardloan-all.jar' '/webapp/hardloan.jar'

echo 'Copying web resources...'
cp -rf '/frontend' '/webapp/www'

echo 'Exit.'
