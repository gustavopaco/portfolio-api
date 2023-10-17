CREATE SEQUENCE IF NOT EXISTS bio_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS project_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS skill_seq START WITH 1 INCREMENT BY 1;

CREATE SEQUENCE IF NOT EXISTS token_confirmation_seq START WITH 1 INCREMENT BY 50;

CREATE SEQUENCE IF NOT EXISTS user_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE bio
(
    id                  BIGINT NOT NULL,
    avatar_url          TEXT,
    presentation        TEXT,
    testimonial         TEXT,
    user_application_id BIGINT NOT NULL,
    CONSTRAINT pk_bio PRIMARY KEY (id)
);

CREATE TABLE project
(
    id                  BIGINT      NOT NULL,
    name                VARCHAR(20) NOT NULL,
    description         TEXT        NOT NULL,
    url                 TEXT,
    picture_url         TEXT,
    picture_orientation VARCHAR(20),
    status              VARCHAR(20) NOT NULL,
    user_application_id BIGINT      NOT NULL,
    CONSTRAINT pk_project PRIMARY KEY (id)
);

CREATE TABLE skill
(
    id                  BIGINT      NOT NULL,
    name                VARCHAR(20) NOT NULL,
    description         VARCHAR(100),
    rating              INTEGER     NOT NULL,
    picture_url         TEXT        NOT NULL,
    user_application_id BIGINT      NOT NULL,
    CONSTRAINT pk_skill PRIMARY KEY (id)
);

CREATE TABLE token_confirmation
(
    id                  BIGINT                      NOT NULL,
    token               TEXT                        NOT NULL,
    created_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    expired_at          TIMESTAMP WITHOUT TIME ZONE NOT NULL,
    confirmed_at        TIMESTAMP WITHOUT TIME ZONE,
    user_application_id BIGINT                      NOT NULL,
    CONSTRAINT pk_token_confirmation PRIMARY KEY (id)
);

CREATE TABLE "user-application"
(
    id       BIGINT       NOT NULL,
    username VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    nickname VARCHAR(30)  NOT NULL,
    role     VARCHAR(20)  NOT NULL,
    enabled  BOOLEAN      NOT NULL,
    locked   BOOLEAN      NOT NULL,
    CONSTRAINT "pk_user-application" PRIMARY KEY (id)
);

ALTER TABLE "user-application"
    ADD CONSTRAINT unique_nickname UNIQUE (nickname);

ALTER TABLE "user-application"
    ADD CONSTRAINT unique_username UNIQUE (username);

ALTER TABLE token_confirmation
    ADD CONSTRAINT USER_APPLICATION_ID_FK FOREIGN KEY (user_application_id) REFERENCES "user-application" (id);

ALTER TABLE skill
    ADD CONSTRAINT USER_APPLICATION_ID_FK75rFh0 FOREIGN KEY (user_application_id) REFERENCES "user-application" (id);

ALTER TABLE bio
    ADD CONSTRAINT USER_APPLICATION_ID_FKnUvHpJ FOREIGN KEY (user_application_id) REFERENCES "user-application" (id);

ALTER TABLE project
    ADD CONSTRAINT USER_APPLICATION_ID_FKsQjlHP FOREIGN KEY (user_application_id) REFERENCES "user-application" (id);
