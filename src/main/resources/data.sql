DROP TABLE IF EXISTS products;
 
CREATE TABLE products (
  id INT AUTO_INCREMENT PRIMARY KEY,
  code VARCHAR(20) NOT NULL UNIQUE,
  name VARCHAR(250) NOT NULL,
  price DECIMAL(10, 2) NOT NULL,
  detail VARCHAR(250) NOT NULL
);

DROP TABLE IF EXISTS carts;
 
CREATE TABLE carts (
  id INT AUTO_INCREMENT PRIMARY KEY,
  user_id INT
);

DROP TABLE IF EXISTS users;
 
CREATE TABLE users (
  id INT AUTO_INCREMENT PRIMARY KEY,
  email VARCHAR(250) NOT NULL UNIQUE,
  hashed_password VARCHAR(250) NOT NULL,
  session_token VARCHAR(250)
);

DROP TABLE IF EXISTS product_items;
 
CREATE TABLE product_items (
  id INT AUTO_INCREMENT PRIMARY KEY,
  product_id INT NOT NULL,
  cart_id INT NOT NULL,
  quantity INT NOT NULL,
  price DECIMAL(10, 2) NOT NULL
);

INSERT INTO products (name, price, detail, code) VALUES
  ('Desktop', '100.99', 'Desktop for office', 'QWERTYCL'),
  ('Bicycle', '45.89', 'Bicycle Ring 26', 'QWERTYWE'),
  ('Bottle of water 1L', '1.38', 'Bottle of water', 'QWERTYOK'),
  ('Chair', '35.60', 'Chair for office', 'QWERTYNJ'),
  ('MacBook Pro 2019', '5000.00', 'MacBook Pro Laptop', 'QWERTYFH'),
  ('Windows 2010', '9.78', 'Windows 2010 installation DVD', 'QWERTYAS'),
  ('Sport shoes M', '35.99', 'Sport shoes for man', 'QWERTYED'),
  ('Tomato', '00.98', 'Tomatoes 1Kg', 'QWERRTYML'),
  ('Backpack', '65.99', 'Backpack for laptops', 'QWERTYCF'),
  ('Mouse unwired', '8.98', 'Unwired mouse for PC/Laptops', 'QWERTYPO'),
  ('Programming Java book', '36.98', 'Book about the programming language Java 8', 'QWERTYZS'),
  ('Xiaomi Redmi', '200.56', 'Xiaomi Redmi cell phone', 'QWERTYPK'),
  ('Display 30', '39.89', 'Display for PC', 'QWERTYSO');
  
INSERT INTO users (email, hashed_password) VALUES
  ('user@nexio.com', '5e884898da28047151d0e56f8dc6292773603d0d6aabbdd62a11ef721d1542d8');