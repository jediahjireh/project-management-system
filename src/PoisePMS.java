
// import Java packages
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.time.LocalDate;
import java.util.InputMismatchException;
import java.util.Scanner;

/*
 * refer to the 'potential-improvements.txt' file in the 'docs' directory
 * for a detailed list of potential improvements and suggested refactoring
 * for this file ('PoisePMS.java').
 */

/**
 * the PoisePMS class handles information about company projects and the people
 * associated with those projects (architects, contractors and customers)
 * 
 * functionalities for viewing, adding, updating and
 * searching for the project-related data are included
 */
public class PoisePMS {

    /**
     * main method to run the Poised Project Management System
     * connect to the PoisePMS database
     * present a menu to the user
     * execute actions based on the user's input
     * 
     * @param args command-line arguments (not used)
     * @throws Exception if an error occurs during database connection or SQL
     *                   operations
     */
    public static void main(String[] args) throws Exception {
        // try-catch block for error handling
        try {
            // connect to PoisePMS database via jdbc:mysql: channel on localhost
            Connection connection = DriverManager.getConnection(
                    // database url
                    "jdbc:mysql://localhost:3306/PoisePMS?useSSL=false",
                    // username
                    "otheruser",
                    // password
                    "swordfish");

            // create a direct line to the database for running queries
            Statement statement = connection.createStatement();

            // create scanner
            Scanner scanner = new Scanner(System.in);

            // declare and initialise option variable
            int option = 0;

            // repeat prompt until valid input entered and after selected task is performed
            do {
                // present the user with the menu options
                System.out.println("\nPoised Project Management System Menu:");
                // options to view tables
                System.out.println("1. View All Projects");
                System.out.println("2. View All Customers");
                System.out.println("3. View All Architects");
                System.out.println("4. View All Contractors");
                System.out.println();
                // option to change status of project completion
                System.out.println("5. Finalise Project");
                // options to find specific projects according to criteria
                System.out.println("6. Find Incomplete Projects");
                System.out.println("7. Find Overdue Projects");
                System.out.println("8. Search Projects");
                System.out.println();
                // options to update data in tables
                System.out.println("9. Update Project Details");
                System.out.println("10. Update Customer Details");
                System.out.println("11. Update Architect Details");
                System.out.println("12. Update Contractor Details");
                System.out.println();
                // options to add new records to tables
                System.out.println("13. Add New Project");
                System.out.println("14. Add New Customer");
                System.out.println("15. Add New Architect");
                System.out.println("16. Add New Contractor");
                System.out.println();
                // option to delete a project and its associations from the database
                System.out.println("17. Delete Project");
                // option to exit programme
                System.out.println("18. Exit Programme");
                System.out.println();
                // prompt user to enter their selected option
                System.out.print("Enter your option: ");

                // try-catch block
                try {
                    // get user option input
                    option = scanner.nextInt();
                    // consume newline
                    scanner.nextLine();

                    // call method to perform selected option action
                    switch (option) {
                        // call methods to view tables
                        case 1:
                            viewAllProjects(statement);
                            break;
                        case 2:
                            viewAllCustomers(statement);
                            break;
                        case 3:
                            viewAllArchitects(statement);
                            break;
                        case 4:
                            viewAllContractors(statement);
                            break;

                        // call methods to search for and track project progress
                        case 5:
                            finaliseProject(statement);
                            break;
                        case 6:
                            findIncompleteProjects(statement);
                            break;
                        case 7:
                            findOverdueProjects(statement);
                            break;
                        case 8:
                            searchProjects(statement);
                            break;

                        // call methods to update records in the database
                        case 9:
                            updateProject(statement, scanner);
                            break;
                        case 10:
                            updateCustomer(statement);
                            break;
                        case 11:
                            updateArchitect(statement);
                            break;
                        case 12:
                            updateContractor(statement);
                            break;

                        // call methods to add new records to the database
                        case 13:
                            addNewProject(statement, scanner);
                            break;
                        case 14:
                            addNewCustomer(statement);
                            break;
                        case 15:
                            addNewArchitect(statement);
                            break;
                        case 16:
                            addNewContractor(statement);
                            break;

                        // call method to delete records from the database
                        case 17:
                            deleteProject(statement);
                            break;
                        // option to exit programme
                        case 18:
                            // notify user of programme exit
                            System.out.println("Exiting Poised Project Management System...");
                            break;
                        // invalid entry
                        default:
                            // ask user to retry
                            System.out.println("Invalid option selected! Please choose an option from 1-18.");
                    }
                }
                // if a non-integer data type input is entered
                catch (InputMismatchException e) {
                    // clear scanner's buffer
                    scanner.nextLine();
                    // repeat do-while prompt
                    System.out.println("Invalid input! Please choose an integer option from 1-18.");
                }
            } while (option != 18);

            // close connections
            statement.close();
            connection.close();
            // close scanner
            scanner.close();

        } catch (SQLException e) {
            // print error details
            e.printStackTrace();
        }
    }

