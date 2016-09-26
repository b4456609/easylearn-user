#!bin/bash
./gradlew bootRepackage
docker build -t b4456609/easylearn-user:latest -t b4456609/easylearn-user:${1} .
