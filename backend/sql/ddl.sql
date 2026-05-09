-- =============================================================
-- ddl.sql  –  Shifter database schema
-- PostgreSQL. All enum columns are VARCHAR (EnumType.STRING).
-- Sequences use increment 50 to match Hibernate 6 default.
-- Usage:
--   1. psql -U <user> -d <db> -f drop.sql
--   2. psql -U <user> -d <db> -f ddl.sql
-- =============================================================

BEGIN;

-- =============================================================
-- SEQUENCES  (increment 50 = Hibernate 6 default allocationSize)
-- =============================================================
CREATE SEQUENCE language_seq                       INCREMENT 50 START 1;
CREATE SEQUENCE admin_seq                          INCREMENT 50 START 1;
CREATE SEQUENCE expert_seq                         INCREMENT 50 START 1;
CREATE SEQUENCE user_seq                           INCREMENT 50 START 1;
CREATE SEQUENCE topic_seq                          INCREMENT 50 START 1;
CREATE SEQUENCE topic_translation_seq              INCREMENT 50 START 1;
CREATE SEQUENCE skill_seq                          INCREMENT 50 START 1;
CREATE SEQUENCE skill_translation_seq              INCREMENT 50 START 1;
CREATE SEQUENCE course_seq                         INCREMENT 50 START 1;
CREATE SEQUENCE course_version_seq                 INCREMENT 50 START 1;
CREATE SEQUENCE course_module_seq                  INCREMENT 50 START 1;
CREATE SEQUENCE course_lecture_seq                 INCREMENT 50 START 1;
CREATE SEQUENCE course_price_seq                   INCREMENT 50 START 1;
CREATE SEQUENCE course_translation_seq             INCREMENT 50 START 1;
CREATE SEQUENCE course_module_translation_seq      INCREMENT 50 START 1;
CREATE SEQUENCE course_lecture_translation_seq     INCREMENT 50 START 1;
CREATE SEQUENCE related_course_seq                 INCREMENT 50 START 1;
CREATE SEQUENCE course_activity_event_seq          INCREMENT 50 START 1;
CREATE SEQUENCE quiz_seq                           INCREMENT 50 START 1;
CREATE SEQUENCE quiz_translation_seq               INCREMENT 50 START 1;
CREATE SEQUENCE quiz_question_seq                  INCREMENT 50 START 1;
CREATE SEQUENCE quiz_question_translation_seq      INCREMENT 50 START 1;
CREATE SEQUENCE quiz_answer_option_seq             INCREMENT 50 START 1;
CREATE SEQUENCE quiz_answer_option_translation_seq INCREMENT 50 START 1;
CREATE SEQUENCE quiz_question_skill_seq            INCREMENT 50 START 1;
CREATE SEQUENCE quiz_attempt_seq                   INCREMENT 50 START 1;
CREATE SEQUENCE quiz_attempt_answer_seq            INCREMENT 50 START 1;
CREATE SEQUENCE enrollment_seq                     INCREMENT 50 START 1;
CREATE SEQUENCE lecture_progress_seq               INCREMENT 50 START 1;
CREATE SEQUENCE user_skill_seq                     INCREMENT 50 START 1;
CREATE SEQUENCE user_skill_snapshot_seq            INCREMENT 50 START 1;
CREATE SEQUENCE certificate_seq                    INCREMENT 50 START 1;
CREATE SEQUENCE review_seq                         INCREMENT 50 START 1;
CREATE SEQUENCE learning_path_seq                  INCREMENT 50 START 1;
CREATE SEQUENCE learning_path_translation_seq      INCREMENT 50 START 1;
CREATE SEQUENCE curated_learning_path_seq          INCREMENT 50 START 1;
CREATE SEQUENCE personalized_learning_path_seq     INCREMENT 50 START 1;
CREATE SEQUENCE user_learning_path_seq             INCREMENT 50 START 1;
CREATE SEQUENCE learning_path_course_seq           INCREMENT 50 START 1;
CREATE SEQUENCE bundle_seq                         INCREMENT 50 START 1;
CREATE SEQUENCE bundle_translation_seq             INCREMENT 50 START 1;
CREATE SEQUENCE curated_bundle_seq                 INCREMENT 50 START 1;
CREATE SEQUENCE personalized_bundle_seq            INCREMENT 50 START 1;
CREATE SEQUENCE user_bundle_seq                    INCREMENT 50 START 1;
CREATE SEQUENCE order_seq                          INCREMENT 50 START 1;
CREATE SEQUENCE order_details_seq                  INCREMENT 50 START 1;
CREATE SEQUENCE payment_seq                        INCREMENT 50 START 1;
CREATE SEQUENCE meeting_email_reminder_seq         INCREMENT 50 START 1;

-- =============================================================
-- SHARED
-- =============================================================

CREATE TABLE language (
    id            BIGINT       NOT NULL DEFAULT nextval('language_seq'),
    language_code VARCHAR(255) NOT NULL,
    name          VARCHAR(255) NOT NULL,
    native_name   VARCHAR(255) NOT NULL,
    CONSTRAINT pk_language PRIMARY KEY (id)
);

-- =============================================================
-- IDENTITY  (Account is a MappedSuperclass — no own table)
-- admin, expert, _user each carry the base Account columns
-- =============================================================

CREATE TABLE admin (
    id             BIGINT       NOT NULL DEFAULT nextval('admin_seq'),
    name           VARCHAR(255),
    email          VARCHAR(255) NOT NULL,
    login_provider VARCHAR(255) NOT NULL,
    password_hash  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_admin PRIMARY KEY (id)
);
CREATE UNIQUE INDEX uq_admin_email ON admin (email);

CREATE TABLE expert (
    id             BIGINT       NOT NULL DEFAULT nextval('expert_seq'),
    name           VARCHAR(255),
    email          VARCHAR(255) NOT NULL,
    login_provider VARCHAR(255) NOT NULL,
    password_hash  VARCHAR(255) NOT NULL,
    CONSTRAINT pk_expert PRIMARY KEY (id)
);
CREATE UNIQUE INDEX uq_expert_email ON expert (email);