    /**
     * method to add a new project to the database
     *
     * @param statement the SQL statement for executing queries
     * @param scanner   the Scanner object for user input
     * @throws SQLException if a database access error occurs
     */
    private static void addNewProject(Statement statement, Scanner scanner) throws SQLException {
        // prompt user to input project details
        System.out.println("Enter details for the new project:");

        // get architect ID
        String architectID = InputValidation.validateStringInput("Architect ID: ");

        // get contractor ID
        String contractorID = InputValidation.validateStringInput("Contractor ID: ");

        // get customer ID
        String customerID = InputValidation.validateStringInput("Customer ID: ");

        // get project name (can be null - building type + customer name)
        // String projectName = InputValidation.validateStringInput("Project Name: ");
        System.out.print("Project Name: ");
        // get user input
        String projectName = scanner.nextLine();

        // get building type
        String buildingType = InputValidation.validateStringInput("Building Type: ");

        // get physical address
        String physicalAddress = InputValidation.validateStringInput("Physical Address: ");

        // get ERF number
        String erfNumber = InputValidation.validateStringNumberInput("ERF Number: ");

        // get total fee
        double totalFee = InputValidation.validateDoubleInput("Total Fee: ");

        // get amount paid
        double amountPaid = InputValidation.validateDoubleInput("Amount Paid: ");

        // get project deadline
        LocalDate projectDeadline = InputValidation.validateDateInput("Project Deadline (YYYY-MM-DD): ");

        // get project finalised status
        boolean projectFinalised = InputValidation.validateBooleanInput("Project Finalised (true/false): ");

        // declare variables - completion date can be null if project is not finalised
        LocalDate completionDate = null;
        String completionDateSQL = "";
        String completionDateValue = "";

        // check if project is finalised (completion date is not null)
        if (projectFinalised) {
            // get completion date
            completionDate = InputValidation.validateDateInput("Completion Date (YYYY-MM-DD): ");
            // include the completion_date SQL column in the INSERT statement
            completionDateSQL = ", completion_date";
            // include the completion date as a VALUE to be inserted
            completionDateValue = ", '" + completionDate + "'";
        }

        // insert project record into Projects table by executing SQL query
        statement.executeUpdate(
                "INSERT INTO Projects (architect_id, contractor_id, customer_id, project_name, building_type, physical_address, erf_number, total_fee, amount_paid, project_deadline, project_finalised"
                        + completionDateSQL + ") VALUES ('" +
                        architectID + "', '" + contractorID + "', '" + customerID + "', '" + projectName + "', '"
                        + buildingType + "', '" + physicalAddress + "', '" + erfNumber + "', " + totalFee + ", "
                        + amountPaid + ", '"
                        + projectDeadline + "', " + projectFinalised + completionDateValue + ")");

        // notify user of successful entry
        System.out.println("New project successfully added!");
    }

    /**
     * method to update an existing project in the database
     *
     * @param statement the SQL statement for executing queries
     * @param scanner   the Scanner object for user input
     * @throws SQLException if a database access error occurs
     */
    private static void updateProject(Statement statement, Scanner scanner) throws SQLException {
        // prompt user to enter number of the project they wish to update
        int projectNumber = InputValidation
                .validateIntegerInput("Enter project number of the project record you wish to update: ");

        // SQL query to select record(s) with project number matched
        try (ResultSet resultSet = statement
                .executeQuery("SELECT * FROM Projects WHERE project_number = " + projectNumber)) {
            // check if project number is found (if there is a next row in the result set)
            if (resultSet.next()) {
                // prompt user to enter updated project details
                System.out.println("Update details for project record " + projectNumber + ": ");

                // get updated architect ID
                String architectID = InputValidation.validateStringInput("Architect ID: ");

                // get updated contractor ID
                String contractorID = InputValidation.validateStringInput("Contractor ID: ");

                // get updated customer ID
                String customerID = InputValidation.validateStringInput("Customer ID: ");

                // get updated project name (can be null - building type + customer name)
                // String projectName = InputValidation.validateStringInput("Project Name: ");
                System.out.print("Project Name: ");
                // get user input
                String projectName = scanner.nextLine();

                // get updated building type
                String buildingType = InputValidation.validateStringInput("Building Type: ");

                // get updated physical address
                String physicalAddress = InputValidation.validateStringInput("Physical Address: ");

                // get updated ERF number
                String erfNumber = InputValidation.validateStringNumberInput("ERF Number: ");

                // get updated total fee
                double totalFee = InputValidation.validateDoubleInput("Total Fee: ");

                // get updated amount paid
                double amountPaid = InputValidation.validateDoubleInput("Amount Paid: ");

                // get updated project deadline
                LocalDate projectDeadline = InputValidation.validateDateInput("Project Deadline (YYYY-MM-DD): ");

                // get updated project finalised status
                boolean projectFinalised = InputValidation.validateBooleanInput("Project Finalised (true/false): ");

                // declare variables
                LocalDate completionDate;
                String completionDateSQL = "";

                // check if project is finalised (true)
                if (projectFinalised) {
                    // get updated completion date
                    completionDate = InputValidation.validateDateInput("Completion Date (YYYY-MM-DD): ");
                    // add completion date to the SQL query
                    completionDateSQL = ", completion_date = '" + completionDate + "'";
                } else {
                    // if project is not finalised
                    completionDate = null;
                    // set completion date to null in the SQL query
                    completionDateSQL = ", completion_date = NULL";
                }

                // execute the SQL update statement to update the record
                statement.executeUpdate("UPDATE Projects SET " +
                        "architect_id = '" + architectID + "', " +
                        "contractor_id = '" + contractorID + "', " +
                        "customer_id = '" + customerID + "', " +
                        "project_name = '" + projectName + "', " +
                        "building_type = '" + buildingType + "', " +
                        "physical_address = '" + physicalAddress + "', " +
                        "erf_number = '" + erfNumber + "', " +
                        "total_fee = " + totalFee + ", " +
                        "amount_paid = " + amountPaid + ", " +
                        "project_deadline = '" + projectDeadline + "', " +
                        "project_finalised = " + projectFinalised +
                        completionDateSQL +
                        " WHERE project_number = " + projectNumber);

                // notify user of successful update
                System.out.println("Project record is successfully updated!");
            } else {
                // notify user if project number is not found
                System.out.println("Project record not found.");
            }
        }
    }

