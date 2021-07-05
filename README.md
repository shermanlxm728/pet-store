# Pet Store
This repo is an application for a local pet store. The store needs to manage the names, types, and price of all pets. 

The requirements are:

The app is built with Spring Boot

Each pet at the shop include the following attributes – name, type, price. For example: name:Max, type:dog, price:15

The following functionality are exposed via RESTful APIs

Get a specific pet: localhost:8080/pet/{id}

Get a list of pets (max limit of 100 returned): localhost:8080/pets

Create a new pet: localhost:8080/pet

Update a pet (like its name or price change): localhost:8080/pet/{id} 

Delete a pet: localhost:8080/pet/{id}

Pets are stored in h2 database. The database are populated with following data on startup:

![image](https://user-images.githubusercontent.com/86971702/124503302-44416700-dd93-11eb-9a5a-a7d35cae0f01.png)


Tests are be included with the code.

All code should be available for review in a git repository of your choosing. Include documentation on how to run the app
