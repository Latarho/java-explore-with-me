CREATE TABLE IF NOT EXISTS users
(
    id      SERIAL  NOT NULL,
    name    VARCHAR NOT NULL,
    email   VARCHAR NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uq_user_email UNIQUE (email)
);