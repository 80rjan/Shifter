-- =============================================================
-- drop.sql  –  Drop all Shifter schema objects
-- Run this before re-applying ddl.sql.
-- CASCADE handles FK dependencies automatically.
-- =============================================================

BEGIN;

-- Join / collection tables first
DROP TABLE IF EXISTS quiz_attempt_answer_selected_options  CASCADE;
DROP TABLE IF EXISTS course_translation_what_will_be_learned CASCADE;
DROP TABLE IF EXISTS learning_path_translation_learning_outcomes CASCADE;
DROP TABLE IF EXISTS expert_curated_learning_path  CASCADE;
DROP TABLE IF EXISTS expert_curated_bundle         CASCADE;
DROP TABLE IF EXISTS expert_course                 CASCADE;
DROP TABLE IF EXISTS bundle_course                 CASCADE;
DROP TABLE IF EXISTS course_skill                  CASCADE;
DROP TABLE IF EXISTS course_topic                  CASCADE;
DROP TABLE IF EXISTS user_favorite_course          CASCADE;
DROP TABLE IF EXISTS user_topic                    CASCADE;

-- Commerce
DROP TABLE IF EXISTS order_details                 CASCADE;
DROP TABLE IF EXISTS payment                       CASCADE;
DROP TABLE IF EXISTS _order                        CASCADE;

-- Consultation
DROP TABLE IF EXISTS meeting_email_reminder        CASCADE;

-- Learning
DROP TABLE IF EXISTS user_skill_snapshot           CASCADE;
DROP TABLE IF EXISTS user_skill                    CASCADE;
DROP TABLE IF EXISTS lecture_progress              CASCADE;
DROP TABLE IF EXISTS certificate                   CASCADE;
DROP TABLE IF EXISTS review                        CASCADE;

-- Assessment
DROP TABLE IF EXISTS quiz_attempt_answer           CASCADE;
DROP TABLE IF EXISTS quiz_attempt                  CASCADE;
DROP TABLE IF EXISTS quiz_question_skill           CASCADE;
DROP TABLE IF EXISTS quiz_answer_option_translation CASCADE;
DROP TABLE IF EXISTS quiz_answer_option            CASCADE;
DROP TABLE IF EXISTS quiz_question_translation     CASCADE;
DROP TABLE IF EXISTS quiz_question                 CASCADE;
DROP TABLE IF EXISTS quiz_translation              CASCADE;
DROP TABLE IF EXISTS quiz                          CASCADE;

-- Enrollment (after learning + assessment)
DROP TABLE IF EXISTS enrollment                    CASCADE;

-- Collection
DROP TABLE IF EXISTS personalized_bundle           CASCADE;
DROP TABLE IF EXISTS user_bundle                   CASCADE;
DROP TABLE IF EXISTS curated_bundle                CASCADE;
DROP TABLE IF EXISTS bundle_translation            CASCADE;
DROP TABLE IF EXISTS bundle                        CASCADE;
DROP TABLE IF EXISTS personalized_learning_path    CASCADE;
DROP TABLE IF EXISTS user_learning_path            CASCADE;
DROP TABLE IF EXISTS learning_path_course          CASCADE;
DROP TABLE IF EXISTS curated_learning_path         CASCADE;
DROP TABLE IF EXISTS learning_path_translation     CASCADE;
DROP TABLE IF EXISTS learning_path                 CASCADE;

-- Catalog
DROP TABLE IF EXISTS course_activity_event         CASCADE;
DROP TABLE IF EXISTS related_course                CASCADE;
DROP TABLE IF EXISTS course_lecture_translation    CASCADE;
DROP TABLE IF EXISTS course_lecture                CASCADE;
DROP TABLE IF EXISTS course_module_translation     CASCADE;
DROP TABLE IF EXISTS course_module                 CASCADE;
DROP TABLE IF EXISTS course_translation            CASCADE;
DROP TABLE IF EXISTS course_price                  CASCADE;
DROP TABLE IF EXISTS course_version                CASCADE;
DROP TABLE IF EXISTS course                        CASCADE;
DROP TABLE IF EXISTS skill_translation             CASCADE;
DROP TABLE IF EXISTS skill                         CASCADE;
DROP TABLE IF EXISTS topic_translation             CASCADE;
DROP TABLE IF EXISTS topic                         CASCADE;

