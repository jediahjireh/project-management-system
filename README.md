# Poised Project Management System

## Table of Contents

- [What?](#what)
  - [Documents](#documents)
  - [Technologies](#technologies)
  - [Features](#features)
- [Why?](#why)
- [How?](#how)
- [Where?](#where)
- [Who?](#who)

## What ?

The Poised Project Management System is a programme created with Java and SQL, using JDBC, designed to allow `Poised` (a fictitious company) to efficiently manage various projects by storing and organising essential project information (such as project details, client information, deadlines and contacts of architects and contractors).

### Documents

- [Client Requirements](/docs/client-requirements.pdf)
- [Project Entity Relationship Diagram (ERD)](/docs/PoisePMS-entity-relationship-diagram.pdf)
- [API Documentation](/docs/API-documentation/index.html)
- [Potential Improvements](/docs/potential-improvements.txt)

### Technologies

- **Java**: The primary programming language used for implementing the system's core functionality.
- **SQL**: Used for database operations and management.
- **JDBC (Java Database Connectivity)**: Enables interaction between Java applications and the MySQL database.
- **MySQL**: Database management system used to store project data.
- **MySQL Workbench**: Tool used for managing MySQL databases and visualising database structure.

### Features

- **Diverse Skill Set**: Proficient in Java and SQL, with hands-on experience using JDBC for database interactions.
- **Attention to Detail**: Implemented a structured and efficient database design, including [ERD](/docs/PoisePMS-entity-relationship-diagram.pdf) and detailed [SQL scripts](/src/PoisePMS.sql).
- **Problem-Solving**: Developed functionality to capture, update, finalise and manage project data effectively.
- **Code Quality**: Emphasised readable and well-documented code. Clear comments and documentation ensure that the codebase is easy to understand and extend.
- **Console-Based Interactions**: The user interface is designed for intuitive use, with clear prompts and error handling. Input validation is implemented to guide users effectively and ensure accurate data entry.

## Why ?

The programme streamlines the process of project management for `Poised`, enabling them to effectively track the progress of each project, make necessary updates and ensure timely completion.
By providing a centralised platform to store and manage project data, the system:

- Enhances productivity
- Facilitates communication between stakeholders
- Improves overall project management efficiency

This project showcases my understanding of SQL and ability to interact with a database from a Java programme using the JDBC API.
The programme enables users to:

- Add new projects
- Update project details
- Delete projects
- Search for specific projects

## How ?

To get started with the Poised Project Management System, follow these steps:

1. Clone the repository to your local machine: git clone https://github.com/jediahjireh/project-management-system.git

2. Ensure that you have a MySQL Workbench installed on your computer.

3. Navigate to your MySQL bin directory and log into the MySQL Server from the terminal.

4. Run the SQL source file [PoisePMS.sql](/src/PoisePMS.sql) in the terminal to create the database structure and triggers.

- ![Populated Projects Table](/docs/screenshots-of-console/insert-into-projects-table.png)
- ![Populated Architects Table](/docs/screenshots-of-console/insert-into-architects-table.png)
- ![Populated Contractors Table](/docs/screenshots-of-console/insert-into-contractors-table.png)
- ![Populated Customers Table](/docs/screenshots-of-console/insert-into-customers-table.png)

5. Open the project in your preferred Java IDE (Integrated Development Environment).

6. Swap out the last two parameters in the connection string with your MySQL Server username and password.

7. Compile and run the `PoisePMS.java` file to start the programme.

8. Follow the prompts within the programme created with to perform various tasks, such as:
   - Capturing information about new projects
   - Updating existing projects
   - Finalising projects
   - Accessing project data

## Where ?

If you encounter any issues or have questions about this project, feel free to reach out for assistance.

- Refer to the project's documentation within the repository.
- Open an issue on the GitHub repository to report bugs or request features.
- Contact me directly via email at [jediahnaicker@gmail.com](mailto:jediahnaicker@gmail.com).

## Who ?

Project created by: [Jediah Jireh Naicker](https://github.com/jediahjireh)

I have been tasked with the creation and maintenance of this project management system for `Poised`, a small (fictitious) structural engineering company. Note that this programme has been developed and tailored according to the client's requests and specifications but if you are interested in contributing to it or have further suggestions, you're welcome to contact me.

Happy Coding!
