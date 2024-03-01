# My Uni
## Project Overview
My Uni is an MIS (Management Information System) for any university. It allows a university to maintain a record of courses it offers under different departments, students enrolled and teachers and professors under different department. It is still under development stage.
## Features
**CRUD Operations**
- Implemented Create, Read, Update and Delete operations for University, Course and Student entities so far.

**Authentication**
- Implemented JWT authentication for Users (Student as of now).
- Encoded user's password before saving to the database using Bcrypt.

**Tasks completed**
- Created the entities Student, University and Courses along with their respective DTOs, controller and service functions allowing CRUD operations.
- Tested the APIs using JUnit5 and Mockito on both the controller and the service layer.
- Documented the APIs using SwaggerUI.

**Tasks Remaining**
- Create two more entities Department and Teacher and define their relation with each other and the existing entities.
- Implement sorting and filtering functionalities like sorting universities by their rank, courses by their credit hours filtering universities by a particular course etc.

## Stacks Used

- Programming Language: Java
- Framework: Spring Boot
- Database: PostgreSQL

## Getting Started
**1. Clone the repository**

    git clone git@github.com:Pratik333-droid/My-Uni.git
**2. Run the application**

    ./mvnw spring-boot:run
**3. Run test cases**

Run all the test cases using the following command.

    ./mvnw test
**4. Access the API Documentation**

Open a web browser and enter the URL http://localhost:8080/swagger-ui/index.html to access the API documentation. 