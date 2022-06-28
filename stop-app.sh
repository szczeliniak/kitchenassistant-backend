#!/bin/sh

#stop app
docker-compose -f docker-compose-app.yaml down

#stop infra
docker-compose -f docker-compose-infra.yaml down