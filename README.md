# Library Management System API
This document provides instructions on how to run the Library Management System API, interact with its endpoints, and use authentication.

## Running the Application:

### Using Maven:
1. Clone the repository:
    ```
    git clone https://github.com/ahmedsamirsaid/Library-Management-System.git
    ```

2. Navigate to the project directory:
    ```
    cd Library-Management-System
    ```

3. Build the project using Maven:
    ```
    mvn clean install
    ```

4. Run the Spring Boot application:
    ```
    mvn spring-boot:run
    ```

5. Once the application is running, you can access it at `http://localhost:8080`.

### Using JAR file:

1. Clone the repository:
    ```
    git clone https://github.com/ahmedsamirsaid/Library-Management-System.git
    ```

2. Navigate to the project directory:
    ```
    cd Library-Management-System
    ```
3. Run the JAR file:
    ```
    java -jar library-management.jar
    ```

4. Once the application is running, you can access it at `http://localhost:8080`.
## Interacting with API Endpoints:

- **Books Endpoints:**
    - **GET /api/books**: Retrieve a list of all books.
    - **GET /api/books/{id}**: Retrieve details of a specific book by ID.
    - **POST /api/books**: Add a new book to the library.
    - **PUT /api/books/{id}**: Update an existing book's information.
    - **DELETE /api/books/{id}**: Remove a book from the library.

- **Patrons Endpoints:**
    - **GET /api/patrons**: Retrieve a list of all patrons.
    - **GET /api/patrons/{id}**: Retrieve details of a specific patron by ID.
    - **POST /api/patrons**: Add a new patron to the system.
    - **PUT /api/patrons/{id}**: Update an existing patron's information.
    - **DELETE /api/patrons/{id}**: Remove a patron from the system.

- **Borrowing Endpoints:**
    - **POST /api/borrow/{bookId}/patron/{patronId}**: Allow a patron to borrow a book.
    - **PUT /api/return/{bookId}/patron/{patronId}**: Record the return of a borrowed book by a patron.

## Authentication:

The API endpoints is protected with authentication. In this case, you'll need to obtain an authentication token before accessing the secured endpoints. Here's how you can authenticate:

1. Make a POST request to `/auth/authenticate` endpoint with valid credentials (username and password).
2. The username and password for the admin are:
    ```
    username: admin
    password: admin
    ```
   and for the user are:
    ```
    username: user
    password: user
    ```

3. If authentication is successful, the response will contain a JWT token.
4. Include this token in the header of subsequent requests to the secured endpoints using the `Authorization` header:
    ```
    Authorization: Bearer <token>
    ```
5. The endpoints that require admin authentication are:
    - **POST /api/books**
    - **PUT /api/books/{id}**
    - **DELETE /api/books/{id}**
    - **GET /api/patrons**
    - **DELETE /api/patrons/{id}**

**Note:** Ensure to replace `<token>` with the actual JWT token obtained during authentication.

With these instructions, you should be able to run the Library Management System API, interact with its endpoints, and authenticate if necessary.
