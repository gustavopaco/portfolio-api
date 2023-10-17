CREATE SEQUENCE IF NOT EXISTS bio_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE bio
(
    id                  BIGINT NOT NULL,
    avatar_url          TEXT,
    presentation        TEXT,
    testimonial         TEXT,
    user_application_id BIGINT NOT NULL,
    CONSTRAINT pk_bio PRIMARY KEY (id)
);

ALTER TABLE bio
    ADD CONSTRAINT USER_APPLICATION_ID_FK3mHudI FOREIGN KEY (user_application_id) REFERENCES "user-application" (id);
