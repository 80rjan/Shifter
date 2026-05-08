-- Indexes for all foreign key columns
-- Columns with existing unique constraints are skipped (they already have implicit indexes):
--   certificate.enrollment_id, curated_bundle.bundle_id, curated_learning_path.learning_path_id,
--   order_details.enrollment_id, payment.order_id, quiz.course_module_id, quiz.course_version_id,
--   review.enrollment_id, verification_token.user_id

-- bundle_course
CREATE INDEX IF NOT EXISTS idx_bundle_course_bundle_id ON bundle_course(bundle_id);
CREATE INDEX IF NOT EXISTS idx_bundle_course_course_id ON bundle_course(course_id);

-- bundle_translation
CREATE INDEX IF NOT EXISTS idx_bundle_translation_bundle_id ON bundle_translation(bundle_id);
CREATE INDEX IF NOT EXISTS idx_bundle_translation_language_id ON bundle_translation(language_id);

-- certificate
CREATE INDEX IF NOT EXISTS idx_certificate_user_id ON certificate(user_id);

-- course_activity_event
CREATE INDEX IF NOT EXISTS idx_course_activity_event_course_id ON course_activity_event(course_id);
CREATE INDEX IF NOT EXISTS idx_course_activity_event_user_id ON course_activity_event(user_id);

-- course_lecture
CREATE INDEX IF NOT EXISTS idx_course_lecture_course_module_id ON course_lecture(course_module_id);

-- course_lecture_translation
CREATE INDEX IF NOT EXISTS idx_course_lecture_translation_course_lecture_id ON course_lecture_translation(course_lecture_id);
CREATE INDEX IF NOT EXISTS idx_course_lecture_translation_language_id ON course_lecture_translation(language_id);

-- course_module
CREATE INDEX IF NOT EXISTS idx_course_module_course_version_id ON course_module(course_version_id);

-- course_module_translation
CREATE INDEX IF NOT EXISTS idx_course_module_translation_course_module_id ON course_module_translation(course_module_id);
CREATE INDEX IF NOT EXISTS idx_course_module_translation_language_id ON course_module_translation(language_id);

-- course_price
CREATE INDEX IF NOT EXISTS idx_course_price_course_id ON course_price(course_id);

-- course_skill
CREATE INDEX IF NOT EXISTS idx_course_skill_course_id ON course_skill(course_id);
CREATE INDEX IF NOT EXISTS idx_course_skill_skill_id ON course_skill(skill_id);

-- course_topic
CREATE INDEX IF NOT EXISTS idx_course_topic_course_id ON course_topic(course_id);
CREATE INDEX IF NOT EXISTS idx_course_topic_topic_id ON course_topic(topic_id);

-- course_translation
CREATE INDEX IF NOT EXISTS idx_course_translation_course_id ON course_translation(course_id);
CREATE INDEX IF NOT EXISTS idx_course_translation_language_id ON course_translation(language_id);

-- course_translation_what_will_be_learned
CREATE INDEX IF NOT EXISTS idx_course_translation_wwbl_course_translation_id ON course_translation_what_will_be_learned(course_translation_id);

-- course_version
CREATE INDEX IF NOT EXISTS idx_course_version_course_id ON course_version(course_id);

-- enrollment
CREATE INDEX IF NOT EXISTS idx_enrollment_course_version_id ON enrollment(course_version_id);
CREATE INDEX IF NOT EXISTS idx_enrollment_user_id ON enrollment(user_id);

-- expert_course
CREATE INDEX IF NOT EXISTS idx_expert_course_course_id ON expert_course(course_id);
CREATE INDEX IF NOT EXISTS idx_expert_course_expert_id ON expert_course(expert_id);

-- expert_curated_bundle
CREATE INDEX IF NOT EXISTS idx_expert_curated_bundle_curated_bundle_id ON expert_curated_bundle(curated_bundle_id);
CREATE INDEX IF NOT EXISTS idx_expert_curated_bundle_expert_id ON expert_curated_bundle(expert_id);

