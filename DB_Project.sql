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
insert into Customer values (567320,'F','shrexy@gmail.com','Taylor','Swift',2349997577,'300 MILDRED AVE','New Mexico','Albequerque','87111');
insert into Customer values (946719,'M','dontmailme@gmail.com','Charles','Charlie',2347641889,'8271 Brookside St','New York','Staten Island','10306');
insert into Customer values (156789,'M','plsemailme@gmail.com','Chuck','Chucky',234612347,'4994 S MURRAY BLVD','Utah','Salt Lake City','10306');

/* Employee values */
insert into Employee values(246803,'Joey','Kelper','M',966376,'Texas','Dallas','262 Oakland St','Register Duty');
insert into Employee values(369121,'Mable','Pines','F',255505,'Maine','Portland','532 Gravity Hills Dr','Stocker');
insert into Employee values(708931,'Dipper','Pines','M',255505,'Maine','Portland','532 Gravity Hills Dr','Assistant store manager');
insert into Employee values(453201,'Jaden','Crowney','M',966376,'Maine','Portland','908 Saturn Head St','Delivery');
insert into Employee values(766409,'Joey','Wheeler','M',635480,'New York','Staten Island','169 CROMWELL AVE APT ','Delivery');
insert into Employee values(844653,'Nelson','Corwin','M',743866,'New Mexico','South Dolores','34961 Hand Corner','Delivery');
insert into Employee values(452888,'Nikita','Swaniawsky','F',443298,'Utah','New Aidenborough','602 Morissette Ranch','Delivery');
insert into Employee values(454901,'Uriel','Beatty','F',743866,'New Mexico','Gloverton','742 Kessler Estate','Delivery');

/*Location values*/
insert into Location values(255505,'255 Welshman St','Maine','Portland','97086','1236547658');
insert into Location values(966376,'583 Mainland St','Texas','Dallas','75201','2349839021');
insert into Location values(635480,'960 Pierce St.','New York','Staten Island','10306','3478625590');
insert into Location values(743866,'308 Negra Arroyo Lane','New Mexico','Albuquerque','87111','5059824433');
insert into Location values(443298,'9275 S 1300 W','Utah','Salt Lake city','84088','8015625496');

/* Product Values*/
insert into Products values(103476,'Yogurt',50);
insert into Products values(986566,'Chicken breast',100);
insert into Products values(763105,'Lettuce',30);
insert into Products values(216708,'Toothpaste',35);
insert into Products values(906438,'Bread',60);

/*Purchase_detail values */
insert into Purchase_detail values(111123,255505,103476,2,7.99,103869,453201);/*Maine*/
insert into Purchase_detail values(111134,966376,986566,3,8.99,245689,246803);/*Texas cash*/
insert into Purchase_detail values(111145,635480,906438,4,4.99,946719,766409);/*New York*/
insert into Purchase_detail values(111165,743866,763105,5,3.99,567320,844653);/*New Mexico*/
insert into Purchase_detail values(111179,443298,216708,1,5.99,156789,452888);/*Utah*/
insert into Purchase_detail values(111189,966376,763105,1,3.99,986532,246803);/*Texas*/

/*Delivery values */
insert into Delivery values(999981,453201,255505,111123,'243 CourtHouse Ct');/*Maine*/
insert into Delivery values(999982,246803,966376,111189,'543 CandyLane St');/*Texas*/
insert into Delivery values(999983,844653,743866,111165,'300 MILDRED AVE');/*New Mexico*/
insert into Delivery values(999984,452888,443298,111179,'4994 S MURRAY BLVD');/*Utah*/
insert into Delivery values(999985,766409,635480,111145,'8271 Brookside St');/*New York*/

/*Checkout values*/
insert into Checkout values(345789,111123,'2/10/2024','credit card',255505);/*Maine*/
insert into Checkout values(908070,111134,'3/23/2024','cash',966376);/*In person and Texas loc*/
insert into Checkout values(908080,111145,'3/30/2024','credit card',635480);/* New York*/
insert into Checkout values(908060,111165,'3/10/2024','credit card',743866);/*New Mexico*/
insert into Checkout values(908050,111179,'4/10/2024','credit card',443298);/*Utah*/
insert into Checkout values(908040,111189,'6/17/2024','credit card',966376);/*Texas*/

Select *
From Customer;

Select *
From Location;

Select *
From Employee
order by Fname;

Select *
From Products;

Select *
From Purchase_detail;

Select*
from Checkout;

Select * 
From Delivery;

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




