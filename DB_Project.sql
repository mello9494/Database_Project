DROP DATABASE IF EXISTS DB_Project;
CREATE DATABASE DB_Project;
USE DB_Project;

Create TABLE Customer(
	Customer_ID int  NOT NULL,
    Gender char(2) NOT NULL,
    Email varchar(20) NOT NULL,
    Fname varchar(20) NOT NULL,
    Lname varchar(20) NOT NULL,
    Phone_Number char(10) NOT NULL,
    Address varchar(50) NOT NULL,
    Primary Key (Customer_ID)
);

Create TABLE Employee(
	Employee_ID int  NOT NULL,
    Fname varchar(20) NOT NULL,
    Lname varchar(20) NOT NULL,
    Gender char(2) NOT NULL,
	Location_ID int NOT NULL,
    State varchar(20) NOT NULL,
    City varchar(20) NOT NULL,
    Address varchar(20) NOT NULL,
    Employee_Desc varchar(40) NOT NULL,
    Primary Key (Employee_ID),
    Foreign Key (Location_ID) REFERENCES Location(Location_ID)
);

Create TABLE Location(
	Location_ID int NOT NULL,
    Address varchar(20) NOT NULL,
    State varchar(20) NOT NULL,
    City varchar (20) NOT NULL,
    Zip_Code char (5) NOT NULL,
	Phone_Number char(10) NOT NULL,
    Primary Key (Location_ID)
);

Create TABLE Products(
	Product_ID int  NOT NULL,
    Prod_Description varchar (100) NOT NULL,
    Quantity int (5) NOT NULL,
    primary key (Product_ID)
);

Create TABLE Purchase_detail(
	Transaction_ID int NOT NULL ,
    Product_ID int  NOT NULL,
    Product_Quantity int  NOT NULL,
    Product_Price float(6) NOT NULL,
    Customer_ID int  NOT NULL,
    Employee_ID int  NOT NULL,
    Primary Key (Transaction_ID),
    Foreign Key (Product_ID) REFERENCES Products(Product_ID),
    Foreign Key (Customer_ID) REFERENCES Customer(Customer_ID),
	Foreign Key (Employee_ID) REFERENCES Employee(Employee_ID)
);

Create TABLE Delivery(
	Delivery_ID int NOT NULL,
    Employee_ID int NOT NULL,
    Location_ID int NOT NULL,
    Transaction_ID int NOT NULL,
    Address varchar (50) NOT NULL,
    Primary key (Delivery_ID, Employee_ID, Location_ID),
    Foreign Key (Employee_ID) REFERENCES Employee(Employee_ID),
    Foreign Key (Location_ID) REFERENCES Location(Location_ID),
	Foreign Key (Transaction_ID) REFERENCES Purchase_detail(Transaction_ID)
);

Create TABLE Checkout(
	Checkout_ID int NOT NULL,
    Transaction_ID int NOT NULL,
    Checkout_Date varchar (8) NOT NULL,
    Amount float (6) NOT NULL,
    Payment_Method varchar (20) NOT NULL,
	Location_ID int NOT NULL,
	Primary key (Checkout_ID,Location_ID),
    Foreign Key (Location_ID) REFERENCES Location(Location_ID),
	Foreign Key (Transaction_ID) REFERENCES Purchase_detail(Transaction_ID)
);


/* loading values into tables /*

/* Customer values */
insert into Customer values (103869,'M','demoarch@gmail.com','Derrak','Johnson',2346459834,'243 CourtHouse Ct');
insert into Customer values (245689,'F','girlyfin@gmail.com','Hannah','Fleaker',2349887670,'300 FamilyRight St');
insert into Customer values (986532,'M','dMan@gmail.com','Danny','Phantom',2341236770,'543 CandyLane St');


/* Employee values */
insert into Employee values(246803,'Joey','Kelper','M',966376,'Texas','Dallas','262 Oakland St','Register Duty');
insert into Employee values(369121,'Mable','Pines','F',255505,'Maine','Portland','532 Gravity Hills Dr','Stocker');
insert into Employee values(708931,'Dipper','Pines','M',255505,'Maine','Portland','532 Gravity Hills Dr','Assistant store manager');


/*Location values*/
insert into Location values(255505,'255 Welshman St','Maine','Portland','97086','1236547658');
insert into Location values(966376,'583 Mainland St','Texas','Dallas','75201','2349839021');

Select *
From Customer;

Select *
From Location;

Select *
From Employee;

Delete from Employee;
Delete from Location;