CREATE TABLE _user (
    id                     BIGINT       NOT NULL DEFAULT nextval('user_seq'),
    name                   VARCHAR(255),
    email                  VARCHAR(255) NOT NULL,
    login_provider         VARCHAR(255) NOT NULL,
    password_hash          VARCHAR(255) NOT NULL,
    verified               BOOLEAN      NOT NULL DEFAULT false,
    profile_complete       BOOLEAN      NOT NULL DEFAULT false,
    deleted                BOOLEAN      NOT NULL DEFAULT false,
    used_free_consultation BOOLEAN      NOT NULL DEFAULT false,
    company_size           VARCHAR(255),
    work_position          VARCHAR(255),
    points                 INTEGER      NOT NULL DEFAULT 0,
    created_at             TIMESTAMP    NOT NULL,
    updated_at             TIMESTAMP    NOT NULL,
    CONSTRAINT pk_user PRIMARY KEY (id)
);
CREATE UNIQUE INDEX uq_user_email  ON _user (email);
CREATE INDEX idx_user_deleted      ON _user (deleted);
CREATE INDEX idx_user_verified     ON _user (verified);

-- =============================================================
-- AUTH
-- =============================================================

CREATE TABLE verification_token (
    token      UUID      NOT NULL DEFAULT gen_random_uuid(),
    user_id    BIGINT    NOT NULL,
    created_at TIMESTAMP NOT NULL,
    expires_at TIMESTAMP NOT NULL,
    CONSTRAINT pk_verification_token   PRIMARY KEY (token),
    CONSTRAINT uq_verification_user_id UNIQUE      (user_id),
    CONSTRAINT fk_verification_user    FOREIGN KEY (user_id) REFERENCES _user (id)
);

-- =============================================================
-- CATALOG: topic & skill
-- =============================================================

CREATE TABLE topic (
    id   BIGINT       NOT NULL DEFAULT nextval('topic_seq'),
    slug VARCHAR(255) NOT NULL,
    CONSTRAINT pk_topic      PRIMARY KEY (id),
    CONSTRAINT uq_topic_slug UNIQUE      (slug)
);

CREATE TABLE topic_translation (
    id          BIGINT       NOT NULL DEFAULT nextval('topic_translation_seq'),
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    topic_id    BIGINT       NOT NULL,
    language_id BIGINT       NOT NULL,
    CONSTRAINT pk_topic_translation PRIMARY KEY (id),
    CONSTRAINT fk_tt_topic          FOREIGN KEY (topic_id)    REFERENCES topic    (id),
    CONSTRAINT fk_tt_language       FOREIGN KEY (language_id) REFERENCES language (id)
);
CREATE INDEX idx_topic_translation_topic    ON topic_translation (topic_id);
CREATE INDEX idx_topic_translation_language ON topic_translation (language_id);

CREATE TABLE skill (
    id            BIGINT       NOT NULL DEFAULT nextval('skill_seq'),
    slug          VARCHAR(255) NOT NULL,
    show_in_radar BOOLEAN      NOT NULL,
    CONSTRAINT pk_skill      PRIMARY KEY (id),
    CONSTRAINT uq_skill_slug UNIQUE      (slug)
);

CREATE TABLE skill_translation (
    id          BIGINT       NOT NULL DEFAULT nextval('skill_translation_seq'),
    name        VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    skill_id    BIGINT       NOT NULL,
    language_id BIGINT       NOT NULL,
    CONSTRAINT pk_skill_translation PRIMARY KEY (id),
    CONSTRAINT fk_st_skill          FOREIGN KEY (skill_id)    REFERENCES skill    (id),
    CONSTRAINT fk_st_language       FOREIGN KEY (language_id) REFERENCES language (id)
);
CREATE INDEX idx_skill_translation_skill    ON skill_translation (skill_id);
CREATE INDEX idx_skill_translation_language ON skill_translation (language_id);

-- =============================================================
-- CATALOG: course hierarchy
-- =============================================================

CREATE TABLE course (
    id               BIGINT       NOT NULL DEFAULT nextval('course_seq'),
    image_url        TEXT         NOT NULL,
    color            VARCHAR(255),
    difficulty       VARCHAR(255) NOT NULL,
    duration_minutes INTEGER      NOT NULL,
    CONSTRAINT pk_course PRIMARY KEY (id)
);
CREATE INDEX idx_course_difficulty ON course (difficulty);

CREATE TABLE course_version (
    id             BIGINT  NOT NULL DEFAULT nextval('course_version_seq'),
    version_number INTEGER NOT NULL,
    creation_date  DATE    NOT NULL,
    active         BOOLEAN NOT NULL,
    course_id      BIGINT  NOT NULL,
    CONSTRAINT pk_course_version PRIMARY KEY (id),
    CONSTRAINT fk_cv_course      FOREIGN KEY (course_id) REFERENCES course (id)
);
CREATE INDEX idx_course_version_course ON course_version (course_id);
CREATE INDEX idx_course_version_active ON course_version (active);

CREATE TABLE course_module (
    id                BIGINT  NOT NULL DEFAULT nextval('course_module_seq'),
    position          INTEGER NOT NULL,
    course_version_id BIGINT  NOT NULL,
    CONSTRAINT pk_course_module     PRIMARY KEY (id),
    CONSTRAINT fk_cm_course_version FOREIGN KEY (course_version_id) REFERENCES course_version (id)
);
CREATE INDEX idx_course_module_version ON course_module (course_version_id);

CREATE TABLE course_lecture (
    id               BIGINT       NOT NULL DEFAULT nextval('course_lecture_seq'),
    duration_minutes INTEGER      NOT NULL,
    position         INTEGER      NOT NULL,
    content_type     VARCHAR(255) NOT NULL,
    course_module_id BIGINT       NOT NULL,
    CONSTRAINT pk_course_lecture   PRIMARY KEY (id),
    CONSTRAINT fk_cl_course_module FOREIGN KEY (course_module_id) REFERENCES course_module (id)
);
CREATE INDEX idx_course_lecture_module ON course_lecture (course_module_id);

CREATE TABLE course_price (
    id                  BIGINT    NOT NULL DEFAULT nextval('course_price_seq'),
    amount              DECIMAL   NOT NULL,
    active              BOOLEAN   NOT NULL,
    discounted          BOOLEAN   NOT NULL,
    discount_amount     DECIMAL   NOT NULL,
    discount_percentage DECIMAL   NOT NULL,
    created_at          TIMESTAMP NOT NULL,
    course_id           BIGINT    NOT NULL,
    CONSTRAINT pk_course_price PRIMARY KEY (id),
    CONSTRAINT fk_cp_course    FOREIGN KEY (course_id) REFERENCES course (id)
);
CREATE INDEX idx_course_price_course ON course_price (course_id);

