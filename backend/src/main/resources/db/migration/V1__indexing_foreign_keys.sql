
create index idx_bundle_translate_bundle_id on bundle_translate (bundle_id);
create index idx_bundle_translate_language_id on bundle_translate (language_id);

create index idx_certificate_enrollment_id on certificate (enrollment_id);
create index idx_certificate_user_id on certificate (user_id);

create index idx_course_skill_skill_id on course_skill (skill_id);
create index idx_course_skill_course_id on course_skill (course_id);
create index idx_course_skill_course_id_skill_id on course_skill (course_id, skill_id);

create index idx_course_topic_topic_id on course_topic (topic_id);
create index idx_course_topic_course_id on course_topic (course_id);
create index idx_course_topic_course_id_topic_id on course_topic (course_id, topic_id);

create index idx_course_activity_event_course_id on course_activity_event (course_id);
create index idx_course_activity_event_user_id on course_activity_event (user_id);

create index idx_course_lecture_course_module_id on course_lecture (course_module_id);

create index idx_course_lecture_translate_course_lecture_id on course_lecture_translate (course_lecture_id);
create index idx_course_lecture_translate_language_id on course_lecture_translate (language_id);

create index idx_course_module_course_version_id on course_module (course_version_id);

create index idx_course_module_translate_course_module_id on course_module_translate (course_module_id);
create index idx_course_module_translate_language_id on course_module_translate (language_id);

create index idx_course_price_course_id on course_price (course_id);

create index idx_course_translate_course_id on course_translate (course_id);
create index idx_course_translate_language_id on course_translate (language_id);

create index idx_course_translate_wwbl_course_translate_id on course_translate_what_will_be_learned (course_translate_id);

create index idx_course_version_course_id on course_version (course_id);

create index idx_curated_bundle_bundle_id on curated_bundle (bundle_id);
create index idx_curated_learning_path_learning_path_id on curated_learning_path (learning_path_id);

create index idx_enrollment_course_version_id on enrollment (course_version_id);
create index idx_enrollment_user_id on enrollment (user_id);

create index idx_expert_course_course_id on expert_course (course_id);
create index idx_expert_course_expert_id on expert_course (expert_id);
create index idx_expert_course_expert_id_course_id on expert_course (expert_id, course_id);

create index idx_expert_curated_bundle_curated_bundle_id on expert_curated_bundle (curated_bundle_id);
create index idx_expert_curated_bundle_expert_id on expert_curated_bundle (expert_id);
create index idx_expert_curated_bundle_expert_id_cb_id on expert_curated_bundle (expert_id, curated_bundle_id);

create index idx_expert_curated_learning_path_clp_id on expert_curated_learning_path (curated_learning_path_id);
create index idx_expert_curated_learning_path_expert_id on expert_curated_learning_path (expert_id);
create index idx_expert_curated_learning_path_expert_id_clp_id on expert_curated_learning_path (expert_id, curated_learning_path_id);

create index idx_learning_path_course_course_id on learning_path_course (course_id);
create index idx_learning_path_course_learning_path_id on learning_path_course (learning_path_id);

create index idx_learning_path_translate_language_id on learning_path_translate (language_id);
create index idx_learning_path_translate_learning_path_id on learning_path_translate (learning_path_id);

create index idx_lpt_learning_outcomes_lpt_id on learning_path_translate_learning_outcomes (learning_path_translate_id);

create index idx_lecture_progress_course_lecture_id on lecture_progress (course_lecture_id);
create index idx_lecture_progress_enrollment_id on lecture_progress (enrollment_id);

create index idx_meeting_email_reminder_user_id on meeting_email_reminder (user_id);

create index idx_order_details_course_id on order_details (course_id);
create index idx_order_details_enrollment_id on order_details (enrollment_id);
create index idx_order_details_order_id on order_details (order_id);

create index idx_payment_order_id on payment (order_id);

