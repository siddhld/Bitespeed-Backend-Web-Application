# Bitespeed-Backend-Web-Application
Bitespeed is a backend web application built with Java and Spring Boot. This application manages contact data and implements various scenarios to handle contacts with email and phone number information.

## Prerequisites
Before running the application, make sure you have the following installed:

1. Java Development Kit (JDK) 17 (that is used in this application)
2. MySQL database server
3. IDE (IntelliJ IDEA, Eclipse, etc.) for running and managing the project (optional but recommended)


## Getting Started
Follow the steps below to run the Bitespeed backend web application:

1. Clone this GitHub repository to your local machine using the following command:
2. https://github.com/siddhld/Bitespeed-Backend-Web-Application.git
3. Import the project into your IDE (optional but recommended) and ensure all dependencies are resolved.
4. Update the `application.properties` file with your MySQL database connection details. Open the `src/main/resources/application.properties` file and modify the following properties accordingly:
5. The application will start, and you will see log messages indicating that the server is up and running.


## How to Use
Once the application is running, you can access the endpoints via HTTP requests to perform CRUD operations on contacts. Below is the API endpoint provided by the application:

- **POST** `/api/identify`: Use this endpoint to identify and manage contacts based on the given scenarios. Send a JSON payload with `email` and `phoneNumber` fields to identify contacts.

## Important Notes
Please take note of the following before running the application:

- Ensure that your MySQL database is up and running with the provided connection details in `application.properties`.

- The application is set to use `update` for `spring.jpa.hibernate.ddl-auto` in `application.properties`, which means the database schema will be automatically updated. Take caution when running this application on a production database.

- Make sure you have Java 17 installed on your machine.