CREATE TABLE course_translation (
    id                BIGINT       NOT NULL DEFAULT nextval('course_translation_seq'),
    title_short       VARCHAR(255) NOT NULL,
    title             VARCHAR(255) NOT NULL,
    description_short VARCHAR(255) NOT NULL,
    description       TEXT         NOT NULL,
    description_long  TEXT         NOT NULL,
    created_at        TIMESTAMP    NOT NULL,
    language_id       BIGINT       NOT NULL,
    course_id         BIGINT       NOT NULL,
    CONSTRAINT pk_course_translation PRIMARY KEY (id),
    CONSTRAINT fk_ct_language        FOREIGN KEY (language_id) REFERENCES language (id),
    CONSTRAINT fk_ct_course          FOREIGN KEY (course_id)   REFERENCES course   (id)
);
CREATE INDEX idx_course_translation_course   ON course_translation (course_id);
CREATE INDEX idx_course_translation_language ON course_translation (language_id);

-- @ElementCollection: CourseTranslation.whatWillBeLearned
CREATE TABLE course_translation_what_will_be_learned (
    course_translation_id BIGINT       NOT NULL,
    what_will_be_learned  VARCHAR(255),
    CONSTRAINT fk_ctwwbl_translation FOREIGN KEY (course_translation_id)
        REFERENCES course_translation (id)
);
CREATE INDEX idx_ctwwbl_translation ON course_translation_what_will_be_learned (course_translation_id);

CREATE TABLE course_module_translation (
    id               BIGINT       NOT NULL DEFAULT nextval('course_module_translation_seq'),
    title            VARCHAR(255),
    created_at       TIMESTAMP    NOT NULL,
    language_id      BIGINT       NOT NULL,
    course_module_id BIGINT       NOT NULL,
    CONSTRAINT pk_course_module_translation PRIMARY KEY (id),
    CONSTRAINT fk_cmt_language              FOREIGN KEY (language_id)      REFERENCES language      (id),
    CONSTRAINT fk_cmt_module                FOREIGN KEY (course_module_id) REFERENCES course_module (id)
);
CREATE INDEX idx_cmt_module   ON course_module_translation (course_module_id);
CREATE INDEX idx_cmt_language ON course_module_translation (language_id);

CREATE TABLE course_lecture_translation (
    id                BIGINT       NOT NULL DEFAULT nextval('course_lecture_translation_seq'),
    content_file_name TEXT,
    title             VARCHAR(255) NOT NULL,
    description       TEXT         NOT NULL,
    content_text      TEXT,
    created_at        TIMESTAMP    NOT NULL,
    language_id       BIGINT       NOT NULL,
    course_lecture_id BIGINT       NOT NULL,
    CONSTRAINT pk_course_lecture_translation PRIMARY KEY (id),
    CONSTRAINT fk_clt_language               FOREIGN KEY (language_id)       REFERENCES language       (id),
    CONSTRAINT fk_clt_lecture                FOREIGN KEY (course_lecture_id) REFERENCES course_lecture (id)
);
CREATE INDEX idx_clt_lecture  ON course_lecture_translation (course_lecture_id);
CREATE INDEX idx_clt_language ON course_lecture_translation (language_id);

CREATE TABLE related_course (
    id                BIGINT    NOT NULL DEFAULT nextval('related_course_seq'),
    similarity_score  DECIMAL,
    calculated_at     TIMESTAMP,
    course_id         BIGINT    NOT NULL,
    related_course_id BIGINT    NOT NULL,
    CONSTRAINT pk_related_course        PRIMARY KEY (id),
    CONSTRAINT fk_rc_course             FOREIGN KEY (course_id)         REFERENCES course (id),
    CONSTRAINT fk_rc_related_course     FOREIGN KEY (related_course_id) REFERENCES course (id)
);
CREATE INDEX idx_related_course_course  ON related_course (course_id);
CREATE INDEX idx_related_course_related ON related_course (related_course_id);

CREATE TABLE course_activity_event (
    id         BIGINT       NOT NULL DEFAULT nextval('course_activity_event_seq'),
    event_type VARCHAR(255) NOT NULL,
    timestamp  TIMESTAMP    NOT NULL,
    course_id  BIGINT       NOT NULL,
    user_id    BIGINT       NOT NULL,
    CONSTRAINT pk_course_activity_event PRIMARY KEY (id),
    CONSTRAINT fk_cae_course            FOREIGN KEY (course_id) REFERENCES course (id),
    CONSTRAINT fk_cae_user              FOREIGN KEY (user_id)   REFERENCES _user  (id)
);
CREATE INDEX idx_cae_course      ON course_activity_event (course_id);
CREATE INDEX idx_cae_user        ON course_activity_event (user_id);
CREATE INDEX idx_cae_event_type  ON course_activity_event (event_type);
CREATE INDEX idx_cae_timestamp   ON course_activity_event (timestamp);

-- =============================================================
-- CATALOG: join tables
-- =============================================================

CREATE TABLE course_topic (
    course_id BIGINT NOT NULL,
    topic_id  BIGINT NOT NULL,
    CONSTRAINT pk_course_topic PRIMARY KEY (course_id, topic_id),
    CONSTRAINT fk_ct2_course   FOREIGN KEY (course_id) REFERENCES course (id),
    CONSTRAINT fk_ct2_topic    FOREIGN KEY (topic_id)  REFERENCES topic  (id)
);
CREATE INDEX idx_course_topic_topic ON course_topic (topic_id);

CREATE TABLE course_skill (
    course_id BIGINT NOT NULL,
    skill_id  BIGINT NOT NULL,
    CONSTRAINT pk_course_skill PRIMARY KEY (course_id, skill_id),
    CONSTRAINT fk_cs_course    FOREIGN KEY (course_id) REFERENCES course (id),
    CONSTRAINT fk_cs_skill     FOREIGN KEY (skill_id)  REFERENCES skill  (id)
);
CREATE INDEX idx_course_skill_skill ON course_skill (skill_id);

CREATE TABLE expert_course (
    expert_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    CONSTRAINT pk_expert_course PRIMARY KEY (expert_id, course_id),
    CONSTRAINT fk_ec_expert     FOREIGN KEY (expert_id) REFERENCES expert (id),
    CONSTRAINT fk_ec_course     FOREIGN KEY (course_id) REFERENCES course (id)
);
CREATE INDEX idx_expert_course_course ON expert_course (course_id);

