#!/bin/sh

./build-docker-image.sh

#run db
docker-compose -f docker-compose-db.yaml up -d

#run app
docker-compose -f docker-compose-app.yaml up -d