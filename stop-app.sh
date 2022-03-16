#!/bin/sh

#stop app
docker-compose -f docker-compose-app.yaml down

#stop db
docker-compose -f docker-compose-db.yaml down