CREATE TABLE user_favorite_course (
    user_id   BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    CONSTRAINT pk_user_favorite_course PRIMARY KEY (user_id, course_id),
    CONSTRAINT fk_ufc_user             FOREIGN KEY (user_id)   REFERENCES _user  (id),
    CONSTRAINT fk_ufc_course           FOREIGN KEY (course_id) REFERENCES course (id)
);
CREATE INDEX idx_ufc_course ON user_favorite_course (course_id);

CREATE TABLE user_topic (
    user_id  BIGINT NOT NULL,
    topic_id BIGINT NOT NULL,
    CONSTRAINT pk_user_topic PRIMARY KEY (user_id, topic_id),
    CONSTRAINT fk_ut_user    FOREIGN KEY (user_id)  REFERENCES _user (id),
    CONSTRAINT fk_ut_topic   FOREIGN KEY (topic_id) REFERENCES topic (id)
);
CREATE INDEX idx_user_topic_topic ON user_topic (topic_id);

-- =============================================================
-- ASSESSMENT
-- =============================================================

CREATE TABLE quiz (
    id                BIGINT       NOT NULL DEFAULT nextval('quiz_seq'),
    type              VARCHAR(255) NOT NULL,
    passing_score     INTEGER      NOT NULL,
    randomized        BOOLEAN      NOT NULL DEFAULT false,
    active            BOOLEAN      NOT NULL DEFAULT true,
    created_at        TIMESTAMP    NOT NULL,
    course_module_id  BIGINT,
    course_version_id BIGINT,
    CONSTRAINT pk_quiz                PRIMARY KEY (id),
    CONSTRAINT uq_quiz_course_module  UNIQUE      (course_module_id),
    CONSTRAINT uq_quiz_course_version UNIQUE      (course_version_id),
    CONSTRAINT fk_quiz_course_module  FOREIGN KEY (course_module_id)  REFERENCES course_module  (id),
    CONSTRAINT fk_quiz_course_version FOREIGN KEY (course_version_id) REFERENCES course_version (id)
);

CREATE TABLE quiz_translation (
    id          BIGINT       NOT NULL DEFAULT nextval('quiz_translation_seq'),
    title       VARCHAR(255) NOT NULL,
    description TEXT         NOT NULL,
    created_at  TIMESTAMP,
    language_id BIGINT       NOT NULL,
    quiz_id     BIGINT       NOT NULL,
    CONSTRAINT pk_quiz_translation PRIMARY KEY (id),
    CONSTRAINT fk_qtr_language     FOREIGN KEY (language_id) REFERENCES language (id),
    CONSTRAINT fk_qtr_quiz         FOREIGN KEY (quiz_id)     REFERENCES quiz     (id)
);
CREATE INDEX idx_quiz_translation_quiz     ON quiz_translation (quiz_id);
CREATE INDEX idx_quiz_translation_language ON quiz_translation (language_id);

CREATE TABLE quiz_question (
    id       BIGINT       NOT NULL DEFAULT nextval('quiz_question_seq'),
    position INTEGER      NOT NULL,
    points   INTEGER      NOT NULL,
    type     VARCHAR(255) NOT NULL,
    quiz_id  BIGINT       NOT NULL,
    CONSTRAINT pk_quiz_question PRIMARY KEY (id),
    CONSTRAINT fk_qq_quiz       FOREIGN KEY (quiz_id) REFERENCES quiz (id)
);
CREATE INDEX idx_quiz_question_quiz ON quiz_question (quiz_id);

CREATE TABLE quiz_question_translation (
    id               BIGINT    NOT NULL DEFAULT nextval('quiz_question_translation_seq'),
    question_text    TEXT      NOT NULL,
    scenario         TEXT,
    created_at       TIMESTAMP NOT NULL,
    language_id      BIGINT    NOT NULL,
    quiz_question_id BIGINT    NOT NULL,
    CONSTRAINT pk_quiz_question_translation PRIMARY KEY (id),
    CONSTRAINT fk_qqt_language              FOREIGN KEY (language_id)      REFERENCES language      (id),
    CONSTRAINT fk_qqt_question              FOREIGN KEY (quiz_question_id) REFERENCES quiz_question (id)
);
CREATE INDEX idx_qqt_question ON quiz_question_translation (quiz_question_id);
CREATE INDEX idx_qqt_language ON quiz_question_translation (language_id);

CREATE TABLE quiz_answer_option (
    id               BIGINT  NOT NULL DEFAULT nextval('quiz_answer_option_seq'),
    correct          BOOLEAN NOT NULL,
    quiz_question_id BIGINT  NOT NULL,
    CONSTRAINT pk_quiz_answer_option PRIMARY KEY (id),
    CONSTRAINT fk_qao_question       FOREIGN KEY (quiz_question_id) REFERENCES quiz_question (id)
);
CREATE INDEX idx_quiz_answer_option_question ON quiz_answer_option (quiz_question_id);

CREATE TABLE quiz_answer_option_translation (
    id                    BIGINT       NOT NULL DEFAULT nextval('quiz_answer_option_translation_seq'),
    answer_text           TEXT         NOT NULL,
    explanation           VARCHAR(255) NOT NULL,
    created_at            TIMESTAMP    NOT NULL,
    language_id           BIGINT       NOT NULL,
    quiz_answer_option_id BIGINT       NOT NULL,
    CONSTRAINT pk_quiz_answer_option_translation PRIMARY KEY (id),
    CONSTRAINT fk_qaot_language                  FOREIGN KEY (language_id)           REFERENCES language           (id),
    CONSTRAINT fk_qaot_option                    FOREIGN KEY (quiz_answer_option_id) REFERENCES quiz_answer_option (id)
);
CREATE INDEX idx_qaot_option   ON quiz_answer_option_translation (quiz_answer_option_id);
CREATE INDEX idx_qaot_language ON quiz_answer_option_translation (language_id);

