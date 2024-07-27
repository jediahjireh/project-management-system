-- create the PoisePMS database
CREATE database IF NOT EXISTS PoisePMS;

-- use "PoisePMS" as the default database
USE PoisePMS;

-- create the Projects table
CREATE TABLE IF NOT EXISTS Projects (
    project_number INT PRIMARY KEY AUTO_INCREMENT NOT NULL,
    -- foreign keys
    architect_id VARCHAR(50) UNIQUE NOT NULL,
    contractor_id VARCHAR(50) UNIQUE NOT NULL,
    customer_id VARCHAR(50) UNIQUE NOT NULL,
    -- can be null - building type + customer name
    project_name VARCHAR(50),
    building_type VARCHAR(50) NOT NULL,
    physical_address VARCHAR(60) NOT NULL,
    erf_number VARCHAR(20) NOT NULL,
    total_fee DECIMAL(12, 2) NOT NULL,
    amount_paid DECIMAL(12, 2) NOT NULL,
    project_deadline DATE NOT NULL,
    project_finalised BOOLEAN NOT NULL,
    -- can be null until project is finalised
    completion_date DATE
);

-- completion date can be null if project is not yet finalised
ALTER TABLE Projects
MODIFY completion_date DATE NULL;

-- create the Architects table
CREATE TABLE IF NOT EXISTS Architects (
    architect_id VARCHAR(50) UNIQUE NOT NULL,
    architect_name VARCHAR(50) NOT NULL,
    architect_email VARCHAR(50) NOT NULL,
    architect_address VARCHAR(60) NOT NULL,
    architect_tel VARCHAR(10) NOT NULL,
    FOREIGN KEY (architect_id) REFERENCES Projects(architect_id)
);

-- create the Contractors table
CREATE TABLE IF NOT EXISTS Contractors (
    contractor_id VARCHAR(50) UNIQUE NOT NULL,
    contractor_name VARCHAR(50) NOT NULL,
    contractor_email VARCHAR(50) NOT NULL,
    contractor_address VARCHAR(60) NOT NULL,
    contractor_tel VARCHAR(10) NOT NULL,
    FOREIGN KEY (contractor_id) REFERENCES Projects(contractor_id)
);

-- create the Customers table
CREATE TABLE IF NOT EXISTS Customers (
    customer_id VARCHAR(50) UNIQUE NOT NULL,
    customer_fname VARCHAR(50) NOT NULL,
    customer_surname VARCHAR(50) NOT NULL,
    customer_email VARCHAR(50) NOT NULL,
    customer_address VARCHAR(60) NOT NULL,
    customer_tel VARCHAR(10) NOT NULL,
    FOREIGN KEY (customer_id) REFERENCES Projects(customer_id)
);

-- create trigger to name project if project_name not provided
DELIMITER $$
CREATE TRIGGER AutoNameProject
AFTER INSERT ON Customers FOR EACH ROW 
BEGIN
    DECLARE created_project_name VARCHAR(50);
    -- check if project_name is NULL or empty
    IF (
        SELECT project_name
        FROM Projects
        WHERE customer_id = NEW.customer_id
    ) IS NULL
    OR (
        SELECT project_name
        FROM Projects
        WHERE customer_id = NEW.customer_id
    ) = '' THEN
        -- generate project_name using building type and customer surname
        SET created_project_name = CONCAT(
            (
                SELECT Projects.building_type
                FROM Projects
                WHERE Projects.customer_id = NEW.customer_id
            ),
            ' ',
            NEW.customer_surname
        );
        -- update the inserted row with the generated project name
        UPDATE Projects
        SET Projects.project_name = created_project_name
        WHERE Projects.customer_id = NEW.customer_id;
    END IF;
END$$
DELIMITER ;

/* 
A trigger needs to be created to name project if
project_name not provided after project details update
Limitation in MySQL :
It doesn't allow for the update of the same table
that is being triggered by the trigger itself
*/

-- insert values into the Projects table
INSERT INTO Projects (
        architect_id,
        contractor_id,
        customer_id,
        project_name,
        building_type,
        physical_address,
        erf_number,
        total_fee,
        amount_paid,
        project_deadline,
        project_finalised,
        completion_date
    )
-- project number is automatically incremented
VALUES (
        'ARCH001',
        'CONT001',
        'CUST001',
        'LakeHouse Garrick',
        'House',
        '14 Garrick Avenue, Cape Town, South Africa',
        '912',
        1582000.10,
        1000000.90,
        '2024-11-18',
        false,
        null
    ),
    -- Mike Tyson's House
    (
        'ARCH002',
        'CONT002',
        'CUST002',
        '',
        'House',
        '14 Royage Lane, Johannesburg, South Africa',
        '712',
        913000.22,
        900000.88,
        '2023-01-23',
        true,
        '2022-12-30'
    ),
    -- Jared Goldman's Apartment
    (
        'ARCH003',
        'CONT003',
        'CUST003',
        '',
        'Apartment',
        '14 Pink Heights, Durban, South Africa',
        '524',
        795000.18,
        723000.52,
        '2023-06-20',
        true,
        '2023-03-11'
    );

-- insert values into Architects table
INSERT INTO Architects (
        architect_id,
        architect_name,
        architect_email,
        architect_address,
        architect_tel
    )
VALUES (
        'ARCH001',
        'Vicent Van Gough',
        'vicentbuilds@gmail.com',
        '82 Mulberry Lane, Johannesburg, South Africa',
        '0109228319'
    ),
    (
        'ARCH002',
        'James Manton',
        'mantonstructures@gmail.com',
        '91 Panda Park, Pretoria, South Africa',
        '0987654321'
    ),
    (
        'ARCH003',
        'Diana Richfield',
        'richfieldarch@gmail.com',
        '98 Pine Street, Pretoria, South Africa',
        '0718384821'
    );

-- insert values into Contractors table
INSERT INTO Contractors (
        contractor_id,
        contractor_name,
        contractor_email,
        contractor_address,
        contractor_tel
    )
VALUES (
        'CONT001',
        'Pamela Harris',
        'harrisconstruction@gmail.com',
        '67 Shortcake Street, Pretoria, South Africa',
        '0109228319'
    ),
    (
        'CONT002',
        'Victoria Ridge',
        'victoriaprojects@gmail.com',
        '34 Lincoln Lane, Cape Town, South Africa',
        '0987654321'
    ),
    (
        'CONT003',
        'Ruth Reddy',
        'ruthconstructs@gmail.com',
        '45 Hemingsway Road, Durban, South Africa',
        '0718384821'
    );

-- insert values into Customers table
INSERT INTO Customers (
        customer_id,
        customer_fname,
        customer_surname,
        customer_email,
        customer_address,
        customer_tel
    )
VALUES (
        'CUST001',
        'Shonda',
        'Rhimes',
        'shondar05@gmail.com',
        '34 Mandy Peak, Johannesburg, South Africa',
        '0109228319'
    ),
    (
        'CUST002',
        'Mike',
        'Tyson',
        'mikety@gmail.com',
        '11 Umbrella Avenue, Cape Town, South Africa',
        '0183834321'
    ),
    (
        'CUST003',
        'Jared',
        'Goldman',
        'goldman@gmail.com',
        '98 Super Street, Pretoria, South Africa',
        '0283902931'
    );