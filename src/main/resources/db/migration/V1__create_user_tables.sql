-- Create table for admin details
CREATE TABLE IF NOT EXISTS administrators
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
    user_type    VARCHAR(50)
);

CREATE INDEX administrators_phone_number_idx ON administrators (phone_number);
CREATE INDEX administrators_user_type_idx ON administrators (user_type);

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
    user_type    VARCHAR(50),
    preferences  TEXT[]
);

CREATE INDEX home_owners_phone_number_idx ON home_owners (phone_number);
CREATE INDEX home_owners_user_type_idx ON home_owners (user_type);

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
    user_type    VARCHAR(50),
    date_of_birth   DATE,
    state_of_origin VARCHAR(30),
    availability    VARCHAR(50),
    preference      VARCHAR(50)
);

CREATE INDEX helpers_phone_number_idx ON helpers (phone_number);
CREATE INDEX helpers_user_type_idx ON helpers (user_type);