CREATE TABLE quiz_question_skill (
    id               BIGINT       NOT NULL DEFAULT nextval('quiz_question_skill_seq'),
    proficiency      VARCHAR(255),
    weight           INTEGER,
    quiz_question_id BIGINT       NOT NULL,
    skill_id         BIGINT       NOT NULL,
    CONSTRAINT pk_quiz_question_skill PRIMARY KEY (id),
    CONSTRAINT fk_qqs_question        FOREIGN KEY (quiz_question_id) REFERENCES quiz_question (id),
    CONSTRAINT fk_qqs_skill           FOREIGN KEY (skill_id)         REFERENCES skill         (id)
);
CREATE INDEX idx_qqs_question ON quiz_question_skill (quiz_question_id);
CREATE INDEX idx_qqs_skill    ON quiz_question_skill (skill_id);

-- =============================================================
-- LEARNING
-- =============================================================

CREATE TABLE enrollment (
    id                BIGINT       NOT NULL DEFAULT nextval('enrollment_seq'),
    enrollment_status VARCHAR(255) NOT NULL,
    enrollment_type   VARCHAR(255) NOT NULL,
    on_trial          BOOLEAN      NOT NULL,
    enrollment_date   DATE         NOT NULL,
    purchase_date     DATE,
    activation_date   DATE,
    completion_date   DATE,
    updated_at        TIMESTAMP    NOT NULL,
    course_version_id BIGINT       NOT NULL,
    user_id           BIGINT       NOT NULL,
    CONSTRAINT pk_enrollment         PRIMARY KEY (id),
    CONSTRAINT fk_enr_course_version FOREIGN KEY (course_version_id) REFERENCES course_version (id),
    CONSTRAINT fk_enr_user           FOREIGN KEY (user_id)           REFERENCES _user          (id)
);
CREATE INDEX idx_enrollment_user           ON enrollment (user_id);
CREATE INDEX idx_enrollment_course_version ON enrollment (course_version_id);
CREATE INDEX idx_enrollment_status         ON enrollment (enrollment_status);

CREATE TABLE lecture_progress (
    id                BIGINT    NOT NULL DEFAULT nextval('lecture_progress_seq'),
    completed         BOOLEAN   NOT NULL,
    completed_at      TIMESTAMP NOT NULL,
    enrollment_id     BIGINT    NOT NULL,
    course_lecture_id BIGINT    NOT NULL,
    CONSTRAINT pk_lecture_progress PRIMARY KEY (id),
    CONSTRAINT fk_lp_enrollment    FOREIGN KEY (enrollment_id)     REFERENCES enrollment    (id),
    CONSTRAINT fk_lp_lecture       FOREIGN KEY (course_lecture_id) REFERENCES course_lecture (id)
);
CREATE INDEX idx_lecture_progress_enrollment ON lecture_progress (enrollment_id);
CREATE INDEX idx_lecture_progress_lecture    ON lecture_progress (course_lecture_id);

CREATE TABLE quiz_attempt (
    id             BIGINT       NOT NULL DEFAULT nextval('quiz_attempt_seq'),
    attempt_number INTEGER      NOT NULL,
    started_at     TIMESTAMP    NOT NULL,
    completed_at   TIMESTAMP    NOT NULL,
    status         VARCHAR(255) NOT NULL,
    score          INTEGER      NOT NULL,
    total_points   INTEGER      NOT NULL,
    earned_points  INTEGER      NOT NULL,
    passed         BOOLEAN      NOT NULL,
    quiz_id        BIGINT       NOT NULL,
    enrollment_id  BIGINT       NOT NULL,
    CONSTRAINT pk_quiz_attempt  PRIMARY KEY (id),
    CONSTRAINT fk_qa_quiz       FOREIGN KEY (quiz_id)       REFERENCES quiz       (id),
    CONSTRAINT fk_qa_enrollment FOREIGN KEY (enrollment_id) REFERENCES enrollment (id)
);
CREATE INDEX idx_quiz_attempt_quiz       ON quiz_attempt (quiz_id);
CREATE INDEX idx_quiz_attempt_enrollment ON quiz_attempt (enrollment_id);
CREATE INDEX idx_quiz_attempt_status     ON quiz_attempt (status);

CREATE TABLE quiz_attempt_answer (
    id               BIGINT  NOT NULL DEFAULT nextval('quiz_attempt_answer_seq'),
    correct          BOOLEAN NOT NULL,
    quiz_question_id BIGINT  NOT NULL,
    quiz_attempt_id  BIGINT  NOT NULL,
    CONSTRAINT pk_quiz_attempt_answer PRIMARY KEY (id),
    CONSTRAINT fk_qaa_question        FOREIGN KEY (quiz_question_id) REFERENCES quiz_question (id),
    CONSTRAINT fk_qaa_attempt         FOREIGN KEY (quiz_attempt_id)  REFERENCES quiz_attempt  (id)
);
CREATE INDEX idx_qaa_attempt  ON quiz_attempt_answer (quiz_attempt_id);
CREATE INDEX idx_qaa_question ON quiz_attempt_answer (quiz_question_id);

CREATE TABLE quiz_attempt_answer_selected_options (
    quiz_attempt_answer_id BIGINT NOT NULL,
    quiz_answer_option_id  BIGINT NOT NULL,
    CONSTRAINT fk_qaaso_answer FOREIGN KEY (quiz_attempt_answer_id) REFERENCES quiz_attempt_answer (id),
    CONSTRAINT fk_qaaso_option FOREIGN KEY (quiz_answer_option_id)  REFERENCES quiz_answer_option  (id)
);
CREATE INDEX idx_qaaso_answer ON quiz_attempt_answer_selected_options (quiz_attempt_answer_id);
CREATE INDEX idx_qaaso_option ON quiz_attempt_answer_selected_options (quiz_answer_option_id);

CREATE TABLE user_skill (
    id                BIGINT       NOT NULL DEFAULT nextval('user_skill_seq'),
    verified          BOOLEAN      NOT NULL,
    proficiency       VARCHAR(255) NOT NULL,
    proficiency_score INTEGER      NOT NULL DEFAULT 0,
    created_at        TIMESTAMP    NOT NULL,
    updated_at        TIMESTAMP    NOT NULL,
    enrollment_id     BIGINT       NOT NULL,
    skill_id          BIGINT       NOT NULL,
    user_id           BIGINT       NOT NULL,
    CONSTRAINT pk_user_skill    PRIMARY KEY (id),
    CONSTRAINT fk_us_enrollment FOREIGN KEY (enrollment_id) REFERENCES enrollment (id),
    CONSTRAINT fk_us_skill      FOREIGN KEY (skill_id)      REFERENCES skill      (id),
    CONSTRAINT fk_us_user       FOREIGN KEY (user_id)       REFERENCES _user      (id)
);
CREATE INDEX idx_user_skill_user       ON user_skill (user_id);
CREATE INDEX idx_user_skill_skill      ON user_skill (skill_id);
CREATE INDEX idx_user_skill_enrollment ON user_skill (enrollment_id);

