# Language Spotter REST API
## About the project
Language Spotter is a REST API for a language learning platform. An example below shows one of possible ways how it may work:
1) Choose a language you want to learn.
2) Get a list of available countries and choose one where you would like to study.
3) Get a list of cities of a chosen country and pick one.
4) Get a list of education centers where you can learn the language and select one.
5) Choose a course that would suit you best.
6) At last, choose accommodation provided by the education center.

As a result: English -> Ireland -> Dublin -> Erin School Of English -> General English Course / Hostel.

## Running locally
Language Spotter is a [Spring Boot](https://spring.io/guides/gs/spring-boot) application built using [Maven](https://spring.io/guides/gs/maven). You can build a jar file and run it from the command line:
```
git clone https://github.com/vl-blinov/spring-language-spotter.git
cd spring-language-spotter
./mvnw package
java -jar target/*.jar
```
<ins>IMPORTANT NOTE</ins>: you should have a Docker running on your computer!

Or you can run it from Maven directly using the [Spring Boot Maven plugin](https://docs.spring.io/spring-boot/docs/current/maven-plugin/reference/htmlsingle):
```
./mvnw spring-boot:run
```

## Documentation
API documentation is generated using [Swagger](https://swagger.io/tools/swagger-ui). While the application is running you can visit Swagger UI page:
```
http://localhost:8080/swagger-ui/index.html
```

## Built with
* [Spring Boot](https://spring.io/projects/spring-boot)
* [Spring Data JPA](https://spring.io/projects/spring-data-jpa)
* [Springdoc OpenAPI UI](https://springdoc.org)
* [H2 Database](https://www.h2database.com/html/main.html)
* [Flyway](https://flywaydb.org)
* [Docker](https://www.docker.com)

## Database
Language Spotter uses an in-memory database (H2) which gets populated at startup with data using Flyway. The h2 console is automatically exposed at ```http://localhost:8080/h2-console``` and it is possible to inspect the content of the database using the ```jdbc:h2:mem:testdb``` url.\
Also, the application uses a PostgreSQL testcontainer in tests.

## Working in IDE

### Prerequisites
The following items should be installed in your system:
* JDK 17 or newer
* Eclipse IDE
* [Git command line tool](https://help.github.com/articles/set-up-git)
* [Docker](https://docs.docker.com/get-docker)

### Steps
1) On the command line:
```
git clone https://github.com/vl-blinov/spring-language-spotter.git
```
2) Inside Eclipse IDE:
```
File -> Import -> Maven -> Existing Maven Projects
```
3) Run the application main method by right clicking on it and choosing ```Run As -> Java Application```.
4) Use [Postman](https://www.postman.com) or Swagger UI page ```http://localhost:8080/swagger-ui/index.html``` to test API.
