
    create sequence _order_seq start with 1 increment by 50;

    create sequence _user_seq start with 1 increment by 50;

    create sequence admin_seq start with 1 increment by 50;

    create sequence bundle_seq start with 1 increment by 50;

    create sequence bundle_translate_seq start with 1 increment by 50;

    create sequence certificate_seq start with 1 increment by 50;

    create sequence course_activity_event_seq start with 1 increment by 50;

    create sequence course_lecture_seq start with 1 increment by 50;

    create sequence course_lecture_translate_seq start with 1 increment by 50;

    create sequence course_module_seq start with 1 increment by 50;

    create sequence course_module_translate_seq start with 1 increment by 50;

    create sequence course_price_seq start with 1 increment by 50;

    create sequence course_seq start with 1 increment by 50;

    create sequence course_translate_seq start with 1 increment by 50;

    create sequence course_version_seq start with 1 increment by 50;

    create sequence curated_bundle_seq start with 1 increment by 50;

    create sequence curated_learning_path_seq start with 1 increment by 50;

    create sequence enrollment_seq start with 1 increment by 50;

    create sequence expert_seq start with 1 increment by 50;

    create sequence language_seq start with 1 increment by 50;

    create sequence learning_path_course_seq start with 1 increment by 50;

    create sequence learning_path_seq start with 1 increment by 50;

    create sequence learning_path_translate_seq start with 1 increment by 50;

    create sequence lecture_progress_seq start with 1 increment by 50;

    create sequence meeting_email_reminder_seq start with 1 increment by 50;

    create sequence order_details_seq start with 1 increment by 50;

    create sequence payment_seq start with 1 increment by 50;

    create sequence personalized_bundle_seq start with 1 increment by 50;

    create sequence personalized_learning_path_seq start with 1 increment by 50;

    create sequence quiz_answer_option_seq start with 1 increment by 50;

    create sequence quiz_answer_option_translate_seq start with 1 increment by 50;

    create sequence quiz_attempt_answer_seq start with 1 increment by 50;

    create sequence quiz_attempt_seq start with 1 increment by 50;

    create sequence quiz_question_seq start with 1 increment by 50;

    create sequence quiz_question_skill_seq start with 1 increment by 50;

    create sequence quiz_question_translate_seq start with 1 increment by 50;

    create sequence quiz_seq start with 1 increment by 50;

    create sequence quiz_translate_seq start with 1 increment by 50;

    create sequence related_course_seq start with 1 increment by 50;

    create sequence review_seq start with 1 increment by 50;

    create sequence skill_seq start with 1 increment by 50;

    create sequence skill_translate_seq start with 1 increment by 50;

    create sequence topic_seq start with 1 increment by 50;

    create sequence topic_translate_seq start with 1 increment by 50;

    create sequence user_bundle_seq start with 1 increment by 50;

    create sequence user_learning_path_seq start with 1 increment by 50;

    create sequence user_skill_seq start with 1 increment by 50;

    create sequence user_skill_snapshot_seq start with 1 increment by 50;

    create table _order (
        created_at timestamp(6) not null,
        id bigint not null,
        status varchar(255) not null check (status in ('PENDING','IN_PROGRESS','COMPLETED','CANCELLED')),
        primary key (id)
    );

    create table _user (
        deleted boolean not null,
        points integer,
        profile_complete boolean not null,
        used_free_consultation boolean not null,
        verified boolean not null,
        created_at timestamp(6) not null,
        id bigint not null,
        updated_at timestamp(6) not null,
        company_size varchar(255) check (company_size in ('FREELANCE','MICRO','SMALL','MEDIUM','MID_MARKET','ENTERPRISE','OTHER')),
        email varchar(255) not null,
        login_provider varchar(255) not null check (login_provider in ('LOCAL','GOOGLE')),
        name varchar(255),
        password_hash varchar(255) not null,
        work_position varchar(255),
        primary key (id)
    );

    create table admin (
        id bigint not null,
        email varchar(255) not null,
        login_provider varchar(255) not null check (login_provider in ('LOCAL','GOOGLE')),
        name varchar(255),
        password_hash varchar(255) not null,
        primary key (id)
    );

    create table bundle (
        active boolean not null,
        base_price numeric(38,2) not null,
        discount_amount numeric(38,2) not null,
        discount_percentage numeric(38,2) not null,
        created_at timestamp(6) not null,
        deactivated_at timestamp(6),
        id bigint not null,
        updated_at timestamp(6) not null,
        image_url varchar(255) not null,
        slug varchar(255) not null,
        type varchar(255) not null check (type in ('SYSTEM_GENERATED','CURATED','PERSONALIZED')),
        primary key (id)
    );

    create table bundle_translate (
        bundle_id bigint not null,
        created_at timestamp(6) not null,
        id bigint not null,
        language_id bigint not null,
        description varchar(255) not null,
        title varchar(255) not null,
        primary key (id)
    );

    create table certificate (
        issue_date date not null,
        enrollment_id bigint not null unique,
        id bigint not null,
        user_id bigint not null,
        certificate_number varchar(255) not null,
        certificate_url varchar(255) not null,
        primary key (id)
    );

    create table course (
        duration_minutes integer not null,
        id bigint not null,
        color varchar(255),
        difficulty varchar(255) not null check (difficulty in ('BEGINNER','INTERMEDIATE','ADVANCED','EXPERT')),
        image_url text not null,
        primary key (id)
    );

    create table course_skill (
        course_id bigint not null,
        skill_id bigint not null
    );

    create table course_topic (
        course_id bigint not null,
        topic_id bigint not null
    );

    create table course_activity_event (
        course_id bigint not null,
        id bigint not null,
        timestamp timestamp(6) not null,
        user_id bigint not null,
        event_type varchar(255) not null check (event_type in ('COURSE_VIEWED','COURSE_ENROLLED','COURSE_STARTED','COURSE_COMPLETED','COURSE_ABANDONED','LESSON_STARTED','LESSON_COMPLETED','MODULE_COMPLETED','QUIZ_ATTEMPTED','QUIZ_PASSED','QUIZ_FAILED','VIDEO_WATCHED','RESOURCE_DOWNLOADED','EXERCISE_SUBMITTED','DISCUSSION_PARTICIPATED','COURSE_WISHLISTED','COURSE_SHARED','CERTIFICATE_DOWNLOADED','COURSE_REVIEWED','COURSE_UNENROLLED','LESSON_SKIPPED')),
        primary key (id)
    );

    create table course_lecture (
        duration_minutes integer not null,
        position integer not null,
        course_module_id bigint not null,
        id bigint not null,
        content_type varchar(255) not null check (content_type in ('VIDEO','TEXT','FILE','QUIZ','TOOL')),
        primary key (id)
    );

    create table course_lecture_translate (
        course_lecture_id bigint not null,
        created_at timestamp(6) not null,
        id bigint not null,
        language_id bigint not null,
        content_file_name text,
        content_text text,
        description text not null,
        title varchar(255) not null,
        primary key (id)
    );

    create table course_module (
        position integer not null,
        course_version_id bigint not null,
        id bigint not null,
        primary key (id)
    );

    create table course_module_translate (
        course_module_id bigint not null,
        created_at timestamp(6) not null,
        id bigint not null,
        language_id bigint not null,
        title varchar(255),
        primary key (id)
    );

    create table course_price (
        active boolean not null,
        amount numeric(38,2) not null,
        discount_amount numeric(38,2) not null,
        discount_percentage numeric(38,2) not null,
        discounted boolean not null,
        course_id bigint not null,
        created_at timestamp(6) not null,
        id bigint not null,
        primary key (id)
    );

    create table course_translate (
        course_id bigint not null,
        created_at timestamp(6) not null,
        id bigint not null,
        language_id bigint not null,
        description text not null,
        description_long text not null,
        description_short varchar(255) not null,
        title varchar(255) not null,
        title_short varchar(255) not null,
        primary key (id)
    );

    create table course_translate_what_will_be_learned (
        course_translate_id bigint not null,
        what_will_be_learned text
    );

    create table course_version (
        active boolean not null,
        creation_date date not null,
        version_number integer not null,
        course_id bigint not null,
        id bigint not null,
        primary key (id)
    );

    create table curated_bundle (
        bundle_id bigint not null unique,
        id bigint not null,
        primary key (id)
    );

    create table curated_learning_path (
        id bigint not null,
        learning_path_id bigint not null unique,
        primary key (id)
    );

    create table enrollment (
        activation_date date,
        completion_date date,
        enrollment_date date not null,
        on_trial boolean not null,
        purchase_date date,
        course_version_id bigint not null,
        id bigint not null,
        updated_at timestamp(6) not null,
        user_id bigint not null,
        enrollment_status varchar(255) not null check (enrollment_status in ('PENDING','ACTIVE','COMPLETED')),
        enrollment_type varchar(255) not null check (enrollment_type in ('INDIVIDUAL','BUNDLE','LEARNING_PATH')),
        primary key (id)
    );

    create table expert (
        id bigint not null,
        email varchar(255) not null,
        login_provider varchar(255) not null check (login_provider in ('LOCAL','GOOGLE')),
        name varchar(255),
        password_hash varchar(255) not null,
        primary key (id)
    );

    create table expert_course (
        course_id bigint not null,
        expert_id bigint not null
    );

    create table expert_curated_bundle (
        curated_bundle_id bigint not null,
        expert_id bigint not null
    );

    create table expert_curated_learning_path (
        curated_learning_path_id bigint not null,
        expert_id bigint not null
    );

    create table language (
        language_code smallint not null check (language_code between 0 and 1),
        id bigint not null,
        name varchar(255) not null,
        native_name varchar(255) not null,
        primary key (id)
    );

    create table learning_path (
        active boolean not null,
        base_price numeric(38,2) not null,
        discount_amount numeric(38,2) not null,
        discount_percentage numeric(38,2) not null,
        discounted boolean not null,
        estimated_duration_hours integer not null,
        created_at timestamp(6) not null,
        deactivated_at timestamp(6),
        id bigint not null,
        difficulty varchar(255) not null check (difficulty in ('BEGINNER','INTERMEDIATE','ADVANCED','EXPERT')),
        image_url varchar(255) not null,
        slug varchar(255) not null,
        type varchar(255) not null check (type in ('SYSTEM_GENERATED','CURATED','PERSONALIZED')),
        primary key (id)
    );

    create table learning_path_course (
        sequence_order integer not null,
        course_id bigint not null,
        id bigint not null,
        learning_path_id bigint not null,
        primary key (id)
    );

    create table learning_path_translate (
        created_at timestamp(6) not null,
        id bigint not null,
        language_id bigint not null,
        learning_path_id bigint not null,
        description varchar(255) not null,
        title varchar(255) not null,
        primary key (id)
    );

    create table learning_path_translate_learning_outcomes (
        learning_path_translate_id bigint not null,
        learning_outcomes text
    );

    create table lecture_progress (
        completed boolean not null,
        completed_at timestamp(6) not null,
        course_lecture_id bigint not null,
        enrollment_id bigint not null,
        id bigint not null,
        primary key (id)
    );

    create table meeting_email_reminder (
        sent boolean not null,
        created_at timestamp(6) not null,
        id bigint not null,
        meeting_at timestamp(6) not null,
        scheduled_at timestamp(6) not null,
        updated_at timestamp(6) not null,
        user_id bigint not null,
        meeting_link TEXT not null,
        status varchar(255) not null check (status in ('PENDING','SENT','FAILED')),
        primary key (id)
    );

    create table order_details (
        discount_amount numeric(38,2) not null,
        discount_percentage numeric(38,2) not null,
        price numeric(38,2) not null,
        course_id bigint not null,
        created_at timestamp(6) not null,
        enrollment_id bigint not null unique,
        id bigint not null,
        order_id bigint not null,
        primary key (id)
    );

    create table payment (
        amount float(53) not null,
        date date not null,
        id bigint not null,
        order_id bigint not null unique,
        method varchar(255) not null check (method in ('CARD','PAYPAL','CASYS')),
        status varchar(255) not null check (status in ('PENDING','COMPLETED','FAILED','REFUNDED')),
        primary key (id)
    );

    create table personalized_bundle (
        active boolean not null,
        added_discount_amount numeric(38,2) not null,
        added_discount_percent numeric(38,2) not null,
        discounted boolean not null,
        bundle_id bigint not null,
        created_at timestamp(6) not null,
        expires_at timestamp(6) not null,
        id bigint not null,
        source_bundle_id bigint,
        user_id bigint not null,
        generated_reason varchar(255) not null check (generated_reason in ('SKILL_GAP_DETECTED','CAREER_ADVANCEMENT','ROLE_RELEVANT_SKILL','COURSE_ALREADY_ENROLLED')),
        type varchar(255) not null check (type in ('SYSTEM_RECOMMENDATION','ADJUSTED_BUNDLE')),
        primary key (id)
    );

    create table personalized_learning_path (
        active boolean not null,
        added_discount_amount numeric(38,2) not null,
        added_discount_percent numeric(38,2) not null,
        discounted boolean not null,
        created_at timestamp(6) not null,
        expires_at timestamp(6) not null,
        id bigint not null,
        learning_path_id bigint not null,
        source_learning_path_id bigint,
        user_id bigint not null,
        generated_reason varchar(255) not null check (generated_reason in ('SKILL_GAP_DETECTED','CAREER_ADVANCEMENT','ROLE_RELEVANT_SKILL','COURSE_ALREADY_ENROLLED')),
        type varchar(255) not null check (type in ('SYSTEM_RECOMMENDATION','ADJUSTED_LEARNING_PATH')),
        primary key (id)
    );

    create table quiz (
        active boolean not null,
        passing_score integer not null,
        randomized boolean not null,
        course_module_id bigint unique,
        course_version_id bigint unique,
        created_at timestamp(6) not null,
        id bigint not null,
        type varchar(255) not null check (type in ('PRE_DIAGNOSTIC','CHECKPOINT','FINAL')),
        primary key (id)
    );

    create table quiz_attempt_answer_selected_options (
        quiz_answer_option_id bigint not null,
        quiz_attempt_answer_id bigint not null
    );

    create table quiz_answer_option (
        correct boolean not null,
        id bigint not null,
        quiz_question_id bigint not null,
        primary key (id)
    );

    create table quiz_answer_option_translate (
        created_at timestamp(6) not null,
        id bigint not null,
        language_id bigint not null,
        quiz_answer_option_id bigint not null,
        answer_text TEXT not null,
        explanation varchar(255) not null,
        primary key (id)
    );

    create table quiz_attempt (
        attempt_number integer not null,
        earned_points integer not null,
        passed boolean not null,
        score integer not null,
        total_points integer not null,
        completed_at timestamp(6) not null,
        enrollment_id bigint not null,
        id bigint not null,
        quiz_id bigint not null,
        started_at timestamp(6) not null,
        status varchar(255) not null check (status in ('IN_PROGRESS','PASSED','FAILED','ABANDONED')),
        primary key (id)
    );

    create table quiz_attempt_answer (
        correct boolean not null,
        id bigint not null,
        quiz_attempt_id bigint not null,
        quiz_question_id bigint not null,
        primary key (id)
    );

    create table quiz_question (
        points integer not null,
        position integer not null,
        type smallint not null check (type between 0 and 2),
        id bigint not null,
        quiz_id bigint not null,
        primary key (id)
    );

    create table quiz_question_skill (
        proficiency smallint check (proficiency between 0 and 3),
        weight integer,
        id bigint not null,
        quiz_question_id bigint not null,
        skill_id bigint not null,
        primary key (id)
    );

    create table quiz_question_translate (
        created_at timestamp(6) not null,
        id bigint not null,
        language_id bigint not null,
        quiz_question_id bigint not null,
        question_text TEXT not null,
        scenario TEXT,
        primary key (id)
    );

    create table quiz_translate (
        created_at timestamp(6),
        id bigint not null,
        language_id bigint not null,
        quiz_id bigint not null,
        description TEXT not null,
        title varchar(255) not null,
        primary key (id)
    );

    create table related_course (
        similarity_score numeric(38,2),
        calculated_at timestamp(6),
        course_id bigint not null,
        id bigint not null,
        related_course_id bigint not null,
        primary key (id)
    );

    create table review (
        date date not null,
        rating integer not null,
        enrollment_id bigint unique,
        id bigint not null,
        comment varchar(255),
        primary key (id)
    );

    create table skill (
        show_in_radar boolean not null,
        id bigint not null,
        slug varchar(255) not null unique,
        primary key (id)
    );

    create table skill_translate (
        created_at timestamp(6) not null,
        id bigint not null,
        language_id bigint not null,
        skill_id bigint not null,
        description varchar(255) not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table topic (
        id bigint not null,
        slug varchar(255) not null unique,
        primary key (id)
    );

    create table topic_translate (
        created_at timestamp(6) not null,
        id bigint not null,
        language_id bigint not null,
        topic_id bigint not null,
        description varchar(255) not null,
        name varchar(255) not null,
        primary key (id)
    );

    create table user_favorite_course (
        course_id bigint not null,
        user_id bigint not null
    );

    create table user_topic (
        topic_id bigint not null,
        user_id bigint not null
    );

    create table user_bundle (
        acquired_date date not null,
        bundle_id bigint not null,
        id bigint not null,
        user_id bigint not null,
        primary key (id)
    );

    create table user_learning_path (
        acquired_date date not null,
        completed_date date,
        progress_percentage integer not null,
        id bigint not null,
        learning_path_id bigint not null,
        updated_at timestamp(6) not null,
        user_id bigint not null,
        status varchar(255) not null check (status in ('NOT_STARTED','IN_PROGRESS','COMPLETED')),
        primary key (id)
    );

    create table user_skill (
        proficiency_score integer not null,
        verified boolean not null,
        created_at timestamp(6) not null,
        enrollment_id bigint not null,
        id bigint not null,
        skill_id bigint not null,
        updated_at timestamp(6) not null,
        user_id bigint not null,
        proficiency varchar(255) not null check (proficiency in ('BEGINNER','INTERMEDIATE','ADVANCED','EXPERT')),
        primary key (id)
    );

    create table user_skill_snapshot (
        new_proficiency_score integer not null,
        proficiency_score_at_time integer not null,
        created_at timestamp(6) not null,
        enrollment_id bigint not null,
        id bigint not null,
        quiz_attempt_id bigint not null,
        user_skill_id bigint not null,
        new_proficiency varchar(255) not null check (new_proficiency in ('BEGINNER','INTERMEDIATE','ADVANCED','EXPERT')),
        proficiency_at_time varchar(255) not null check (proficiency_at_time in ('BEGINNER','INTERMEDIATE','ADVANCED','EXPERT')),
        primary key (id)
    );

    create table verification_token (
        created_at timestamp(6) with time zone not null,
        expires_at timestamp(6) with time zone not null,
        user_id bigint not null unique,
        token uuid not null,
        primary key (token)
    );

    alter table if exists bundle_translate 
       add constraint FKhaxeigblumyn7d6yihc2kaw7a 
       foreign key (bundle_id) 
       references bundle;

    alter table if exists bundle_translate 
       add constraint FKk6khtf9q2h4y54mp6gdbamg2g 
       foreign key (language_id) 
       references language;

    alter table if exists certificate 
       add constraint FK6ahwata2qxlvb8e3fe0qtseq1 
       foreign key (enrollment_id) 
       references enrollment;

    alter table if exists certificate 
       add constraint FKk01mc5gvmwis2leepuntymwek 
       foreign key (user_id) 
       references _user;

    alter table if exists course_skill 
       add constraint FKdq4boqb20geguit45rgr1r33v 
       foreign key (skill_id) 
       references skill;

    alter table if exists course_skill 
       add constraint FKn17ep7229hbi0li6eobs1mi6q 
       foreign key (course_id) 
       references course;

    alter table if exists course_topic 
       add constraint FKpmlbmev283ud50kxgvmyjcbhj 
       foreign key (topic_id) 
       references topic;

    alter table if exists course_topic 
       add constraint FK6esfb41t5ja5jp8p49uv72x9d 
       foreign key (course_id) 
       references course;

    alter table if exists course_activity_event 
       add constraint FKl0jx91qej21fg8kddkbxyceca 
       foreign key (course_id) 
       references course;

    alter table if exists course_activity_event 
       add constraint FKsti6o99mr9vn1epoinlpn3v1b 
       foreign key (user_id) 
       references _user;

    alter table if exists course_lecture 
       add constraint FK4jkngg76jhg924w4ecest5qhh 
       foreign key (course_module_id) 
       references course_module;

    alter table if exists course_lecture_translate 
       add constraint FKgo0vlymbsq4k182sijw4weqkn 
       foreign key (course_lecture_id) 
       references course_lecture;

    alter table if exists course_lecture_translate 
       add constraint FKkqtgoxeoh0rpp4ycvk6yhgpo2 
       foreign key (language_id) 
       references language;

    alter table if exists course_module 
       add constraint FKaqw6q4ekvbyb71xbxf9qo06id 
       foreign key (course_version_id) 
       references course_version;

    alter table if exists course_module_translate 
       add constraint FKo0ggy3yi0rj37lg4j6q5x4ucg 
       foreign key (course_module_id) 
       references course_module;

    alter table if exists course_module_translate 
       add constraint FK83247nn89bkc3puds96d3c0nq 
       foreign key (language_id) 
       references language;

    alter table if exists course_price 
       add constraint FK9qy75q9hyu79mfx4i80rbd57 
       foreign key (course_id) 
       references course;

    alter table if exists course_translate 
       add constraint FK7ve3o6tucv149g1qac8cyi2a9 
       foreign key (course_id) 
       references course;

    alter table if exists course_translate 
       add constraint FK4bmogcl4eaavn8hdyphdsfqf8 
       foreign key (language_id) 
       references language;

    alter table if exists course_translate_what_will_be_learned 
       add constraint FKmntkd3o4lhrfoqlx445nffvg2 
       foreign key (course_translate_id) 
       references course_translate;

    alter table if exists course_version 
       add constraint FK6yglm1887hjjbrm60ll1eg2fv 
       foreign key (course_id) 
       references course;

    alter table if exists curated_bundle 
       add constraint FK4atyfqgbdqjub5p6a5ems4blf 
       foreign key (bundle_id) 
       references bundle;

    alter table if exists curated_learning_path 
       add constraint FK1rkmrhutbilapn9tksgrf0p4q 
       foreign key (learning_path_id) 
       references learning_path;

    alter table if exists enrollment 
       add constraint FKqch2vm3goe0xq8i4b2p0s6n5c 
       foreign key (course_version_id) 
       references course_version;

    alter table if exists enrollment 
       add constraint FKk2q6td98gy1kh54uxtompibte 
       foreign key (user_id) 
       references _user;

    alter table if exists expert_course 
       add constraint FKdi9swk9y35h363h9arfi8cv7i 
       foreign key (course_id) 
       references course;

    alter table if exists expert_course 
       add constraint FKl8f98fsvl2rsrcfyehpytorjm 
       foreign key (expert_id) 
       references expert;

    alter table if exists expert_curated_bundle 
       add constraint FKcl731nyfiesam1hhblgu52lkx 
       foreign key (curated_bundle_id) 
       references curated_bundle;

    alter table if exists expert_curated_bundle 
       add constraint FKktqhmeu183njbob7sjban6e9h 
       foreign key (expert_id) 
       references expert;

    alter table if exists expert_curated_learning_path 
       add constraint FK2jc4fkqvn2o8kc7idkbi656bu 
       foreign key (curated_learning_path_id) 
       references curated_learning_path;

    alter table if exists expert_curated_learning_path 
       add constraint FK8awl7vwwnp8k4en34o5sb0dip 
       foreign key (expert_id) 
       references expert;

    alter table if exists learning_path_course 
       add constraint FKishnjvramgwwa7thrjou3kddp 
       foreign key (course_id) 
       references course;

    alter table if exists learning_path_course 
       add constraint FKgklotfyjlrc0ydbfnu2ff78d4 
       foreign key (learning_path_id) 
       references learning_path;

    alter table if exists learning_path_translate 
       add constraint FKpyjcef4m15jobe2kra6wtmmd8 
       foreign key (language_id) 
       references language;

    alter table if exists learning_path_translate 
       add constraint FKfi70ucc5wtb0urc5g90on0muo 
       foreign key (learning_path_id) 
       references learning_path;

    alter table if exists learning_path_translate_learning_outcomes 
       add constraint FK7769fcw4b0utsf48r0mxivdl0 
       foreign key (learning_path_translate_id) 
       references learning_path_translate;

    alter table if exists lecture_progress 
       add constraint FKonh9tp6oswh0r969va5k29qcp 
       foreign key (course_lecture_id) 
       references course_lecture;

    alter table if exists lecture_progress 
       add constraint FKg66j5hw5xj3xpb2aei7n9u8h4 
       foreign key (enrollment_id) 
       references enrollment;

    alter table if exists meeting_email_reminder 
       add constraint FKy8jy72yp609y7oynv2jt34wc 
       foreign key (user_id) 
       references _user;

    alter table if exists order_details 
       add constraint FKkb349a35bgtcmvcchhsjil24g 
       foreign key (course_id) 
       references course;

    alter table if exists order_details 
       add constraint FKirqg49mevvtyher2f1vrmog63 
       foreign key (enrollment_id) 
       references enrollment;

    alter table if exists order_details 
       add constraint FK7xtsljbo390gc608yxxdrwi5r 
       foreign key (order_id) 
       references _order;

    alter table if exists payment 
       add constraint FKp12cpy6idihw3gyo3sv7pfcw2 
       foreign key (order_id) 
       references _order;

    alter table if exists personalized_bundle 
       add constraint FKg1kly5ojlcp9cc2l40rxpw4up 
       foreign key (bundle_id) 
       references bundle;

    alter table if exists personalized_bundle 
       add constraint FKpu1xid370ofgyo71a8rgi087v 
       foreign key (source_bundle_id) 
       references curated_bundle;

    alter table if exists personalized_bundle 
       add constraint FK6t67fgumndnw2aswgm8iy3ql 
       foreign key (user_id) 
       references _user;

    alter table if exists personalized_learning_path 
       add constraint FK26yby03yywv3qj3mlffx60swg 
       foreign key (learning_path_id) 
       references learning_path;

    alter table if exists personalized_learning_path 
       add constraint FKoj7jamgtd310vffare41id3jl 
       foreign key (source_learning_path_id) 
       references curated_learning_path;

    alter table if exists personalized_learning_path 
       add constraint FKn3ej0qye221430t7odddlhjir 
       foreign key (user_id) 
       references _user;

    alter table if exists quiz 
       add constraint FKh1oxnsy9mlbm9327assy73554 
       foreign key (course_module_id) 
       references course_module;

    alter table if exists quiz 
       add constraint FK7ipimjgupvu0n9069efe8j3n9 
       foreign key (course_version_id) 
       references course_version;

    alter table if exists quiz_attempt_answer_selected_options 
       add constraint FK6qebj52a5eo2k279tek54ffia 
       foreign key (quiz_answer_option_id) 
       references quiz_answer_option;

    alter table if exists quiz_attempt_answer_selected_options 
       add constraint FK4qgqoa7r29lx1f7wc2conwtrk 
       foreign key (quiz_attempt_answer_id) 
       references quiz_attempt_answer;

    alter table if exists quiz_answer_option 
       add constraint FKn4qkhxiy5vbgk2bw4lmnyly0i 
       foreign key (quiz_question_id) 
       references quiz_question;

    alter table if exists quiz_answer_option_translate 
       add constraint FKe8sq2bkcnabhigi6pod0kc6hu 
       foreign key (quiz_answer_option_id) 
       references quiz_answer_option;

    alter table if exists quiz_answer_option_translate 
       add constraint FKijfcf9vq82ctcsr2bd6920o10 
       foreign key (language_id) 
       references language;

    alter table if exists quiz_attempt 
       add constraint FK1mfvh50tb8kir4uvilp6btaaw 
       foreign key (enrollment_id) 
       references enrollment;

    alter table if exists quiz_attempt 
       add constraint FK8l6wmgul0rgeha0lp6abrp5fa 
       foreign key (quiz_id) 
       references quiz;

    alter table if exists quiz_attempt_answer 
       add constraint FKkjt1vbkna2o7m6kkph3mdly51 
       foreign key (quiz_question_id) 
       references quiz_question;

    alter table if exists quiz_attempt_answer 
       add constraint FK3drqtufjr375d34nehuidp1xy 
       foreign key (quiz_attempt_id) 
       references quiz_attempt;

    alter table if exists quiz_question 
       add constraint FKdtynvfjgh6e7fd8l0wk37nrpc 
       foreign key (quiz_id) 
       references quiz;

    alter table if exists quiz_question_skill 
       add constraint FK3yl3t40y5wpse8pnjifj86mt6 
       foreign key (quiz_question_id) 
       references quiz_question;

    alter table if exists quiz_question_skill 
       add constraint FKtf69jtugu6navrve98lfkcmy7 
       foreign key (skill_id) 
       references skill;

    alter table if exists quiz_question_translate 
       add constraint FK15luw8cuosj4j3s0eibgawnjx 
       foreign key (language_id) 
       references language;

    alter table if exists quiz_question_translate 
       add constraint FKs8ptlir5wbsb11e40ld4ewpqe 
       foreign key (quiz_question_id) 
       references quiz_question;

    alter table if exists quiz_translate 
       add constraint FKn32jbf2h5ma6xexku4c2vm8t8 
       foreign key (language_id) 
       references language;

    alter table if exists quiz_translate 
       add constraint FKjubqallbaweegpby2jei7nrmk 
       foreign key (quiz_id) 
       references quiz;

    alter table if exists related_course 
       add constraint FKavg12dqv92iedvowrefqve7qf 
       foreign key (course_id) 
       references course;

    alter table if exists related_course 
       add constraint FK9ndljtid903b318m3y368a8q0 
       foreign key (related_course_id) 
       references course;

    alter table if exists review 
       add constraint FKtbcjdinaby874shrxwa2by95k 
       foreign key (enrollment_id) 
       references enrollment;

    alter table if exists skill_translate 
       add constraint FKqnt1g1wr2q9tvn5mgpyscnm0f 
       foreign key (language_id) 
       references language;

    alter table if exists skill_translate 
       add constraint FKd5s22k58rxsydkm9t77bne4sv 
       foreign key (skill_id) 
       references skill;

    alter table if exists topic_translate 
       add constraint FKdx19l12il8thorhnplc6k95on 
       foreign key (language_id) 
       references language;

    alter table if exists topic_translate 
       add constraint FKbxd3m0gg2h9h6l5t1o5bmxnge 
       foreign key (topic_id) 
       references topic;

    alter table if exists user_favorite_course 
       add constraint FKpd7kuwmi2fts1x0rnsm46ven5 
       foreign key (course_id) 
       references course;

    alter table if exists user_favorite_course 
       add constraint FKkx7btq6vl0t7c92wk101oce7b 
       foreign key (user_id) 
       references _user;

    alter table if exists user_topic 
       add constraint FKrbbt9w8w48w4pl7dv0lh3om0v 
       foreign key (topic_id) 
       references topic;

    alter table if exists user_topic 
       add constraint FKrigmw1ul2tladbkykn6p7nq9y 
       foreign key (user_id) 
       references _user;

    alter table if exists user_bundle 
       add constraint FKkgujilv0ofi6carmk8mynjc82 
       foreign key (bundle_id) 
       references bundle;

    alter table if exists user_bundle 
       add constraint FKp71mnd97q842p2muw98c496d 
       foreign key (user_id) 
       references _user;

    alter table if exists user_learning_path 
       add constraint FK1gg9vx9pf19d7bal3x6xb50uc 
       foreign key (learning_path_id) 
       references learning_path;

    alter table if exists user_learning_path 
       add constraint FKj1whqeu8rm7dddql70uh3qq93 
       foreign key (user_id) 
       references _user;

    alter table if exists user_skill 
       add constraint FKo92ghoo9sk6kemgvb6d9lpq8k 
       foreign key (enrollment_id) 
       references enrollment;

    alter table if exists user_skill 
       add constraint FKj53flyds4vknyh8llw5d7jdop 
       foreign key (skill_id) 
       references skill;

    alter table if exists user_skill 
       add constraint FKqsxejk0f9hr4jpdoygwoyn6qg 
       foreign key (user_id) 
       references _user;

    alter table if exists user_skill_snapshot 
       add constraint FKqasc7n3cyymgb3x6l7btskbab 
       foreign key (enrollment_id) 
       references enrollment;

    alter table if exists user_skill_snapshot 
       add constraint FKrkpjyrpl6c4m2r0rw43e24u5s 
       foreign key (quiz_attempt_id) 
       references quiz_attempt;

    alter table if exists user_skill_snapshot 
       add constraint FKr22wle0esupohke0m179l39tv 
       foreign key (user_skill_id) 
       references user_skill;

    alter table if exists verification_token 
       add constraint FK311qhdx7kfhq2m0utkvjs7ay8 
       foreign key (user_id) 
       references _user;