-- Auth
DROP TABLE IF EXISTS verification_token            CASCADE;

-- Identity
DROP TABLE IF EXISTS _user                         CASCADE;
DROP TABLE IF EXISTS expert                        CASCADE;
DROP TABLE IF EXISTS admin                         CASCADE;

-- Shared
DROP TABLE IF EXISTS language                      CASCADE;

-- Sequences
DROP SEQUENCE IF EXISTS language_seq               CASCADE;
DROP SEQUENCE IF EXISTS admin_seq                  CASCADE;
DROP SEQUENCE IF EXISTS expert_seq                 CASCADE;
DROP SEQUENCE IF EXISTS user_seq                   CASCADE;
DROP SEQUENCE IF EXISTS topic_seq                  CASCADE;
DROP SEQUENCE IF EXISTS topic_translation_seq      CASCADE;
DROP SEQUENCE IF EXISTS skill_seq                  CASCADE;
DROP SEQUENCE IF EXISTS skill_translation_seq      CASCADE;
DROP SEQUENCE IF EXISTS course_seq                 CASCADE;
DROP SEQUENCE IF EXISTS course_version_seq         CASCADE;
DROP SEQUENCE IF EXISTS course_module_seq          CASCADE;
DROP SEQUENCE IF EXISTS course_lecture_seq         CASCADE;
DROP SEQUENCE IF EXISTS course_price_seq           CASCADE;
DROP SEQUENCE IF EXISTS course_translation_seq     CASCADE;
DROP SEQUENCE IF EXISTS course_module_translation_seq     CASCADE;
DROP SEQUENCE IF EXISTS course_lecture_translation_seq    CASCADE;
DROP SEQUENCE IF EXISTS related_course_seq         CASCADE;
DROP SEQUENCE IF EXISTS course_activity_event_seq  CASCADE;
DROP SEQUENCE IF EXISTS quiz_seq                   CASCADE;
DROP SEQUENCE IF EXISTS quiz_translation_seq       CASCADE;
DROP SEQUENCE IF EXISTS quiz_question_seq          CASCADE;
DROP SEQUENCE IF EXISTS quiz_question_translation_seq     CASCADE;
DROP SEQUENCE IF EXISTS quiz_answer_option_seq     CASCADE;
DROP SEQUENCE IF EXISTS quiz_answer_option_translation_seq CASCADE;
DROP SEQUENCE IF EXISTS quiz_question_skill_seq    CASCADE;
DROP SEQUENCE IF EXISTS quiz_attempt_seq           CASCADE;
DROP SEQUENCE IF EXISTS quiz_attempt_answer_seq    CASCADE;
DROP SEQUENCE IF EXISTS enrollment_seq             CASCADE;
DROP SEQUENCE IF EXISTS lecture_progress_seq       CASCADE;
DROP SEQUENCE IF EXISTS user_skill_seq             CASCADE;
DROP SEQUENCE IF EXISTS user_skill_snapshot_seq    CASCADE;
DROP SEQUENCE IF EXISTS certificate_seq            CASCADE;
DROP SEQUENCE IF EXISTS review_seq                 CASCADE;
DROP SEQUENCE IF EXISTS learning_path_seq          CASCADE;
DROP SEQUENCE IF EXISTS learning_path_translation_seq     CASCADE;
DROP SEQUENCE IF EXISTS curated_learning_path_seq  CASCADE;
DROP SEQUENCE IF EXISTS personalized_learning_path_seq    CASCADE;
DROP SEQUENCE IF EXISTS user_learning_path_seq     CASCADE;
DROP SEQUENCE IF EXISTS learning_path_course_seq   CASCADE;
DROP SEQUENCE IF EXISTS bundle_seq                 CASCADE;
DROP SEQUENCE IF EXISTS bundle_translation_seq     CASCADE;
DROP SEQUENCE IF EXISTS curated_bundle_seq         CASCADE;
DROP SEQUENCE IF EXISTS personalized_bundle_seq    CASCADE;
DROP SEQUENCE IF EXISTS user_bundle_seq            CASCADE;
DROP SEQUENCE IF EXISTS order_seq                  CASCADE;
DROP SEQUENCE IF EXISTS order_details_seq          CASCADE;
DROP SEQUENCE IF EXISTS payment_seq                CASCADE;
DROP SEQUENCE IF EXISTS meeting_email_reminder_seq CASCADE;

COMMIT;
