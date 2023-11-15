CREATE SEQUENCE IF NOT EXISTS course_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE course
(
    id                  BIGINT      NOT NULL,
    name                VARCHAR(64) NOT NULL,
    issuer              VARCHAR(64) NOT NULL,
    end_date            date        NOT NULL,
    user_application_id BIGINT      NOT NULL,
    CONSTRAINT pk_course PRIMARY KEY (id)
);

CREATE TABLE project_tags
(
    project_id BIGINT NOT NULL,
    tag        VARCHAR(255)
);

ALTER TABLE course
    ADD CONSTRAINT USER_APPLICATION_ID_FKYfnSP8 FOREIGN KEY (user_application_id) REFERENCES "user-application" (id);

ALTER TABLE project_tags
    ADD CONSTRAINT fk_project_tags_on_project FOREIGN KEY (project_id) REFERENCES project (id);
