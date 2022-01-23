# transaction-service

## Description
This repository contains the source code of transaction service api written with Spring & Spring Boot SpringBoot.

## Prerequisite
- Spring Framework 5.3.15
- Spring Boot 2.6.3
- Spring Data JPA
- Hibernate ORM
- Hibernate Validation API
- Model Mapper
- PostgreSQL
- JUnit & Mockito
- Gradle

## Screens
There are 5 API endpoints in the transaction-service api:
* **Post Transaction** - `POST http://localhost:8080/api/v1/transactions`
* **Update Transaction** - `PUT http://localhost:8080/api/v1/transactions/{id}`
* **Delete Transaction** - `DELETE http://localhost:8080/api/v1/transactions/{id}`
* **Get Transaction** - `GET http://localhost:8080/api/v1/transactions/{id}`
* **Get Paginated Transactions** - `GET http://localhost:8080/api/v1/transactions`

#### Post Transaction
![Post Transaction Screenshot](https://github.com/ajitata6f/transaction-service/blob/main/assets/img/Post_Transaction.png?raw=true)

#### Update Transaction
![Post Transaction Screenshot](https://github.com/ajitata6f/transaction-service/blob/main/assets/img/Update_Transaction.png?raw=true)

#### Delete Transaction
![Delete Transaction Screenshot](https://github.com/ajitata6f/transaction-service/blob/main/assets/img/Delete_Transaction.png?raw=true)

### Get Transaction
![Get Transaction Screenshot](https://github.com/ajitata6f/transaction-service/blob/main/assets/img/Get_Transaction.png?raw=true)

#### Get Transactions
![Get Transactions Screenshot](https://github.com/ajitata6f/transaction-service/blob/main/assets/img/Get_Transactions.png?raw=true)

## Running the server locally ##
To be able to run this Spring Boot app you will need to first build it. To build and package a Spring Boot app into a single executable Jar file with Gradle, use the below command. You will need to run it from the project folder which contains the gradle.build file.

```
./gradlew build
```
or you can also use

```
./gradlew bootJar
```

To run the Spring Boot app from a command line in a Terminal window you can you the java -jar command. This is provided your Spring Boot app was packaged as an executable jar file.

```
java -jar target/transaction-service-1.0.0.jar
```

You can also use Maven plugin to run the app. Use the below example to run your Spring Boot app with Maven plugin :

```
./gradlew bootRun
```

## Running the server in Docker Container ##
##### Docker #####
Command to pull the image:

```
docker pull ajitata6f/transaction-service
```

Command to run the container :

```
docker run -p 8080:8080 ajitata6f/transaction-service
```

Please **note** when you build the container image and if postgres is running locally on your system, you will need to provide user credentials in the application.yml file to be able to connect to the database from within the container.

Clone from repository
-------------------

       $ git clone https://github.com/ajitata6f/transaction-service.git


