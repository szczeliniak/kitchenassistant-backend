#!/bin/sh

./build-docker-image.sh

#run db
docker-compose -f docker-compose-db.yaml up -d

#run ftp
docker-compose -f docker-compose-ftp.yaml up -d

#run app
docker-compose -f docker-compose-app.yaml up -d