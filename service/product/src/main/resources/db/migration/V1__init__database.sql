CREATE TABLE IF NOT EXISTS category (
    id INT NOT NULL AUTO_INCREMENT,
    description VARCHAR(255),
    name VARCHAR(255),
    PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS product (
    id INT NOT NULL AUTO_INCREMENT,
    available_quantity DOUBLE PRECISION NOT NULL,
    description VARCHAR(255),
    name VARCHAR(255),
    price NUMERIC(38, 2),
    category_id INT,
    PRIMARY KEY (id),
    FOREIGN KEY (category_id) REFERENCES category(id)
);