    /**
     * method to delete a project from the database
     *
     * @param statement the SQL statement for executing queries
     * @throws SQLException if a database access error occurs
     */
    private static void deleteProject(Statement statement) throws SQLException {
        // prompt user to enter number of the project they wish to delete
        int projectNumber = InputValidation
                .validateIntegerInput("Enter project number of the project record you wish to delete: ");

        // SQL query to find record with project number of record user wishes to delete
        try (ResultSet projectResult = statement
                .executeQuery("SELECT * FROM Projects WHERE project_number = " + projectNumber)) {
            // check if the project exists
            if (projectResult.next()) {
                // get the related IDs
                String architectID = projectResult.getString("architect_id");
                String contractorID = projectResult.getString("contractor_id");
                String customerID = projectResult.getString("customer_id");

                // check how many projects each ID is associated with
                int architectCount = getCount(statement, "Projects", "architect_id", architectID);
                int contractorCount = getCount(statement, "Projects", "contractor_id", contractorID);
                int customerCount = getCount(statement, "Projects", "customer_id", customerID);

                /*
                 * execute delete queries to remove associated data from the child tables
                 * before removing the record from the parent table (Projects table)
                 */

                // check if architect is linked to multiple projects
                if (architectCount > 1) {
                    // ask user if they still wish to delete record
                    boolean deleteArchitect = InputValidation.validateBooleanInput(
                            "The architect is associated with multiple projects. Do you also wish to delete the architect? (true/false): ");

                    // if user still wishes to delete record from table
                    if (deleteArchitect) {
                        // SQL query to delete architect record
                        statement.executeUpdate("DELETE FROM Architects WHERE architect_id = '" + architectID + "'");
                        // notify user of successful deletion
                        System.out.println("Architect with ID " + architectID + " successfully deleted!");
                    }
                } else {
                    // delete architect if not associated with any other project
                    statement.executeUpdate("DELETE FROM Architects WHERE architect_id = '" + architectID + "'");
                    // notify user of successful deletion
                    System.out.println("Architect with ID " + architectID + " successfully deleted!");
                }

                // check if contractor is linked to multiple projects
                if (contractorCount > 1) {
                    // ask user if they still wish to delete record
                    boolean deleteContractor = InputValidation.validateBooleanInput(
                            "The contractor is associated with multiple projects. Do you also wish to delete the contractor? (true/false): ");

                    // if user still wishes to delete record from table
                    if (deleteContractor) {
                        // SQL query to delete contractor record
                        statement.executeUpdate("DELETE FROM Contractors WHERE contractor_id = '" + contractorID + "'");
                        // notify user of successful deletion
                        System.out.println("Contractor with ID " + contractorID + " successfully deleted!");
                    }
                } else {
                    // delete contractor if not associated with any other project
                    statement.executeUpdate("DELETE FROM Contractors WHERE contractor_id = '" + contractorID + "'");
                    // notify user of successful deletion
                    System.out.println("Contractor with ID " + contractorID + " successfully deleted!");
                }

                // check if customer is linked to multiple projects
                if (customerCount > 1) {
                    // ask user if they still wish to delete record
                    boolean deleteCustomer = InputValidation.validateBooleanInput(
                            "The customer is associated with multiple projects. Do you also wish to delete the customer? (true/false): ");

                    // if user still wishes to delete record from table
                    if (deleteCustomer) {
                        // SQL query to delete customer record
                        statement.executeUpdate("DELETE FROM Customers WHERE customer_id = '" + customerID + "'");
                        // notify user of successful deletion
                        System.out.println("Customer with ID " + customerID + " successfully deleted!");
                    }
                } else {
                    // delete customer if not associated with any other project
                    statement.executeUpdate("DELETE FROM Customers WHERE customer_id = '" + customerID + "'");
                    // notify user of successful deletion
                    System.out.println("Customer with ID " + customerID + " successfully deleted!");
                }

                // delete project from Projects table
                statement.executeUpdate("DELETE FROM Projects WHERE project_number = " + projectNumber);
                // notify user of successful deletion
                System.out.println("Project Number " + projectNumber + " successfully deleted!");

            } else {
                // notify user if project does not exist
                System.out.println("Project Number " + projectNumber + " not found.");
            }
        }
    }

