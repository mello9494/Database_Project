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
    State varchar(20) NOT NULL,
    City varchar(20) NOT NULL,
    Zip_Code char(5) NOT NULL,
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
    Location_ID int NOT NULL,
    Product_ID int  NOT NULL,
    Product_Quantity int  NOT NULL,
    Product_Price float(6) NOT NULL,
    Customer_ID int  NOT NULL,
    Employee_ID int  NOT NULL,
    Primary Key (Transaction_ID),
    Foreign Key (Product_ID) REFERENCES Products(Product_ID),
    Foreign Key (Customer_ID) REFERENCES Customer(Customer_ID),
	Foreign Key (Employee_ID) REFERENCES Employee(Employee_ID),
    Foreign Key (Location_ID) REFERENCES Location(Location_ID)
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
    Checkout_Date varchar (10) NOT NULL,
    Payment_Method varchar (20) NOT NULL,
	Location_ID int NOT NULL,
	Primary key (Checkout_ID,Location_ID),
    Foreign Key (Location_ID) REFERENCES Location(Location_ID),
	Foreign Key (Transaction_ID) REFERENCES Purchase_detail(Transaction_ID)
);


/* loading values into tables /*

/* Customer values */
insert into Customer values (103869,'M','demoarch@gmail.com','Derrak','Johnson',2346459834,'243 CourtHouse Ct','Maine','Bangor','44401');
insert into Customer values (245689,'F','girlyfin@gmail.com','Hannah','Fleaker',2349887670,'300 FamilyRight St','Texas','Dallas','75201');
insert into Customer values (986532,'M','dMan@gmail.com','Danny','Phantom',2341236770,'543 CandyLane St','Texas','Aledo','76008');


/* Employee values */
insert into Employee values(246803,'Joey','Kelper','M',966376,'Texas','Dallas','262 Oakland St','Register Duty');
insert into Employee values(369121,'Mable','Pines','F',255505,'Maine','Portland','532 Gravity Hills Dr','Stocker');
insert into Employee values(708931,'Dipper','Pines','M',255505,'Maine','Portland','532 Gravity Hills Dr','Assistant store manager');
insert into Employee values(453201,'Jaden','Crowney','M',966376,'Maine','Portland','908 Saturn Head St','Delivery');


/*Location values*/
insert into Location values(255505,'255 Welshman St','Maine','Portland','97086','1236547658');
insert into Location values(966376,'583 Mainland St','Texas','Dallas','75201','2349839021');

/* Product Values*/
insert into Products values(103476,'Yogurt',50);
insert into Products values(986566,'Chicken breast',100);
insert into Products values(763105,'Lettuce',30);
insert into Products values(216708,'Toothpaste',35);
insert into Products values(906438,'Bread',60);

/*Purchase_detail values */
insert into Purchase_detail values(111123,255505,103476,2,7.99,103869,453201);
insert into Purchase_detail values(111134,966376,986566,3,8.99,245689,246803);

/*Delivery values */
insert into Delivery values(999981,453201,255505,111123,'243 CourtHouse Ct');

/*Checkout values*/
insert into Checkout values(345789,111123,'2/10/2024','credit card',255505);
insert into Checkout values(908070,111134,'3/23/2024','cash',966376);

Select *
From Customer;

Select *
From Location;

Select *
From Employee;

Select *
From Products;

Select *
From Purchase_detail;

Select*
from Checkout;

/*Example to see if database works*/
Select Customer.Fname
From Customer,Purchase_detail,Location
where Purchase_detail.Customer_ID = Customer.Customer_ID and Location.Location_ID = Purchase_detail.Location_ID and Purchase_detail.Transaction_ID=111123;



Delete from Checkout;
Delete from Delivery;
Delete from Purchase_detail;
Delete from Products;
Delete from Customer;
Delete from Employee;
Delete from Location;
SET sql_safe_updates = 0;




