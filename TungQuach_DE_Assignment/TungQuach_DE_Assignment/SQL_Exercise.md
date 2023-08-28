# SQL Exercise

## Problem Statement
Assume you have the following tables with the mentioned columns

**Customers table**  
customer_id first_name last_name email

**Products Table**  
product_id product_name unit_price category

**Orders Table**  
order_id order_date customer_id total_amount  

Can you write the SQL statements for the below questions:
1. Retrieve the first name, last name, and email of all customers from the "Customers" table.
2. Retrieve the product name and unit price of all products with a unit price greater than $50, ordered by unit price in descending order.
3. Retrieve the order date, customer's first name, and total amount of each order from the "Orders" and "Customers" tables. Join these tables on the customer ID.
4. Increase the unit price by 10% for all products in the "Electronics" category.

## Answer
### Exercise 1
Retrieve the first name, last name, and email of all customers from the "Customers" table.
```sql
SELECT
  first_name,
  last_name,
  email
FROM
  Customers
;
```
### Exercise 2
Retrieve the product name and unit price of all products with a unit price greater than $50, ordered by unit price in descending order.
```sql
SELECT
  product_name,
  unit_price
FROM
  Products
WHERE
  unit_price > 50
ORDER BY unit_price DESC
;
```
### Exercise 3
```sql
SELECT
  order_date,
  first_name,
  total_amount
FROM Customers c
JOIN Orders o
  on c.customer_id = o.customer_id
;
```
### Exercise 4
```sql
UPDATE Products
SET
  unit_price = 1.1 * unit_price
WHERE category = 'Electronics'
;
```