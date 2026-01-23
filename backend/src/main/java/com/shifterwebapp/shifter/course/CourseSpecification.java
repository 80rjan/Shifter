//package com.shifterwebapp.shifter.course;
//
//import com.shifterwebapp.shifter.enums.Difficulty;
//import jakarta.persistence.criteria.Join;
//import jakarta.persistence.criteria.JoinType;
//import jakarta.persistence.criteria.Predicate;
//import org.springframework.data.jpa.domain.Specification;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Objects;
//
//public class CourseSpecification {
//
//    public static Specification<Course> hasSearchLike(String search) {
//        return (root, query, cb) -> {
//            query.distinct(true);
//            Join<Course, String> skillsJoin = root.join("skillsGained", JoinType.LEFT);
//            Join<Course, String> topicsJoin = root.join("topicsCovered", JoinType.LEFT);
//
//            return cb.or(
//                    cb.like(cb.lower(root.get("title")), "%" + search.toLowerCase() + "%"),
//                    cb.like(cb.lower(root.get("descriptionShort")), "%" + search.toLowerCase() + "%"),
//                    cb.like(cb.lower(root.get("description")), "%" + search.toLowerCase() + "%"),
//                    cb.like(cb.lower(root.get("descriptionLong")), "%" + search.toLowerCase() + "%"),
//                    cb.like(cb.lower(skillsJoin.as(String.class)), "%" + search.toLowerCase().replaceAll("\\s", "_") + "%"),
//                    cb.like(cb.lower(topicsJoin.as(String.class)), "%" + search.toLowerCase().replaceAll("\\s", "_") + "%")
//            );
//        };
//    }
//
//    public static Specification<Course> hasDifficulties(List<Difficulty> difficulties) {
//        return (root, query, cb) -> root.get("difficulty").in(difficulties);
//    }
//
//    public static Specification<Course> hasSkills(List<String> skills) {
//        return (root, query, cb) -> root.join("skillsGained").in(skills);
//    }
//
//    public static Specification<Course> hasTopics(List<String> topics) {
//        return (root, query, cb) -> root.join("topicsCovered").in(topics);
//    }
//
//    public static Specification<Course> hasPricesBetween(List<String> prices) {
//        return (root, query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//
//            for (String price : prices) {
//                switch (price) {
//                    case "free":
//                        predicates.add(cb.lessThanOrEqualTo(root.get("price"), 0.0));
//                        break;
//                    case "low":
//                        predicates.add(cb.between(root.get("price"), 0.0, 20.0));
//                        break;
//                    case "medium":
//                        predicates.add(cb.between(root.get("price"), 20.0, 50.0));
//                        break;
//                    case "high":
//                        predicates.add(cb.greaterThanOrEqualTo(root.get("price"), 50.0));
//                        break;
//                }
//            }
//
//            if (predicates.isEmpty()) {
//                return null;
//            }
//
//            return cb.or(predicates.toArray(new Predicate[0]));
//        };
//    }
//
//    public static Specification<Course> hasDurationBetween(List<String> durations) {
//        return (root, query, cb) -> {
//            List<Predicate> predicates = new ArrayList<>();
//
//
//            for (String duration : durations) {
//                switch (duration) {
//                    case "extraShort":
//                        predicates.add(cb.lessThanOrEqualTo(root.get("durationMinutes"), 3.0 * 60.0));
//                        break;
//                    case "short":
//                        predicates.add(cb.between(root.get("durationMinutes"), 3.0 * 60.0, 6.0 * 60.0));
//                        break;
//                    case "medium":
//                        predicates.add(cb.between(root.get("durationMinutes"), 6.0 * 60.0, 10.0 * 60.0));
//                        break;
//                    case "long":
//                        predicates.add(cb.greaterThanOrEqualTo(root.get("durationMinutes"), 10.0 * 60.0));
//                        break;
//                }
//            }
//
//            if (predicates.isEmpty()) {
//                return null;
//            }
//
//            return cb.or(predicates.toArray(new Predicate[0]));
//        };
//    }
//
//    public static Specification<Course> idNotIn(List<Long> enrolledCourseIds) {
//        return (root, query, cb) -> root.get("id").in(enrolledCourseIds).not();
//    }
//}
