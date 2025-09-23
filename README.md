# API-TASK1
- Create a Spring Boot project with REST API support and connect it to a MySQL database.

- Define Entities:
  - Student → id, firstName, lastName, email
  - Course → id, title, description

- Each student can enroll in multiple courses (many-to-many relationship).

- Implement Repositories for both entities using Spring Data JPA.

- Create Services for handling business logic of Students and Courses.

- Build REST Controllers with the following endpoints:
  - Add, update, delete a student
  - Add, update, delete a course
  - Get all students
  - Get all courses
  - Get all courses for a student
  - Enroll a student into a course

- Implement Exception Handling for cases like student not found, course not found, or duplicate enrollment.

- Test the API with postman.
