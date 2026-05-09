-- view #1 - get enrolled courses for a user (for a specific user its one where clause in the end)
drop view if exists enrolled_courses;
create or replace view enrolled_courses as
(
select
    e.user_id                      as user_id,
    cv.version_number              as course_version_id,
    cv.active                      as version_is_active,
    cv.creation_date               as version_creation_date,
    c.id                           as course_id,
    c.image_url,
    c.color,
    c.difficulty,
    c.duration_minutes,
    ct.title_short,

    (select array_agg(st.name order by st.name)
     from skill s
              join course_skill cs on cs.skill_id = s.id
              join skill_translation st on st.skill_id = s.id
              join language l on l.id = st.language_id
     where cs.course_id = c.id
       and l.language_code = 'EN') as skills_learned,

    (select array_agg(tt.name order by tt.name)
     from topic t
              join course_topic ct on ct.topic_id = t.id
              join topic_translation tt on tt.topic_id = t.id
              join language l on l.id = tt.language_id
     where ct.course_id = c.id
       and l.language_code = 'EN') as topics_covered

from course_version cv
         join enrollment e on e.course_version_id = cv.id
         join course c on c.id = cv.course_id
         join course_translation ct on ct.course_id = c.id
         join language l on l.id = ct.language_id
where l.language_code = 'EN'
    );

select *
from enrolled_courses;


-- view #2 - get detailed information about an enrolled course for a user (for a specific user its one where clause in the end)
drop view if exists enrolled_course_details;
create or replace view enrolled_course_details as
select
    e.user_id,
    cv.version_number as course_version_id,
    cv.active as version_is_active,
    cv.creation_date as version_creation_date,
    c.id as course_id,
    c.image_url,
    c.color,
    c.difficulty,
    c.duration_minutes,
    ct.title,
    ct.description,
    ct.description_long,

    (
        select json_agg(
                       json_build_object(
                               'module_id', cm.id,
                               'position', cm.position,
                               'title', cmt.title,

                               'lectures',
                               (
                                   select json_agg(
                                                  json_build_object(
                                                          'lecture_id', cl.id,
                                                          'position', cl.position,
                                                          'duration_minutes', cl.duration_minutes,
                                                          'content_type', cl.content_type,
                                                          'title', clt.title,
                                                          'description', clt.description,
                                                          'content_file_name', clt.content_file_name,
                                                          'content_text', clt.content_text
                                                  )
                                                  order by cl.position
                                          )
                                   from course_lecture cl
                                            join course_lecture_translation clt
                                                 on clt.course_lecture_id = cl.id
                                            join language l2
                                                 on l2.id = clt.language_id
                                   where cl.course_module_id = cm.id
                                     and l2.language_code = 'EN'
                               )
                       )
                       order by cm.position
               )
        from course_module cm
                 join course_module_translation cmt
                      on cmt.course_module_id = cm.id
                 join language l1
                      on l1.id = cmt.language_id
        where cm.course_version_id = cv.id
          and l1.language_code = 'EN'
    ) as modules

from course_version cv
         join enrollment e
              on e.course_version_id = cv.id
         join course c
              on c.id = cv.course_id
         join course_translation ct
              on ct.course_id = c.id
         join language l
              on l.id = ct.language_id
where l.language_code = 'EN';

select * from enrolled_course_details;


-- view #3 - top 10 best-selling courses, platform wide
drop view if exists best_selling_courses;
create or replace view best_selling_courses as
select
    c.id                           as course_id,
    c.difficulty,
    c.duration_minutes,
    ct.title_short,

    count(e.user_id) as total_enrollments

from course c
         join course_version cv on cv.course_id = c.id
         join enrollment e on e.course_version_id = cv.id
         join course_translation ct on ct.course_id = c.id
         join language l on l.id = ct.language_id
where l.language_code = 'EN'
group by c.id, ct.id
order by total_enrollments desc
limit 10;

select * from best_selling_courses;