CREATE TABLE user_skill_snapshot (
    id                        BIGINT       NOT NULL DEFAULT nextval('user_skill_snapshot_seq'),
    proficiency_at_time       VARCHAR(255) NOT NULL,
    proficiency_score_at_time INTEGER      NOT NULL DEFAULT 0,
    new_proficiency           VARCHAR(255) NOT NULL,
    new_proficiency_score     INTEGER      NOT NULL DEFAULT 0,
    created_at                TIMESTAMP    NOT NULL,
    quiz_attempt_id           BIGINT       NOT NULL,
    enrollment_id             BIGINT       NOT NULL,
    user_skill_id             BIGINT       NOT NULL,
    CONSTRAINT pk_user_skill_snapshot PRIMARY KEY (id),
    CONSTRAINT fk_uss_quiz_attempt    FOREIGN KEY (quiz_attempt_id) REFERENCES quiz_attempt (id),
    CONSTRAINT fk_uss_enrollment      FOREIGN KEY (enrollment_id)   REFERENCES enrollment   (id),
    CONSTRAINT fk_uss_user_skill      FOREIGN KEY (user_skill_id)   REFERENCES user_skill   (id)
);
CREATE INDEX idx_uss_user_skill   ON user_skill_snapshot (user_skill_id);
CREATE INDEX idx_uss_enrollment   ON user_skill_snapshot (enrollment_id);
CREATE INDEX idx_uss_quiz_attempt ON user_skill_snapshot (quiz_attempt_id);

CREATE TABLE certificate (
    id                 BIGINT       NOT NULL DEFAULT nextval('certificate_seq'),
    issue_date         DATE         NOT NULL,
    certificate_url    VARCHAR(255) NOT NULL,
    certificate_number VARCHAR(255) NOT NULL,
    user_id            BIGINT       NOT NULL,
    enrollment_id      BIGINT       NOT NULL,
    CONSTRAINT pk_certificate            PRIMARY KEY (id),
    CONSTRAINT uq_certificate_enrollment UNIQUE      (enrollment_id),
    CONSTRAINT uq_certificate_number     UNIQUE      (certificate_number),
    CONSTRAINT fk_cert_user              FOREIGN KEY (user_id)       REFERENCES _user      (id),
    CONSTRAINT fk_cert_enrollment        FOREIGN KEY (enrollment_id) REFERENCES enrollment (id)
);
CREATE INDEX idx_certificate_user ON certificate (user_id);

CREATE TABLE review (
    id            BIGINT       NOT NULL DEFAULT nextval('review_seq'),
    rating        INTEGER      NOT NULL,
    comment       VARCHAR(255),
    date          DATE         NOT NULL,
    enrollment_id BIGINT,
    CONSTRAINT pk_review            PRIMARY KEY (id),
    CONSTRAINT uq_review_enrollment UNIQUE      (enrollment_id),
    CONSTRAINT fk_review_enrollment FOREIGN KEY (enrollment_id) REFERENCES enrollment (id)
);

-- =============================================================
-- COLLECTION: learning path
-- =============================================================

CREATE TABLE learning_path (
    id                       BIGINT       NOT NULL DEFAULT nextval('learning_path_seq'),
    type                     VARCHAR(255) NOT NULL,
    slug                     VARCHAR(255) NOT NULL,
    image_url                VARCHAR(255) NOT NULL,
    base_price               DECIMAL      NOT NULL,
    discounted               BOOLEAN      NOT NULL,
    discount_amount          DECIMAL      NOT NULL,
    discount_percentage      DECIMAL      NOT NULL,
    estimated_duration_hours INTEGER      NOT NULL,
    difficulty               VARCHAR(255) NOT NULL,
    active                   BOOLEAN      NOT NULL DEFAULT true,
    created_at               TIMESTAMP    NOT NULL,
    deactivated_at           TIMESTAMP,
    CONSTRAINT pk_learning_path PRIMARY KEY (id)
);
CREATE INDEX idx_learning_path_active ON learning_path (active);
CREATE INDEX idx_learning_path_type   ON learning_path (type);

CREATE TABLE learning_path_translation (
    id               BIGINT       NOT NULL DEFAULT nextval('learning_path_translation_seq'),
    title            VARCHAR(255) NOT NULL,
    description      VARCHAR(255) NOT NULL,
    created_at       TIMESTAMP    NOT NULL,
    language_id      BIGINT       NOT NULL,
    learning_path_id BIGINT       NOT NULL,
    CONSTRAINT pk_learning_path_translation PRIMARY KEY (id),
    CONSTRAINT fk_lpt_language              FOREIGN KEY (language_id)      REFERENCES language      (id),
    CONSTRAINT fk_lpt_learning_path         FOREIGN KEY (learning_path_id) REFERENCES learning_path (id)
);
CREATE INDEX idx_lpt_learning_path ON learning_path_translation (learning_path_id);
CREATE INDEX idx_lpt_language      ON learning_path_translation (language_id);

-- @ElementCollection: LearningPathTranslation.learningOutcomes
CREATE TABLE learning_path_translation_learning_outcomes (
    learning_path_translation_id BIGINT       NOT NULL,
    learning_outcomes            VARCHAR(255),
    CONSTRAINT fk_lptlo_translation FOREIGN KEY (learning_path_translation_id)
        REFERENCES learning_path_translation (id)
);
CREATE INDEX idx_lptlo_translation ON learning_path_translation_learning_outcomes (learning_path_translation_id);

CREATE TABLE curated_learning_path (
    id               BIGINT NOT NULL DEFAULT nextval('curated_learning_path_seq'),
    learning_path_id BIGINT NOT NULL,
    CONSTRAINT pk_curated_learning_path PRIMARY KEY (id),
    CONSTRAINT uq_clp_learning_path     UNIQUE      (learning_path_id),
    CONSTRAINT fk_clp_learning_path     FOREIGN KEY (learning_path_id) REFERENCES learning_path (id)
);

