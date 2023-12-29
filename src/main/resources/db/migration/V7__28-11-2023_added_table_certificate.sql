CREATE SEQUENCE IF NOT EXISTS certificate_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE certificate
(
    id                  BIGINT NOT NULL,
    url                 TEXT   NOT NULL,
    user_application_id BIGINT NOT NULL,
    CONSTRAINT pk_certificate PRIMARY KEY (id)
);

ALTER TABLE certificate
    ADD CONSTRAINT FK_CERTIFICATE_USER_APPLICATION_ID FOREIGN KEY (user_application_id) REFERENCES "user-application" (id);
