CREATE TABLE IF NOT EXISTS users
(
    id      SERIAL  NOT NULL,
    name    VARCHAR NOT NULL,
    email   VARCHAR NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories
(
    id      SERIAL NOT NULL,
    name    VARCHAR,
    CONSTRAINT pk_category PRIMARY KEY (id),
    CONSTRAINT uq_category_name UNIQUE (name)
);