CREATE TABLE personalized_learning_path (
    id                      BIGINT       NOT NULL DEFAULT nextval('personalized_learning_path_seq'),
    type                    VARCHAR(255) NOT NULL,
    generated_reason        VARCHAR(255) NOT NULL,
    discounted              BOOLEAN      NOT NULL,
    added_discount_percent  DECIMAL      NOT NULL DEFAULT 0,
    added_discount_amount   DECIMAL      NOT NULL DEFAULT 0,
    active                  BOOLEAN      NOT NULL DEFAULT true,
    created_at              TIMESTAMP    NOT NULL,
    expires_at              TIMESTAMP    NOT NULL,
    learning_path_id        BIGINT       NOT NULL,
    source_learning_path_id BIGINT,
    user_id                 BIGINT       NOT NULL,
    CONSTRAINT pk_personalized_learning_path PRIMARY KEY (id),
    CONSTRAINT fk_plp_learning_path          FOREIGN KEY (learning_path_id)        REFERENCES learning_path         (id),
    CONSTRAINT fk_plp_source                 FOREIGN KEY (source_learning_path_id) REFERENCES curated_learning_path (id),
    CONSTRAINT fk_plp_user                   FOREIGN KEY (user_id)                 REFERENCES _user                 (id)
);
CREATE INDEX idx_plp_user          ON personalized_learning_path (user_id);
CREATE INDEX idx_plp_learning_path ON personalized_learning_path (learning_path_id);

CREATE TABLE user_learning_path (
    id                  BIGINT       NOT NULL DEFAULT nextval('user_learning_path_seq'),
    acquired_date       DATE         NOT NULL,
    completed_date      DATE,
    status              VARCHAR(255) NOT NULL DEFAULT 'NOT_STARTED',
    progress_percentage INTEGER      NOT NULL DEFAULT 0,
    updated_at          TIMESTAMP    NOT NULL,
    learning_path_id    BIGINT       NOT NULL,
    user_id             BIGINT       NOT NULL,
    CONSTRAINT pk_user_learning_path PRIMARY KEY (id),
    CONSTRAINT fk_ulp_learning_path  FOREIGN KEY (learning_path_id) REFERENCES learning_path (id),
    CONSTRAINT fk_ulp_user           FOREIGN KEY (user_id)          REFERENCES _user         (id)
);
CREATE INDEX idx_ulp_user          ON user_learning_path (user_id);
CREATE INDEX idx_ulp_learning_path ON user_learning_path (learning_path_id);

CREATE TABLE learning_path_course (
    id               BIGINT  NOT NULL DEFAULT nextval('learning_path_course_seq'),
    sequence_order   INTEGER NOT NULL,
    learning_path_id BIGINT  NOT NULL,
    course_id        BIGINT  NOT NULL,
    CONSTRAINT pk_learning_path_course PRIMARY KEY (id),
    CONSTRAINT fk_lpc_learning_path    FOREIGN KEY (learning_path_id) REFERENCES learning_path (id),
    CONSTRAINT fk_lpc_course           FOREIGN KEY (course_id)        REFERENCES course        (id)
);
CREATE INDEX idx_lpc_learning_path ON learning_path_course (learning_path_id);
CREATE INDEX idx_lpc_course        ON learning_path_course (course_id);

CREATE TABLE expert_curated_learning_path (
    expert_id                BIGINT NOT NULL,
    curated_learning_path_id BIGINT NOT NULL,
    CONSTRAINT pk_expert_curated_lp PRIMARY KEY (expert_id, curated_learning_path_id),
    CONSTRAINT fk_eclp_expert        FOREIGN KEY (expert_id)                REFERENCES expert                (id),
    CONSTRAINT fk_eclp_clp           FOREIGN KEY (curated_learning_path_id) REFERENCES curated_learning_path (id)
);
CREATE INDEX idx_eclp_clp ON expert_curated_learning_path (curated_learning_path_id);

-- =============================================================
-- COLLECTION: bundle
-- =============================================================

CREATE TABLE bundle (
    id                  BIGINT       NOT NULL DEFAULT nextval('bundle_seq'),
    type                VARCHAR(255) NOT NULL,
    slug                VARCHAR(255) NOT NULL,
    image_url           VARCHAR(255) NOT NULL,
    base_price          DECIMAL      NOT NULL,
    discount_amount     DECIMAL      NOT NULL,
    discount_percentage DECIMAL      NOT NULL,
    active              BOOLEAN      NOT NULL DEFAULT true,
    created_at          TIMESTAMP    NOT NULL,
    updated_at          TIMESTAMP    NOT NULL,
    deactivated_at      TIMESTAMP,
    CONSTRAINT pk_bundle PRIMARY KEY (id)
);
CREATE INDEX idx_bundle_active ON bundle (active);
CREATE INDEX idx_bundle_type   ON bundle (type);

CREATE TABLE bundle_translation (
    id          BIGINT       NOT NULL DEFAULT nextval('bundle_translation_seq'),
    title       VARCHAR(255) NOT NULL,
    description VARCHAR(255) NOT NULL,
    created_at  TIMESTAMP    NOT NULL,
    language_id BIGINT       NOT NULL,
    bundle_id   BIGINT       NOT NULL,
    CONSTRAINT pk_bundle_translation PRIMARY KEY (id),
    CONSTRAINT fk_bt_language        FOREIGN KEY (language_id) REFERENCES language (id),
    CONSTRAINT fk_bt_bundle          FOREIGN KEY (bundle_id)   REFERENCES bundle   (id)
);
CREATE INDEX idx_bundle_translation_bundle   ON bundle_translation (bundle_id);
CREATE INDEX idx_bundle_translation_language ON bundle_translation (language_id);

CREATE TABLE curated_bundle (
    id        BIGINT NOT NULL DEFAULT nextval('curated_bundle_seq'),
    bundle_id BIGINT NOT NULL,
    CONSTRAINT pk_curated_bundle    PRIMARY KEY (id),
    CONSTRAINT uq_curated_bundle_id UNIQUE      (bundle_id),
    CONSTRAINT fk_cb_bundle         FOREIGN KEY (bundle_id) REFERENCES bundle (id)
);

