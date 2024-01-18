CREATE SEQUENCE IF NOT EXISTS resume_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE resume
(
    id                  BIGINT       NOT NULL,
    url                 TEXT         NOT NULL,
    content_type        VARCHAR(255) NOT NULL,
    user_application_id BIGINT       NOT NULL,
    CONSTRAINT pk_resume PRIMARY KEY (id)
);

ALTER TABLE bio
    ADD CONSTRAINT uc_bio_user_application UNIQUE (user_application_id);

ALTER TABLE resume
    ADD CONSTRAINT uc_resume_user_application UNIQUE (user_application_id);

ALTER TABLE social
    ADD CONSTRAINT uc_social_user_application UNIQUE (user_application_id);

ALTER TABLE resume
    ADD CONSTRAINT FK_RESUME_USER_APPLICATION FOREIGN KEY (user_application_id) REFERENCES "user-application" (id);
