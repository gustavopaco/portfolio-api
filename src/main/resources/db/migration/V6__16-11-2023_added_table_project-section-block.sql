CREATE SEQUENCE IF NOT EXISTS project_section_block_seq START WITH 1 INCREMENT BY 1;

CREATE TABLE project_section_block
(
    id          BIGINT      NOT NULL,
    title       VARCHAR(20) NOT NULL,
    description TEXT        NOT NULL,
    project_id  BIGINT      NOT NULL,
    CONSTRAINT pk_project_section_block PRIMARY KEY (id)
);

ALTER TABLE project_section_block
    ADD CONSTRAINT PROJECT_SECTION_BLOCK_PROJECT_FK FOREIGN KEY (project_id) REFERENCES project (id);
