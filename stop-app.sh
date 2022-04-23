#!/bin/sh

#stop app
docker-compose -f docker-compose-app.yaml down

#stop ftp
docker-compose -f docker-compose-ftp.yaml up -d

#stop db
docker-compose -f docker-compose-db.yaml down