-- expert_curated_learning_path
CREATE INDEX IF NOT EXISTS idx_expert_curated_learning_path_curated_learning_path_id ON expert_curated_learning_path(curated_learning_path_id);
CREATE INDEX IF NOT EXISTS idx_expert_curated_learning_path_expert_id ON expert_curated_learning_path(expert_id);

-- learning_path_course
CREATE INDEX IF NOT EXISTS idx_learning_path_course_course_id ON learning_path_course(course_id);
CREATE INDEX IF NOT EXISTS idx_learning_path_course_learning_path_id ON learning_path_course(learning_path_id);

-- learning_path_translation
CREATE INDEX IF NOT EXISTS idx_learning_path_translation_language_id ON learning_path_translation(language_id);
CREATE INDEX IF NOT EXISTS idx_learning_path_translation_learning_path_id ON learning_path_translation(learning_path_id);

-- learning_path_translation_learning_outcomes
CREATE INDEX IF NOT EXISTS idx_lp_translation_outcomes_lp_translation_id ON learning_path_translation_learning_outcomes(learning_path_translation_id);

-- lecture_progress
CREATE INDEX IF NOT EXISTS idx_lecture_progress_course_lecture_id ON lecture_progress(course_lecture_id);
CREATE INDEX IF NOT EXISTS idx_lecture_progress_enrollment_id ON lecture_progress(enrollment_id);

-- meeting_email_reminder
CREATE INDEX IF NOT EXISTS idx_meeting_email_reminder_user_id ON meeting_email_reminder(user_id);

-- order_details
CREATE INDEX IF NOT EXISTS idx_order_details_course_id ON order_details(course_id);
CREATE INDEX IF NOT EXISTS idx_order_details_order_id ON order_details(order_id);

-- personalized_bundle
CREATE INDEX IF NOT EXISTS idx_personalized_bundle_bundle_id ON personalized_bundle(bundle_id);
CREATE INDEX IF NOT EXISTS idx_personalized_bundle_source_bundle_id ON personalized_bundle(source_bundle_id);
CREATE INDEX IF NOT EXISTS idx_personalized_bundle_user_id ON personalized_bundle(user_id);

-- personalized_learning_path
CREATE INDEX IF NOT EXISTS idx_personalized_learning_path_learning_path_id ON personalized_learning_path(learning_path_id);
CREATE INDEX IF NOT EXISTS idx_personalized_learning_path_source_lp_id ON personalized_learning_path(source_learning_path_id);
CREATE INDEX IF NOT EXISTS idx_personalized_learning_path_user_id ON personalized_learning_path(user_id);

-- quiz_answer_option
CREATE INDEX IF NOT EXISTS idx_quiz_answer_option_quiz_question_id ON quiz_answer_option(quiz_question_id);

-- quiz_answer_option_translation
CREATE INDEX IF NOT EXISTS idx_quiz_answer_option_translation_option_id ON quiz_answer_option_translation(quiz_answer_option_id);
CREATE INDEX IF NOT EXISTS idx_quiz_answer_option_translation_language_id ON quiz_answer_option_translation(language_id);

-- quiz_attempt
CREATE INDEX IF NOT EXISTS idx_quiz_attempt_enrollment_id ON quiz_attempt(enrollment_id);
CREATE INDEX IF NOT EXISTS idx_quiz_attempt_quiz_id ON quiz_attempt(quiz_id);

-- quiz_attempt_answer
CREATE INDEX IF NOT EXISTS idx_quiz_attempt_answer_quiz_question_id ON quiz_attempt_answer(quiz_question_id);
CREATE INDEX IF NOT EXISTS idx_quiz_attempt_answer_quiz_attempt_id ON quiz_attempt_answer(quiz_attempt_id);

