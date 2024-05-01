# RESTful  Account service

Account service is a Spring Boot application designed to
transfer money between users.<br>

***

## What data service stores?

<li>User data that consists of name, unique document number and its type (passport or driver's license)</li>
<li>User accounts that consist of current amount of money and currency of the account</li>
<li>All transactions data of user including the type of operation (deposit or withdraw), 
the amount of money, the currency of transaction, the date and time of the transaction</li>

***

## Possible actions that service provides:

<li>Adding funds to the user's account</li>
<li>Transfer money to any user</li>

***

## Technologies that were used in the project:

<li>Java 21</li>
<li>Spring Boot 3.2.5</li>
<li>Gradle for building the project</li>
<li>PostgreSQL</li>
<li>Test containers for integrational testing</li>
<li>Liquibase for DB migrating</li>
<li>Open API 3.0</li>
<li>Docker Compose for running application in a container</li>

***

## Build and run locally

**1. Clone the repository**

```git clone <repository-url>```

**2. Navigate to file application.yml**
<br>
There you can find application properties. One of the url's is commented.
<br><br>
If you want to run app locally that you need to uncomment line:
<br>
```url: jdbc:postgresql://localhost:5432/tehnovsky_task```
<br>
And also you need to comment line:
<br>
```url: jdbc:postgresql://postgres:5432/tehnovsky_task```
<br>
<br>
So, after this your **application.yml** will look like this:
<br>

```
spring:
  liquibase:
    enabled: true

  datasource:
    driver-class-name: org.postgresql.Driver
    username: postgres
    password: postgres
    url: jdbc:postgresql://localhost:5432/tehnovsky_task
  #    url: jdbc:postgresql://postgres:5432/tehnovsky_task

  jpa:
    hibernate:
      ddl-auto: none
    properties:
      hibernate:
        show_sql: true
        format_sql: true

  application:
    name: task

logging:
  file:
    name: app.log

```

### Note:

If you selected this way then you need to set up locally database with name **tehnovsky_task**
<br>

***

## Run in Docker

**1. Clone repo as written above**
<br>
<br>
**2. Navigate to application.yml**
<br><br>
As you want to run app in Docker then you need to uncomment line:
<br>

```
url: jdbc:postgresql://postgres:5432/tehnovsky_task
```

And also you need to comment line:

```
url: jdbc:postgresql://localhost:5432/tehnovsky_task
```

So, after this your **application.yml** will look like this:
<br>

```
spring:
  liquibase:
    enabled: true

  datasource:
    driver-class-name: org.postgresql.Driver
    username: postgres
    password: postgres
    #    url: jdbc:postgresql://localhost:5432/tehnovsky_task
    url: jdbc:postgresql://postgres:5432/tehnovsky_task

  jpa:
    hibernate:
      ddl-auto: none
    properties:
      hibernate:
        show_sql: true
        format_sql: true

  application:
    name: task

logging:
  file:
    name: app.log
```
**3. Clean and build the project:**

```
./gradlew clean build
```

**4. Start the application using Docker Compose:**

```
docker-compose up
```

The application will be accessible at http://localhost:8080.

---

## Documentation

You can access Swagger-UI for API documentation by this link:
http://localhost:8080/swagger-ui/index.html

---

## Examples of test requests using Postman
**1. Get user by id**
<br>
<br>
Request type is **GET**
<br>
Example:
```
localhost:8080/api/v1/users/1
```
**2. Top up  user balance**
<br>
<br>
Request type is **POST**
<br>
Example:
```
{
    "userId": 2,
    "amountOfMoney": 100,
    "currency": "BYN"
}
```
**3. Send money to user**
<br>
<br>
Request type is **POST**
<br>
Example:
```
{
    "senderUserId": 2,
    "senderCurrency": "USD",
    "receiverUserId": 1,
    "amount": 100
}
```

