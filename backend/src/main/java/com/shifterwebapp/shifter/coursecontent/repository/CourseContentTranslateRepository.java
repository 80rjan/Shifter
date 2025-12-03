package com.shifterwebapp.shifter.coursecontent.repository;

import com.shifterwebapp.shifter.coursecontent.CourseContent;
import com.shifterwebapp.shifter.coursecontent.CourseContentTranslate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface CourseContentTranslateRepository extends JpaRepository<CourseContentTranslate, Long> {

}
