package com.shifterwebapp.shifter.course;

import com.shifterwebapp.shifter.enums.Difficulty;
import com.shifterwebapp.shifter.enums.Skills;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class CourseSpecification {

    public static Specification<Course> hasTitleLike(String title) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("title")), "%" + title.toLowerCase() + "%");
    }

    public static Specification<Course> hasTopicLike(String topic) {
        return (root, query, cb) ->
                cb.like(cb.lower(root.get("topic")), "%" + topic.toLowerCase() + "%");
    }

    public static Specification<Course> hasDifficulties(List<Difficulty> difficulties) {
        return (root, query, cb) -> difficulties == null || difficulties.isEmpty()
                ? null
                : root.get("difficulty").in(difficulties);
    }

    public static Specification<Course> hasSkills(List<Skills> skills) {
        return (root, query, cb) -> skills == null || skills.isEmpty()
                ? null
                : root.join("skillsGained").in(skills);
    }

    public static Specification<Course> hasPriceBetween(Float min, Float max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) return cb.between(root.get("price"), min, max);
            if (min != null) return cb.greaterThanOrEqualTo(root.get("price"), min);
            return cb.lessThanOrEqualTo(root.get("price"), max);
        };
    }

    public static Specification<Course> hasHoursBetween(Float min, Float max) {
        return (root, query, cb) -> {
            if (min == null && max == null) return null;
            if (min != null && max != null) return cb.between(root.get("durationInHours"), min, max);
            if (min != null) return cb.greaterThanOrEqualTo(root.get("durationInHours"), min);
            return cb.lessThanOrEqualTo(root.get("durationInHours"), max);
        };
    }
}
