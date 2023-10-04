#!/bin/bash

echo 'Building...'
gradle -p '/backend' -I '/workdir/init.gradle.kts' --no-daemon clean build

echo 'Copying jar...'
cp -f '/workdir/build/libs/hardloan-all.jar' '/webapp/hardloan.jar'

echo 'Copying web resources...'
rm -rf '/webapp/www'
cp -rf '/frontend' '/webapp/www'

echo 'Exit.'
