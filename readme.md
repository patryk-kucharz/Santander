Challenge: Create a Java microservice to manage items from a shop. This microservice will expose an
API to CRUD items. Item model is open, use your creativity.
The expected outcome is:
1. one endpoint to create one item; POST /items
2. one endpoint to create multiple items; POST /items/collections
3. one endpoint to delete an item; DELETE /items/{sku}
4. one endpoint to update an item; PUT /items/{sku}
5. one endpoint to fetch an item by id, name and some other field of your choice; GET /items/filters?sku=&name=&productGroup=
6. one endpoint to fetch all items(tip: can be a LOT of items); GET /items?page=&size=
   
What your solution should contain:
1. source code in java 8 or higher (we recommend the usage of springboot with maven);
2. the items should be saved on a database of your choice(can be an in-memory database);
3. an OpenAPI/Swagger must be provided;
4. tests. Keep in mind that we are not looking for high coverage but different types of tests;
5. a generic http trace should be sent to a queue/topic to be consumed by a magic been. You
   can use any messaging system (kafka, rabbitmq, jms);
6. no need for a fancy solutions, just keep it simple and clean;
7. endpoints secured (any security is accepted);
8. readme.md file with running instructions;
   Nice to have:
9. use cache layer -
10. your service runs in a container +

Use git bundle to create a file and send it to us by e-mail.

0. Prerequisites:
   1. Java 20 installed
   2. Docker-compose installed
   3. Docker Desktop installed
1. Run in a terminal: mvnw clean install
2. Run in a terminal: docker-compose up -d
3. The endpoints are secured with BASIC authentication USER:PASS