    /**
     * helper method to count the number of projects associated with a specific ID
     *
     * @param statement the SQL statement for executing queries
     * @param table     the name of the table to query
     * @param column    the name of the column to check for the specific ID
     * @param id        the ID to count the associated projects for
     * @return the number of associated projects
     * @throws SQLException if a database access error occurs
     */
    private static int getCount(Statement statement, String table, String column, String id)
            throws SQLException {
        // SQL query to count number of projects associated with a specific ID
        try (ResultSet resultSet = statement
                .executeQuery("SELECT COUNT(*) FROM " + table + " WHERE " + column + " = '" + id + "'")) {
            // loop through query results
            if (resultSet.next()) {
                // return count of projects associated with the specific ID
                return resultSet.getInt(1);
            }
            // return that no projects associated with the specific ID found
            return 0;
        }
    }

    /**
     * method to "finalise" a project in the database
     *
     * @param statement the SQL statement for executing queries
     * @throws SQLException if a database access error occurs
     */
    private static void finaliseProject(Statement statement) throws SQLException {
        // prompt user to enter number of the project they wish to finalise
        int projectNumber = InputValidation
                .validateIntegerInput("Enter project number of the project you wish to finalise: ");

        try (ResultSet projectResult = statement
                .executeQuery("SELECT * FROM Projects WHERE project_number = " + projectNumber)) {
            // check if the project exists
            if (projectResult.next()) {
                // prompt user for completion date
                LocalDate completionDate = InputValidation.validateDateInput("Enter completion date (YYYY-MM-DD): ");

                // update project record to mark it as finalised and set completion date
                statement.executeUpdate("UPDATE Projects SET project_finalised = true, completion_date = '"
                        + completionDate + "' WHERE project_number = " + projectNumber);

                // notify user of successful project finalisation
                System.out.println("Project Number " + projectNumber + " successfully finalised!");
            } else {
                // notify user if project does not exist
                System.out.println("Project Number " + projectNumber + " not found.");
            }
        }
    }

