#!/bin/sh

#build app
mvn clean install

#build image
docker build -t kitchenassistant-app .