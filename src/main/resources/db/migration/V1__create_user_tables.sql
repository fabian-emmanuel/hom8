
-- Create table for home owner details
CREATE TABLE IF NOT EXISTS home_owners
(
    id           BIGSERIAL PRIMARY KEY,
    created_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    created_by   BIGINT,
    updated_by   BIGINT,
    first_name   VARCHAR(30),
    last_name    VARCHAR(30),
    phone_number VARCHAR(15),
    address      VARCHAR(255),
    pin          VARCHAR(255),
    is_active    BOOLEAN,
    deleted      BOOLEAN DEFAULT FALSE,
    user_type    VARCHAR(50), -- This indicates if the user is a 'HomeOwner' or a 'Helper'
    preferences  TEXT[]
);

-- Create table for helper details
CREATE TABLE IF NOT EXISTS helpers
(
    id           BIGSERIAL PRIMARY KEY,
    created_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    updated_at   TIMESTAMP WITHOUT TIME ZONE DEFAULT CURRENT_TIMESTAMP,
    created_by   BIGINT,
    updated_by   BIGINT,
    first_name   VARCHAR(30),
    last_name    VARCHAR(30),
    phone_number VARCHAR(15),
    address      VARCHAR(255),
    pin          VARCHAR(255),
    is_active    BOOLEAN,
    deleted      BOOLEAN DEFAULT FALSE,
    user_type    VARCHAR(50), -- This indicates if the user is a 'HomeOwner' or a 'Helper'
    date_of_birth   DATE,
    state_of_origin VARCHAR(30),
    availability    VARCHAR(50),
    preference      VARCHAR(50)
);