    /**
     * method to find the incomplete projects in the database
     *
     * @param statement the SQL statement for executing queries
     * @throws SQLException if a database access error occurs
     */
    private static void findIncompleteProjects(Statement statement) throws SQLException {
        // SQL query to retrieve all incomplete projects
        try (ResultSet incompleteProjects = statement
                .executeQuery("SELECT * FROM Projects WHERE project_finalised = false")) {

            // check if incomplete project(s) found
            if (incompleteProjects.next()) {
                // display header for results
                System.out.println("Incomplete Projects:");
                System.out.println(
                        "Project Number\t\tProject Name\t\tBuilding Type\t\tPhysical Address\t\tERF Number\t\tTotal Fee\t\tAmount Paid\t\tDeadline\t\tFinalised\t\tCompletion Date\t\tArchitect ID\t\tContractor ID\t\tCustomer ID");
                System.out.println(
                        "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

                // loop through the results and display each project's details
                do {
                    // extract data from current record
                    int projectNumber = incompleteProjects.getInt("project_number");
                    String projectName = incompleteProjects.getString("project_name");
                    String buildingType = incompleteProjects.getString("building_type");
                    String physicalAddress = incompleteProjects.getString("physical_address");
                    int erfNumber = incompleteProjects.getInt("erf_number");
                    double totalFee = incompleteProjects.getDouble("total_fee");
                    double amountPaid = incompleteProjects.getDouble("amount_paid");
                    Date projectDeadline = incompleteProjects.getDate("project_deadline");
                    boolean projectFinalised = incompleteProjects.getBoolean("project_finalised");
                    Date completionDate = incompleteProjects.getDate("completion_date");
                    String architectID = incompleteProjects.getString("architect_id");
                    String contractorID = incompleteProjects.getString("contractor_id");
                    String customerID = incompleteProjects.getString("customer_id");

                    // print project details
                    System.out.println(projectNumber + "\t\t" +
                            projectName + "\t\t" +
                            buildingType + "\t\t" +
                            physicalAddress + "\t\t" +
                            erfNumber + "\t\t" +
                            totalFee + "\t\t" +
                            amountPaid + "\t\t" +
                            projectDeadline + "\t\t" +
                            projectFinalised + "\t\t" +
                            completionDate + "\t\t" +
                            architectID + "\t\t" +
                            contractorID + "\t\t" +
                            customerID);
                } while (incompleteProjects.next());
            } else {
                // notify user if no incomplete projects found
                System.out.println("No incomplete projects found.");
            }
        }
    }

    /**
     * method to find the overdue projects in the database
     *
     * @param statement the SQL statement for executing queries
     * @throws SQLException if a database access error occurs
     */
    private static void findOverdueProjects(Statement statement) throws SQLException {
        // get the current date
        LocalDate currentDate = LocalDate.now();

        // SQL query to retrieve all overdue projects
        try (ResultSet overdueProjects = statement
                .executeQuery("SELECT * FROM Projects WHERE project_finalised = false AND project_deadline < '"
                        + currentDate + "'")) {

            // check if overdue project(s) found
            if (overdueProjects.next()) {
                // display header for results
                System.out.println("Overdue Projects:");
                System.out.println(
                        "Project Number\t\tProject Name\t\tBuilding Type\t\tPhysical Address\t\tERF Number\t\tTotal Fee\t\tAmount Paid\t\tDeadline\t\tFinalised\t\tCompletion Date\t\tArchitect ID\t\tContractor ID\t\tCustomer ID");
                System.out.println(
                        "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

                // loop through the results and display each project's details
                do {
                    // extract data from current record
                    int projectNumber = overdueProjects.getInt("project_number");
                    String projectName = overdueProjects.getString("project_name");
                    String buildingType = overdueProjects.getString("building_type");
                    String physicalAddress = overdueProjects.getString("physical_address");
                    int erfNumber = overdueProjects.getInt("erf_number");
                    double totalFee = overdueProjects.getDouble("total_fee");
                    double amountPaid = overdueProjects.getDouble("amount_paid");
                    Date projectDeadline = overdueProjects.getDate("project_deadline");
                    boolean projectFinalised = overdueProjects.getBoolean("project_finalised");
                    Date completionDate = overdueProjects.getDate("completion_date");
                    String architectID = overdueProjects.getString("architect_id");
                    String contractorID = overdueProjects.getString("contractor_id");
                    String customerID = overdueProjects.getString("customer_id");

                    // print project details
                    System.out.println(projectNumber + "\t\t" +
                            projectName + "\t\t" +
                            buildingType + "\t\t" +
                            physicalAddress + "\t\t" +
                            erfNumber + "\t\t" +
                            totalFee + "\t\t" +
                            amountPaid + "\t\t" +
                            projectDeadline + "\t\t" +
                            projectFinalised + "\t\t" +
                            completionDate + "\t\t" +
                            architectID + "\t\t" +
                            contractorID + "\t\t" +
                            customerID);
                } while (overdueProjects.next());
            } else {
                // notify user if no overdue projects found
                System.out.println("No overdue projects found.");
            }
        }
    }

    /**
     * method to search for projects in the database
     * based on project number or name
     *
     * @param statement the SQL statement object for executing queries
     * @throws SQLException if an SQL exception occurs
     */
    private static void searchProjects(Statement statement) throws SQLException {
        // prompt the user to enter the project number or name
        String searchTerm = InputValidation.validateStringInput("Enter the project number or name to search for: ");

        // SQL query to search for the project by project number or name
        try (ResultSet resultSet = statement.executeQuery(
                "SELECT * FROM Projects WHERE project_number = '" + searchTerm + "' OR project_name LIKE '%"
                        + searchTerm + "%'")) {
            // check if project name or number found
            if (resultSet.next()) {
                // display header for search results
                System.out.println("Search results for '" + searchTerm + "': ");
                System.out.println(
                        "Project Number\t\tProject Name\t\tBuilding Type\t\tPhysical Address\t\tERF Number\t\tTotal Fee\t\tAmount Paid\t\tDeadline\t\tFinalised\t\tCompletion Date\t\tArchitect ID\t\tContractor ID\t\tCustomer ID");
                System.out.println(
                        "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

                // loop through the results and display each project's details
                do {
                    // extract data from current record
                    int projectNumber = resultSet.getInt("project_number");
                    String projectName = resultSet.getString("project_name");
                    String buildingType = resultSet.getString("building_type");
                    String physicalAddress = resultSet.getString("physical_address");
                    int erfNumber = resultSet.getInt("erf_number");
                    double totalFee = resultSet.getDouble("total_fee");
                    double amountPaid = resultSet.getDouble("amount_paid");
                    Date projectDeadline = resultSet.getDate("project_deadline");
                    boolean projectFinalised = resultSet.getBoolean("project_finalised");
                    Date completionDate = resultSet.getDate("completion_date");
                    String architectID = resultSet.getString("architect_id");
                    String contractorID = resultSet.getString("contractor_id");
                    String customerID = resultSet.getString("customer_id");

                    // print project details
                    System.out.println(projectNumber + "\t\t" +
                            projectName + "\t\t" +
                            buildingType + "\t\t" +
                            physicalAddress + "\t\t" +
                            erfNumber + "\t\t" +
                            totalFee + "\t\t" +
                            amountPaid + "\t\t" +
                            projectDeadline + "\t\t" +
                            projectFinalised + "\t\t" +
                            completionDate + "\t\t" +
                            architectID + "\t\t" +
                            contractorID + "\t\t" +
                            customerID);
                } while (resultSet.next());
            } else {
                // notify user if no projects found matching the search term
                System.out.println("No projects found matching the search term '" + searchTerm + "'.");
            }
        }
    }

    /**
     * method to add a new customer to the database
     *
     * @param statement the SQL statement object for executing queries
     * @throws SQLException if an SQL exception occurs
     */
    private static void addNewCustomer(Statement statement) throws SQLException {
        // prompt user to input customer details
        System.out.println("Enter details for the new customer:");

        // get customer ID
        String customerID = InputValidation.validateStringInput("Customer ID: ");

        // get customer first name
        String fname = InputValidation.validateStringInput("First Name: ");

        // get customer surname
        String surname = InputValidation.validateStringInput("Surname: ");

        // get customer telephone number
        String tel = InputValidation.validateStringNumberInput("Telephone Number: ");

        // get customer email
        String email = InputValidation.validateStringInput("Email: ");

        // get customer address
        String address = InputValidation.validateStringInput("Address: ");

        // insert customer record into Customers table
        statement.executeUpdate(
                "INSERT INTO Customers (customer_id, customer_fname, customer_surname, customer_tel, customer_email, customer_address) VALUES ('"
                        + customerID + "','" + fname + "', '" + surname + "', '" + tel + "', '" + email + "', '"
                        + address + "')");

        // notify user of successful entry
        System.out.println("New customer record " + customerID + " successfully added!");
    }

    /**
     * method to add a new architect to the database
     *
     * @param statement the SQL statement object for executing queries
     * @throws SQLException if an SQL exception occurs
     */
    private static void addNewArchitect(Statement statement) throws SQLException {
        // prompt user to input architect details
        System.out.println("Enter details for the new architect:");

        // get architect ID
        String architectID = InputValidation.validateStringInput("Architect ID: ");

        // get architect name
        String name = InputValidation.validateStringInput("Name: ");

        // get architect telephone number
        String tel = InputValidation.validateStringNumberInput("Telephone Number: ");

        // get architect email
        String email = InputValidation.validateStringInput("Email: ");

        // get architect address
        String address = InputValidation.validateStringInput("Address: ");

        // insert architect record into Architects table
        statement.executeUpdate(
                "INSERT INTO Architects (architect_id, architect_name, architect_tel, architect_email, architect_address) VALUES ('"
                        + architectID + "','" + name + "', '" + tel + "', '" + email + "', '" + address + "')");

        // notify user of successful entry
        System.out.println("New architect record successfully added!");
    }

    /**
     * method to add a new contractor to the database
     *
     * @param statement the SQL statement object for executing queries
     * @throws SQLException if an SQL exception occurs
     */
    private static void addNewContractor(Statement statement) throws SQLException {
        // prompt user to input contractor details
        System.out.println("Enter details for the new contractor:");

        // get contractor ID
        String contractorID = InputValidation.validateStringInput("Contractor ID: ");

        // get contractor name
        String name = InputValidation.validateStringInput("Name: ");

        // get contractor telephone number
        String tel = InputValidation.validateStringNumberInput("Telephone Number: ");

        // get contractor email
        String email = InputValidation.validateStringInput("Email: ");

        // get contractor address
        String address = InputValidation.validateStringInput("Address: ");

        // insert contractor record into Contractors table
        statement.executeUpdate(
                "INSERT INTO Contractors (contractor_id, contractor_name, contractor_tel, contractor_email, contractor_address) VALUES ('"
                        + contractorID + "', '" + name + "', '" + tel + "', '" + email + "', '" + address + "')");

        // notify user of successful entry
        System.out.println("New contractor record successfully added!");
    }

    /**
     * method to update customer details in the database
     *
     * @param statement the SQL statement object for executing queries
     * @throws SQLException if an SQL exception occurs
     */
    private static void updateCustomer(Statement statement) throws SQLException {
        // prompt user to enter ID of the customer they wish to update
        String id = InputValidation.validateStringInput("Enter ID of the customer to update: ");

        // SQL query to select record(s) with customer ID matched
        try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Customers WHERE customer_id = '" + id + "'")) {
            // check if customer ID found (if there is a next row in the result set)
            if (resultSet.next()) {
                // prompt user to enter updated customer details
                System.out.println("Update details for customer record " + id + ": ");

                // get updated customer first name
                String fname = InputValidation.validateStringInput("First Name: ");

                // get updated customer surname
                String surname = InputValidation.validateStringInput("Surname: ");

                // get updated customer telephone number
                String tel = InputValidation.validateStringNumberInput("Telephone Number: ");

                // get updated customer email
                String email = InputValidation.validateStringInput("Email: ");

                // get updated customer address
                String address = InputValidation.validateStringInput("Address: ");

                // SQL query to update record
                statement.executeUpdate("UPDATE Customers SET " +
                        "customer_fname = '" + fname + "', " +
                        "customer_surname = '" + surname + "', " +
                        "customer_tel = '" + tel + "', " +
                        "customer_email = '" + email + "', " +
                        "customer_address = '" + address + "' " +
                        "WHERE customer_id = '" + id + "'");

                // notify user of successful update
                System.out.println("Customer record " + id + " successfully updated!");
            } else {
                // notify user if customer ID not found
                System.out.println("Customer record not found.");
            }
        }
    }

    /**
     * method to update architect details in the database
     *
     * @param statement the SQL statement object for executing queries
     * @throws SQLException if an SQL exception occurs
     */
    private static void updateArchitect(Statement statement) throws SQLException {
        // prompt user to enter ID of the architect they wish to update
        String id = InputValidation.validateStringInput("Enter ID of the architect to update: ");

        // SQL query to select record(s) with architect ID matched
        try (ResultSet resultSet = statement
                .executeQuery("SELECT * FROM Architects WHERE architect_id = '" + id + "'")) {
            // check if architect ID found (if there is a next row in the result set)
            if (resultSet.next()) {
                // prompt user to enter updated architect details
                System.out.println("Update details for architect record " + id + ": ");

                // get updated architect name
                String name = InputValidation.validateStringInput("Name: ");

                // get updated architect telephone number
                String tel = InputValidation.validateStringNumberInput("Telephone Number: ");

                // get updated architect email
                String email = InputValidation.validateStringInput("Email: ");

                // get updated architect address
                String address = InputValidation.validateStringInput("Address: ");

                // SQL query to update record
                statement.executeUpdate("UPDATE Architects SET " +
                        "architect_name = '" + name + "', " +
                        "architect_tel = '" + tel + "', " +
                        "architect_email = '" + email + "', " +
                        "architect_address = '" + address + "' " +
                        "WHERE architect_id = '" + id + "'");

                // notify user of successful update
                System.out.println("Architect record " + id + " successfully updated!");
            } else {
                // notify user if architect ID not found
                System.out.println("Architect record not found.");
            }
        }
    }

    /**
     * method to update contractor details in the database
     *
     * @param statement the SQL statement object for executing queries
     * @throws SQLException if an SQL exception occurs
     */
    private static void updateContractor(Statement statement) throws SQLException {
        // prompt user to enter ID of the contractor they wish to update
        String id = InputValidation.validateStringInput("Enter ID of the contractor to update: ");

        // SQL query to select record(s) with contractor ID matched
        try (ResultSet resultSet = statement
                .executeQuery("SELECT * FROM Contractors WHERE contractor_id = '" + id + "'")) {
            // check if contractor ID found (if there is a next row in the result set)
            if (resultSet.next()) {
                // prompt user to enter updated contractor details
                System.out.println("Update details for contractor record " + id + ": ");

                // get updated contractor name
                String name = InputValidation.validateStringInput("Name: ");

                // get updated contractor telephone number
                String tel = InputValidation.validateStringNumberInput("Telephone Number: ");

                // get updated contractor email
                String email = InputValidation.validateStringInput("Email: ");

                // get updated contractor address
                String address = InputValidation.validateStringInput("Address: ");

                // SQL query to update record
                statement.executeUpdate("UPDATE Contractors SET " +
                        "contractor_name = '" + name + "', " +
                        "contractor_tel = '" + tel + "', " +
                        "contractor_email = '" + email + "', " +
                        "contractor_address = '" + address + "' " +
                        "WHERE contractor_id = '" + id + "'");

                // notify user of successful update
                System.out.println("Contractor record " + id + " successfully updated!");
            } else {
                // notify user if contractor ID not found
                System.out.println("Contractor record not found.");
            }
        }
    }

    /**
     * method to retrieve and display all projects from the database
     *
     * @param statement the SQL statement object for executing queries
     * @throws SQLException if an SQL exception occurs
     */
    private static void viewAllProjects(Statement statement) throws SQLException {
        // SQL query to retrieve all records from the Projects table
        try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Projects")) {
            // check if there are records to display
            if (resultSet.next()) {
                // display header for results
                System.out.println("All Projects:");
                System.out.println(
                        "Project Number\t\tProject Name\t\tBuilding Type\t\tPhysical Address\t\tERF Number\t\tTotal Fee\t\tAmount Paid\t\tDeadline\t\tFinalised\t\tCompletion Date\t\tArchitect ID\t\tContractor ID\t\tCustomer ID");
                System.out.println(
                        "---------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------------");

                // loop through the results and display each project's details
                do {
                    // extract data from current record
                    int projectNumber = resultSet.getInt("project_number");
                    String projectName = resultSet.getString("project_name");
                    String buildingType = resultSet.getString("building_type");
                    String physicalAddress = resultSet.getString("physical_address");
                    int erfNumber = resultSet.getInt("erf_number");
                    double totalFee = resultSet.getDouble("total_fee");
                    double amountPaid = resultSet.getDouble("amount_paid");
                    Date projectDeadline = resultSet.getDate("project_deadline");
                    boolean projectFinalised = resultSet.getBoolean("project_finalised");
                    Date completionDate = resultSet.getDate("completion_date");
                    String architectID = resultSet.getString("architect_id");
                    String contractorID = resultSet.getString("contractor_id");
                    String customerID = resultSet.getString("customer_id");

                    // print project details
                    System.out.println(projectNumber + "\t\t" +
                            projectName + "\t\t" +
                            buildingType + "\t\t" +
                            physicalAddress + "\t\t" +
                            erfNumber + "\t\t" +
                            totalFee + "\t\t" +
                            amountPaid + "\t\t" +
                            projectDeadline + "\t\t" +
                            projectFinalised + "\t\t" +
                            completionDate + "\t\t" +
                            architectID + "\t\t" +
                            contractorID + "\t\t" +
                            customerID);
                } while (resultSet.next());
            } else {
                // notify user if no records found
                System.out.println("No projects found.");
            }
        }
    }

    /**
     * method to retrieve and display all customers from the database
     *
     * @param statement the SQL statement object for executing queries
     * @throws SQLException if an SQL exception occurs
     */
    private static void viewAllCustomers(Statement statement) throws SQLException {
        // SQL query to retrieve all records from the Customers table
        try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Customers")) {
            // check if there are records to display
            if (resultSet.next()) {
                // display header for results
                System.out.println("All Customers:");
                System.out.println("Customer ID\t\tFirst Name\tSurname\t\tTelephone Number\t\tEmail\t\tAddress");
                System.out.println(
                        "--------------------------------------------------------------------------------------------------------------------------------");

                // loop through the results and display each customer's details
                do {
                    // extract data from current record
                    String customerID = resultSet.getString("customer_id");
                    String fname = resultSet.getString("customer_fname");
                    String surname = resultSet.getString("customer_surname");
                    String tel = resultSet.getString("customer_tel");
                    String email = resultSet.getString("customer_email");
                    String address = resultSet.getString("customer_address");

                    // print customer details
                    System.out.println(customerID + "\t\t" + fname + "\t\t" + surname + "\t\t" + tel + "\t\t" + email
                            + "\t\t" + address);
                } while (resultSet.next());
            } else {
                // notify user if no records found
                System.out.println("No customers found.");
            }
        }
    }

