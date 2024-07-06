-- Create table category
CREATE TABLE IF NOT EXISTS category (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    description VARCHAR(255),
    name VARCHAR(255)
);

-- Create table product
CREATE TABLE IF NOT EXISTS product (
    id INT NOT NULL PRIMARY KEY AUTO_INCREMENT,
    available_quantity DOUBLE NOT NULL,
    description VARCHAR(255),
    name VARCHAR(255),
    price DECIMAL(38, 2),
    category_id INT,
    CONSTRAINT fk_category_id FOREIGN KEY (category_id) REFERENCES category(id)
);
