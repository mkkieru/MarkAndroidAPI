CREATE DATABASE properties;
 \c properties
CREATE TABLE properties(
    id SERIAL PRIMARY KEY,
    type VARCHAR,
    location VARCHAR,
    description VARCHAR,
    value INT,
    propertyImage VARCHAR
);

CREATE DATABASE properties_Test WITH TEMPLATE properties;
