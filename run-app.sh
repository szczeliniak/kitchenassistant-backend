#!/bin/sh

#build image
./build-docker-image.sh

#run infra
docker-compose -f docker-compose-infra.yaml up -d

#run app
docker-compose -f docker-compose-app.yaml up -d