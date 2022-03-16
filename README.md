# KitchenAssistant

## Receipts storage and shopping list management API

### Endpoints

Available at **/swagger-ui.html**

### Run application

* **build-docker-image.sh** installs application and builds docker image

* **run-app.sh** runs database and app in containers
* **stop-app.sh** removes database and app containers

To run just database and app seperately in container use commands respectively

* **docker-compose -f docker-compose-db.yaml up -d**
* **docker-compose -f docker-compose-app.yaml up -d**

To debug application run start db container and run application test context **KitchenAssistantApplicationTest.kt** via
Intellij in debug mode 