-- quiz_attempt_answer_selected_options
CREATE INDEX IF NOT EXISTS idx_quiz_attempt_answer_selected_opts_option_id ON quiz_attempt_answer_selected_options(quiz_answer_option_id);
CREATE INDEX IF NOT EXISTS idx_quiz_attempt_answer_selected_opts_answer_id ON quiz_attempt_answer_selected_options(quiz_attempt_answer_id);

-- quiz_question
CREATE INDEX IF NOT EXISTS idx_quiz_question_quiz_id ON quiz_question(quiz_id);

-- quiz_question_skill
CREATE INDEX IF NOT EXISTS idx_quiz_question_skill_quiz_question_id ON quiz_question_skill(quiz_question_id);
CREATE INDEX IF NOT EXISTS idx_quiz_question_skill_skill_id ON quiz_question_skill(skill_id);

-- quiz_question_translation
CREATE INDEX IF NOT EXISTS idx_quiz_question_translation_language_id ON quiz_question_translation(language_id);
CREATE INDEX IF NOT EXISTS idx_quiz_question_translation_quiz_question_id ON quiz_question_translation(quiz_question_id);

-- quiz_translation
CREATE INDEX IF NOT EXISTS idx_quiz_translation_language_id ON quiz_translation(language_id);
CREATE INDEX IF NOT EXISTS idx_quiz_translation_quiz_id ON quiz_translation(quiz_id);

-- related_course
CREATE INDEX IF NOT EXISTS idx_related_course_course_id ON related_course(course_id);
CREATE INDEX IF NOT EXISTS idx_related_course_related_course_id ON related_course(related_course_id);

-- skill_translation
CREATE INDEX IF NOT EXISTS idx_skill_translation_language_id ON skill_translation(language_id);
CREATE INDEX IF NOT EXISTS idx_skill_translation_skill_id ON skill_translation(skill_id);

-- topic_translation
CREATE INDEX IF NOT EXISTS idx_topic_translation_language_id ON topic_translation(language_id);
CREATE INDEX IF NOT EXISTS idx_topic_translation_topic_id ON topic_translation(topic_id);

-- user_bundle
CREATE INDEX IF NOT EXISTS idx_user_bundle_bundle_id ON user_bundle(bundle_id);
CREATE INDEX IF NOT EXISTS idx_user_bundle_user_id ON user_bundle(user_id);

-- user_favorite_course
CREATE INDEX IF NOT EXISTS idx_user_favorite_course_course_id ON user_favorite_course(course_id);
CREATE INDEX IF NOT EXISTS idx_user_favorite_course_user_id ON user_favorite_course(user_id);

-- user_learning_path
CREATE INDEX IF NOT EXISTS idx_user_learning_path_learning_path_id ON user_learning_path(learning_path_id);
CREATE INDEX IF NOT EXISTS idx_user_learning_path_user_id ON user_learning_path(user_id);

-- user_skill
CREATE INDEX IF NOT EXISTS idx_user_skill_enrollment_id ON user_skill(enrollment_id);
CREATE INDEX IF NOT EXISTS idx_user_skill_skill_id ON user_skill(skill_id);
CREATE INDEX IF NOT EXISTS idx_user_skill_user_id ON user_skill(user_id);

-- user_skill_snapshot
CREATE INDEX IF NOT EXISTS idx_user_skill_snapshot_enrollment_id ON user_skill_snapshot(enrollment_id);
CREATE INDEX IF NOT EXISTS idx_user_skill_snapshot_quiz_attempt_id ON user_skill_snapshot(quiz_attempt_id);
CREATE INDEX IF NOT EXISTS idx_user_skill_snapshot_user_skill_id ON user_skill_snapshot(user_skill_id);

-- user_topic
CREATE INDEX IF NOT EXISTS idx_user_topic_topic_id ON user_topic(topic_id);
CREATE INDEX IF NOT EXISTS idx_user_topic_user_id ON user_topic(user_id);
