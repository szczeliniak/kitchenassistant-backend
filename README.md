# KitchenAssistant

## Recipes storage and shopping list management API

## Endpoints

All endpoints and available at `/swagger-ui.html`

## Run dev application

1. Run `docker-compose -f docker-compose-infra.yaml up -d` to start DB, FTP and Adminer containers.
2. Run application context in Intellij `KitchenAssistantApplication.kt` with profile `dev` (VM options: `-Dspring.profiles.active=dev`).

### Run prod application

1. Run `docker-compose -f docker-compose-infra.yaml up -d` to start DB, FTP and Adminer containers.
2. Build docker image with command `docker build -t kitchenassistant-app .`
3. Run docker image and provide environment variables:
    * `DB_HOST` - database host
    * `DB_PORT` - database port
    * `DB_NAME` - database name
    * `DB_USER` - database user name
    * `DB_PASSWORD` - database user password
    * `JWT_SECRET` - jwt secret
    * `FTP_HOST` - ftp host
    * `FTP_PORT` - ftp port
    * `FTP_USER` - ftp user name
    * `FTP_PASSWORD` - ftp password

Application works at `8080` port by default