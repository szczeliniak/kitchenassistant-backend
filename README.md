# KitchenAssistant

## Recipes storage and shopping list management API

## Endpoints

All endpoints and available at **/swagger-ui.html**

## Run application

There are 2 ways of spinning application up:

1. Intellij
2. Docker

### Intellij

1. In root directory run **docker-compose -f docker-compose-infra.yaml up -d** to start DB, FTP and Adminer containers.
2. Run application context in Intellij **KitchenAssistantApplication.kt** with profile 'dev' (VM options: -Dspring.profiles.active=dev).

### Docker

Run **run-app.sh** to build application image, start up DB, FTP server and application itself in one command.
By default it runs in **development mode**. To run it in **production mode** remove **dev** profile in docker-compose-app.yml.

If you want to stop application run **stop-app.sh** stops all containers mentioned above.

Before you start an application up, verify if ports are ready for binding (by default it is 8080 for application, 21 for FTP, 5432 for DB)