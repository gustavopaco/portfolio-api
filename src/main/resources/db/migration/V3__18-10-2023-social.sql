CREATE SEQUENCE IF NOT EXISTS social_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE social
(
    id                  BIGINT NOT NULL,
    facebook            VARCHAR(255),
    instagram           VARCHAR(255),
    linkedin            VARCHAR(255),
    github              VARCHAR(255),
    twitter             VARCHAR(255),
    youtube             VARCHAR(255),
    user_application_id BIGINT NOT NULL,
    CONSTRAINT pk_social PRIMARY KEY (id)
);

ALTER TABLE social
    ADD CONSTRAINT FK_SOCIAL_USER_APPLICATION FOREIGN KEY (user_application_id) REFERENCES "user-application" (id);
