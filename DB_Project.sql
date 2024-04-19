DROP DATABASE IF EXISTS DB_Project;
CREATE DATABASE DB_Project;
USE DB_Project;

Create TABLE Customer(
	Customer_ID int (11) NOT NULL,
    Gender char(2) NOT NULL,
    Email varchar(20) NOT NULL,
    Fname varchar(20) NOT NULL,
    Lname varchar(20) NOT NULL,
    Phone_Number char(10) NOT NULL,
    Address varchar(50) NOT NULL,
    Primary Key (Customer_ID)
);

Create TABLE Employee(
	Employee_ID int (11) NOT NULL,
	Location_ID varchar(20) NOT NULL,
    State varchar(20) NOT NULL,
    City varchar(20) NOT NULL,
    Address varchar(20) NOT NULL,
    Employee_Desc varchar(40) NOT NULL,
    Primary Key (Employee_ID) NOT NULL,
    Foreign Key (Loaction_ID) REFERENCES Location(Loaction_ID)
);

Create TABLE Location(
	Location_ID int(11) NOT NULL,
    Address varchar(20) NOT NULL,
    Phone_Number char(10) NOT NULL,
    Primary Key (Location_ID)
);

Create TABLE Products(
	Product_ID int (11) NOT NULL,
    Prod_Description varchar (100) NOT NULL,
    Quantity int (5) NOT NULL,
    primary key (Product_ID)
);

Create TABLE Purchase_detail(
	Transaction_ID int(11) NOT NULL ,
    Product_ID int (11) NOT NULL,
    Product_Quantity int (4) NOT NULL,
    Product_Price float(6) NOT NULL,
    Customer_ID int (11) NOT NULL,
    Employee_ID int (11) NOT NULL,
    Primary Key (Transaction_ID),
    Foreign Key (Product_ID) REFERENCES Products(Product_ID),
    Foreign Key (Customer_ID) REFERENCES Customer(Customer_ID),
	Foreign Key (Employee_ID) REFERENCES Employee(Employee_ID)
);

Create TABLE Delivery(
	Delivery_ID int(11) NOT NULL,
    Employee_ID int (11) NOT NULL,
    Location_ID int (11) NOT NULL,
    Transaction_ID int (11) NOT NULL,
    Address varchar (50) NOT NULL,
    Primary key (Delivery_ID, Employee_ID, Location_ID),
    Foreign Key (Employee_ID) REFERENCES Employee(Employee_ID),
    Foreign Key (Location_ID) REFERENCES Location(Location_ID),
	Foreign Key (Transaction_ID) REFERENCES Purchase_detail(Transaction_ID)
);

Create TABLE Checkout(
	Checkout_ID int (11) NOT NULL,
    Transaction_ID int (11) NOT NULL,
    Checkout_Date varchar (8) NOT NULL,
    Amount float (6) NOT NULL,
    Payment_Method varchar (20) NOT NULL,
	Location_ID int (11) NOT NULL,
	Primary key (Checkout_ID,Location_ID),
    Foreign Key (Location_ID) REFERENCES Location(Location_ID),
	Foreign Key (Transaction_ID) REFERENCES Purchase_detail(Transaction_ID)
);