CREATE TABLE IF NOT EXISTS users
(
    id          BIGSERIAL       NOT NULL,
    name        VARCHAR(50)     NOT NULL,
    email       VARCHAR(255)    NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id),
    CONSTRAINT uq_user_email UNIQUE (email)
);

CREATE TABLE IF NOT EXISTS categories
(
    id          BIGSERIAL       NOT NULL,
    name        VARCHAR(255),
    CONSTRAINT pk_category PRIMARY KEY (id),
    CONSTRAINT uq_category_name UNIQUE (name)
);

CREATE TABLE IF NOT EXISTS compilations
(
    id          BIGSERIAL       NOT NULL,
    title       VARCHAR(255),
    pinned      BOOLEAN,
    CONSTRAINT pk_compilation PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS locations
(
    id          BIGSERIAL NOT NULL,
    latitude    FLOAT8,
    longitude   FLOAT8,
    CONSTRAINT pk_location PRIMARY KEY (id)
);

CREATE TABLE IF NOT EXISTS events
(
    id                 BIGSERIAL                        NOT NULL,
    annotation         VARCHAR                          NOT NULL,
    category_id        BIGINT                           NOT NULL,
    created            TIMESTAMP WITHOUT TIME ZONE      NOT NULL,
    description        VARCHAR                          NOT NULL,
    event_date         TIMESTAMP WITHOUT TIME ZONE      NOT NULL,
    initiator_id       BIGINT                           NOT NULL,
    location_id        BIGINT                           NOT NULL,
    is_paid            BOOLEAN,
    participant_limit  BIGINT,
    published_on       TIMESTAMP WITHOUT TIME ZONE,
    request_moderation BOOLEAN,
    status             INTEGER,
    title              VARCHAR                          NOT NULL,
    compilation_id     BIGINT,
    CONSTRAINT pk_event PRIMARY KEY (id),
    CONSTRAINT fk_event_category FOREIGN KEY (category_id) REFERENCES categories (id),
    CONSTRAINT fk_event_initiator FOREIGN KEY (initiator_id) REFERENCES users (id),
    CONSTRAINT fk_event_location FOREIGN KEY (location_id) REFERENCES locations (id),
    CONSTRAINT fk_event_compilation FOREIGN KEY (compilation_id) REFERENCES compilations (id)
);

CREATE TABLE IF NOT EXISTS requests
(
    id              BIGSERIAL                  NOT NULL,
    event_id        BIGINT                     NOT NULL,
    requester_id    BIGINT                     NOT NULL,
    created         TIMESTAMP WITHOUT TIME ZONE     NOT NULL,
    status          INTEGER,
    CONSTRAINT pk_request PRIMARY KEY (id),
    CONSTRAINT fk_request_author FOREIGN KEY (requester_id) REFERENCES users (id),
    CONSTRAINT fk_request_event FOREIGN KEY (event_id) REFERENCES events (id)
);

CREATE TABLE IF NOT EXISTS compilations_events
(
    compilation_id  BIGINT,
    event_id        BIGINT,
    CONSTRAINT event_compilation PRIMARY KEY (compilation_id, event_id),
    CONSTRAINT fk_event_id FOREIGN KEY (event_id) REFERENCES events (id),
    CONSTRAINT fk_compilation_id FOREIGN KEY (compilation_id) REFERENCES compilations (id)
);