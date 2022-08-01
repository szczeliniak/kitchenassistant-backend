# KitchenAssistant

## Recipes storage and shopping list management API

Application allows for

* basic user registration with email and password
* authenticate with JWT token
* storing recipes
* searching for recipes with criteria
* adding recipe steps
* adding recipe ingredients
* adding photos for recipes and particular recipe steps
* recipe tagging
* splitting recipes into categories
* adding shopping lists
* searching for shopping lists with criteria
* marking shopping list items as completed
* archiving shopping lists

## Endpoints

All endpoints and available at **/swagger-ui.html**

## Run application

There are 2 ways of spinning application up:

1. Intellij
2. Docker

### Intellij

1. In root directory run **docker-compose -f docker-compose-ftp.yaml up -d** to start a FTP server locally which is
   required by application to handle storing photos.
2. Run application context in Intellij **KitchenAssistantApplicationTest.kt**. Application properties are configured by default for local environment.

### Docker

Run **run-app.sh** to build application image, start up PostgreSQL database, FTP server and application itself in one command.
By default it runs in **development mode**. To run it in **production mode** remove **dev** profile.

If you want to stop application run **stop-app.sh** stops all containers mentioned above.