CREATE TABLE personalized_bundle (
    id                     BIGINT       NOT NULL DEFAULT nextval('personalized_bundle_seq'),
    type                   VARCHAR(255) NOT NULL,
    generated_reason       VARCHAR(255) NOT NULL,
    discounted             BOOLEAN      NOT NULL,
    added_discount_percent DECIMAL      NOT NULL DEFAULT 0,
    added_discount_amount  DECIMAL      NOT NULL DEFAULT 0,
    active                 BOOLEAN      NOT NULL DEFAULT true,
    created_at             TIMESTAMP    NOT NULL,
    expires_at             TIMESTAMP    NOT NULL,
    bundle_id              BIGINT       NOT NULL,
    source_bundle_id       BIGINT,
    user_id                BIGINT       NOT NULL,
    CONSTRAINT pk_personalized_bundle PRIMARY KEY (id),
    CONSTRAINT fk_pb_bundle           FOREIGN KEY (bundle_id)        REFERENCES bundle         (id),
    CONSTRAINT fk_pb_source           FOREIGN KEY (source_bundle_id) REFERENCES curated_bundle (id),
    CONSTRAINT fk_pb_user             FOREIGN KEY (user_id)          REFERENCES _user          (id)
);
CREATE INDEX idx_pb_user   ON personalized_bundle (user_id);
CREATE INDEX idx_pb_bundle ON personalized_bundle (bundle_id);

CREATE TABLE user_bundle (
    id            BIGINT NOT NULL DEFAULT nextval('user_bundle_seq'),
    acquired_date DATE   NOT NULL,
    bundle_id     BIGINT NOT NULL,
    user_id       BIGINT NOT NULL,
    CONSTRAINT pk_user_bundle PRIMARY KEY (id),
    CONSTRAINT fk_ub_bundle   FOREIGN KEY (bundle_id) REFERENCES bundle (id),
    CONSTRAINT fk_ub_user     FOREIGN KEY (user_id)   REFERENCES _user  (id)
);
CREATE INDEX idx_user_bundle_user   ON user_bundle (user_id);
CREATE INDEX idx_user_bundle_bundle ON user_bundle (bundle_id);

CREATE TABLE bundle_course (
    bundle_id BIGINT NOT NULL,
    course_id BIGINT NOT NULL,
    CONSTRAINT pk_bundle_course PRIMARY KEY (bundle_id, course_id),
    CONSTRAINT fk_bc_bundle     FOREIGN KEY (bundle_id) REFERENCES bundle (id),
    CONSTRAINT fk_bc_course     FOREIGN KEY (course_id) REFERENCES course (id)
);
CREATE INDEX idx_bundle_course_course ON bundle_course (course_id);

CREATE TABLE expert_curated_bundle (
    expert_id         BIGINT NOT NULL,
    curated_bundle_id BIGINT NOT NULL,
    CONSTRAINT pk_expert_curated_bundle  PRIMARY KEY (expert_id, curated_bundle_id),
    CONSTRAINT fk_ecb_expert             FOREIGN KEY (expert_id)         REFERENCES expert         (id),
    CONSTRAINT fk_ecb_curated_bundle     FOREIGN KEY (curated_bundle_id) REFERENCES curated_bundle (id)
);
CREATE INDEX idx_ecb_curated_bundle ON expert_curated_bundle (curated_bundle_id);

-- =============================================================
-- COMMERCE
-- =============================================================

CREATE TABLE _order (
    id         BIGINT       NOT NULL DEFAULT nextval('order_seq'),
    status     VARCHAR(255) NOT NULL,
    created_at TIMESTAMP    NOT NULL,
    CONSTRAINT pk_order PRIMARY KEY (id)
);
CREATE INDEX idx_order_status ON _order (status);

CREATE TABLE payment (
    id       BIGINT           NOT NULL DEFAULT nextval('payment_seq'),
    amount   DOUBLE PRECISION NOT NULL,
    date     DATE             NOT NULL,
    method   VARCHAR(255)     NOT NULL,
    status   VARCHAR(255)     NOT NULL,
    order_id BIGINT           NOT NULL,
    CONSTRAINT pk_payment       PRIMARY KEY (id),
    CONSTRAINT uq_payment_order UNIQUE      (order_id),
    CONSTRAINT fk_pay_order     FOREIGN KEY (order_id) REFERENCES _order (id)
);
CREATE INDEX idx_payment_status ON payment (status);

CREATE TABLE order_details (
    id                  BIGINT    NOT NULL DEFAULT nextval('order_details_seq'),
    price               DECIMAL   NOT NULL,
    discount_amount     DECIMAL   NOT NULL,
    discount_percentage DECIMAL   NOT NULL,
    created_at          TIMESTAMP NOT NULL,
    order_id            BIGINT    NOT NULL,
    enrollment_id       BIGINT    NOT NULL,
    course_id           BIGINT    NOT NULL,
    CONSTRAINT pk_order_details        PRIMARY KEY (id),
    CONSTRAINT uq_order_details_enroll UNIQUE      (enrollment_id),
    CONSTRAINT fk_od_order             FOREIGN KEY (order_id)      REFERENCES _order     (id),
    CONSTRAINT fk_od_enrollment        FOREIGN KEY (enrollment_id) REFERENCES enrollment (id),
    CONSTRAINT fk_od_course            FOREIGN KEY (course_id)     REFERENCES course     (id)
);
CREATE INDEX idx_order_details_order  ON order_details (order_id);
CREATE INDEX idx_order_details_course ON order_details (course_id);

-- =============================================================
-- CONSULTATION
-- =============================================================

CREATE TABLE meeting_email_reminder (
    id           BIGINT       NOT NULL DEFAULT nextval('meeting_email_reminder_seq'),
    created_at   TIMESTAMP    NOT NULL,
    updated_at   TIMESTAMP    NOT NULL,
    meeting_at   TIMESTAMP    NOT NULL,
    scheduled_at TIMESTAMP    NOT NULL,
    sent         BOOLEAN      NOT NULL,
    status       VARCHAR(255) NOT NULL DEFAULT 'PENDING',
    meeting_link TEXT         NOT NULL,
    user_id      BIGINT       NOT NULL,
    CONSTRAINT pk_meeting_email_reminder PRIMARY KEY (id),
    CONSTRAINT fk_mer_user               FOREIGN KEY (user_id) REFERENCES _user (id)
);
CREATE INDEX idx_mer_user   ON meeting_email_reminder (user_id);
CREATE INDEX idx_mer_status ON meeting_email_reminder (status);
CREATE INDEX idx_mer_sent   ON meeting_email_reminder (sent);

COMMIT;