    /**
     * method to retrieve and display all architects from the database
     *
     * @param statement the SQL statement object for executing queries
     * @throws SQLException if an SQL exception occurs
     */
    private static void viewAllArchitects(Statement statement) throws SQLException {
        // SQL query to retrieve all records from the Architects table
        try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Architects")) {
            // check if there are records to display
            if (resultSet.next()) {
                // display header for results
                System.out.println("All Architects:");
                System.out.println("Architect ID\t\tName\t\tTelephone Number\t\tEmail\t\tAddress");
                System.out.println(
                        "--------------------------------------------------------------------------------------------------------------------------------");

                // loop through the results and display each architect's details
                do {
                    // extract data from current record
                    String architectID = resultSet.getString("architect_id");
                    String name = resultSet.getString("architect_name");
                    String tel = resultSet.getString("architect_tel");
                    String email = resultSet.getString("architect_email");
                    String address = resultSet.getString("architect_address");

                    // print architect details
                    System.out.println(architectID + "\t\t" + name + "\t\t" + tel + "\t\t" + email + "\t\t" + address);
                } while (resultSet.next());
            } else {
                // notify user if no records found
                System.out.println("No architects found.");
            }
        }
    }

    /**
     * method to retrieve and display all contractors from the database
     *
     * @param statement the SQL statement object for executing queries
     * @throws SQLException if an SQL exception occurs
     */
    private static void viewAllContractors(Statement statement) throws SQLException {
        // SQL query to retrieve all records from the Contractors table
        try (ResultSet resultSet = statement.executeQuery("SELECT * FROM Contractors")) {
            // check if there are records to display
            if (resultSet.next()) {
                // display header for results
                System.out.println("All Contractors:");
                System.out.println("Contractor ID\t\tName\t\tTelephone Number\t\tEmail\t\tAddress");
                System.out.println(
                        "--------------------------------------------------------------------------------------------------------------------------------");

                // loop through the results and display each contractor's details
                do {
                    // extract data from current record
                    String contractorID = resultSet.getString("contractor_id");
                    String name = resultSet.getString("contractor_name");
                    String tel = resultSet.getString("contractor_tel");
                    String email = resultSet.getString("contractor_email");
                    String address = resultSet.getString("contractor_address");

                    // print contractor details
                    System.out.println(contractorID + "\t\t" + name + "\t\t" + tel + "\t\t" + email + "\t\t" + address);
                } while (resultSet.next());
            } else {
                // notify user if no records found
                System.out.println("No contractors found.");
            }
        }
    }
}
