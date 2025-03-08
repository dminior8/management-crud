CREATE TABLE product (
                         id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                         name VARCHAR(20) UNIQUE NOT NULL,
                         description TEXT,
                         category VARCHAR(255),
                         stock INT NOT NULL CHECK (stock >= 0),
                         price DECIMAL(10,2),
                         created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);


CREATE TABLE blocked_word (
                              id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                              name VARCHAR(255) NOT NULL
);

CREATE TABLE product_history (
                                 id UUID PRIMARY KEY DEFAULT gen_random_uuid(),
                                 product_id UUID NOT NULL,
                                 changed_field VARCHAR(255) NOT NULL,
                                 old_value TEXT,
                                 new_value TEXT,
                                 change_timestamp TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                 FOREIGN KEY (product_id) REFERENCES product(id) ON DELETE CASCADE
);
