# Grocery Store Management Database
###### Created by Andrew Goss(001043951), Tanner Smith(001044004), Hayden Rawlings(001029311)

## The Main Objective
The main objective of this project was to utilize an relational database schema, Java, and a Java GUI to make a 
functional database that can perform basic create, read, update, and delete (CRUD) operations. 
The basis of our project, as the title of this README states, will be about a Grocery Store Management Database. 
It will be able to track customer information which is used to deliver their products to their address. We also 
track the information of our employees at multiple locations. The products that are delivered 
or purchased at the locations are assumed to be shared between all locations for simplicity. The database tracks the 
prices and quantity of stock available. Using these tables in our database, we can use our "delivery" system to have 
certain employees at each location deliver products to customers. 

<br>

## Technologies Used:
Our relational database schema uses <strong><em>MySQL</em></strong>. Our UI elements are constructed using 
<strong><em>Java Swing</em></strong>. The interface used to interact with our database with Java is 
<strong><em>Java Database Connectivity (JDBC)</em></strong>.<br><br>

In order to properly run this project, make sure you have the proper JDBC drivers installed in the same directory
as this project. Here is a link to the MySQl JDBC driver downloads.<br>
<a href="https://www.mysql.com/products/connector/">MySQL JDBC Drivers</a>

### More information over the database:
We have a total of 7 tables in our database:
<ol>
  <li>Customer</li>
  <li>Employee</li>
  <li>Location</li>
  <li>Products</li>
  <li>Purchase_detail</li>
  <li>Delivery</li>
  <li>Checkout</li>
</ol>
Below is the ER diagram which we created using MySQL software:<br>

<img src="https://github.com/mello9494/Database_Project/assets/125573542/5e1a8a98-2de7-4160-9004-fc7d1b8f5dd9" alt="ER Diagram" width ="1000px" height="1000px">

## How to use:
<ul>
    <li>When you open the user interface, you will be presented with a table selector, and ID selector, 
        a table showing the data in the currently selected table, text fields for editing each element
        in the selected row, and "Create", "Update", and "Remove" buttons allowing for basic CRUD 
        operations.</li>
    <li>To edit a row in the table, either select an ID in the ID selector drop down or click a row in the
        table to load the data into the text fields on the left of the window. Once you are ready, click 
        the "Update" button to commit the new data to the database. If any information does not comply with
        the database constraints, you will receive an error stating which elements needs to be fixed.</li>
    <li>To add a new row to a table, enter new information into the text fields on the left of the window. 
        Once you are ready, click "Create" to commit the data to the database. If any information does not 
        comply with the database constraints, you will receive an error stating which elements needs to be fixed.</li>
    <li>To delete a row in the table, either select the ID from the ID selector drop down or click a row in the
        table to select the row. Click the "Remove" button to delete the row from the table. This will only delete
        the data in the selected table and no other data in the rest of the database.</li>
    <li>To clear the text fields, re-select the table name in the Table selector.</li>
</ul>