create index idx_personalized_bundle_bundle_id on personalized_bundle (bundle_id);
create index idx_personalized_bundle_source_bundle_id on personalized_bundle (source_bundle_id);
create index idx_personalized_bundle_user_id on personalized_bundle (user_id);

create index idx_personalized_learning_path_learning_path_id on personalized_learning_path (learning_path_id);
create index idx_personalized_learning_path_source_lp_id on personalized_learning_path (source_learning_path_id);
create index idx_personalized_learning_path_user_id on personalized_learning_path (user_id);

create index idx_quiz_course_module_id on quiz (course_module_id);
create index idx_quiz_course_version_id on quiz (course_version_id);

create index idx_qaa_selected_options_qao_id on quiz_attempt_answer_selected_options (quiz_answer_option_id);
create index idx_qaa_selected_options_qaa_id on quiz_attempt_answer_selected_options (quiz_attempt_answer_id);

create index idx_quiz_answer_option_quiz_question_id on quiz_answer_option (quiz_question_id);

create index idx_qao_translate_qao_id on quiz_answer_option_translate (quiz_answer_option_id);
create index idx_qao_translate_language_id on quiz_answer_option_translate (language_id);

create index idx_quiz_attempt_enrollment_id on quiz_attempt (enrollment_id);
create index idx_quiz_attempt_quiz_id on quiz_attempt (quiz_id);

create index idx_quiz_attempt_answer_quiz_question_id on quiz_attempt_answer (quiz_question_id);
create index idx_quiz_attempt_answer_quiz_attempt_id on quiz_attempt_answer (quiz_attempt_id);

create index idx_quiz_question_quiz_id on quiz_question (quiz_id);

create index idx_quiz_question_skill_quiz_question_id on quiz_question_skill (quiz_question_id);
create index idx_quiz_question_skill_skill_id on quiz_question_skill (skill_id);

create index idx_quiz_question_translate_language_id on quiz_question_translate (language_id);
create index idx_quiz_question_translate_quiz_question_id on quiz_question_translate (quiz_question_id);

create index idx_quiz_translate_language_id on quiz_translate (language_id);
create index idx_quiz_translate_quiz_id on quiz_translate (quiz_id);

create index idx_related_course_course_id on related_course (course_id);
create index idx_related_course_related_course_id on related_course (related_course_id);

create index idx_review_enrollment_id on review (enrollment_id);

create index idx_skill_translate_language_id on skill_translate (language_id);
create index idx_skill_translate_skill_id on skill_translate (skill_id);

create index idx_topic_translate_language_id on topic_translate (language_id);
create index idx_topic_translate_topic_id on topic_translate (topic_id);

create index idx_user_favorite_course_course_id on user_favorite_course (course_id);
create index idx_user_favorite_course_user_id on user_favorite_course (user_id);
create index idx_user_favorite_course_user_id_course_id on user_favorite_course (user_id, course_id);

create index idx_user_topic_topic_id on user_topic (topic_id);
create index idx_user_topic_user_id on user_topic (user_id);
create index idx_user_topic_user_id_topic_id on user_topic (user_id, topic_id);

create index idx_user_bundle_bundle_id on user_bundle (bundle_id);
create index idx_user_bundle_user_id on user_bundle (user_id);

create index idx_user_learning_path_learning_path_id on user_learning_path (learning_path_id);
create index idx_user_learning_path_user_id on user_learning_path (user_id);

create index idx_user_skill_enrollment_id on user_skill (enrollment_id);
create index idx_user_skill_skill_id on user_skill (skill_id);
create index idx_user_skill_user_id on user_skill (user_id);

create index idx_user_skill_snapshot_enrollment_id on user_skill_snapshot (enrollment_id);
create index idx_user_skill_snapshot_quiz_attempt_id on user_skill_snapshot (quiz_attempt_id);
create index idx_user_skill_snapshot_user_skill_id on user_skill_snapshot (user_skill_id);

create index idx_verification_token_user_id on verification_token (user_id);