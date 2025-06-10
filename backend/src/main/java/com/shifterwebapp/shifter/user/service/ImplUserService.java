package com.shifterwebapp.shifter.user.service;

import com.shifterwebapp.shifter.course.CourseDto;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.review.ReviewDto;
import com.shifterwebapp.shifter.user.UserDto;
import com.shifterwebapp.shifter.user.enums.CompanyType;
import com.shifterwebapp.shifter.user.enums.Interests;
import com.shifterwebapp.shifter.user.enums.Skills;

import java.util.List;

public interface ImplUserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);

    List<CourseDto> getAllBoughtCourses(Long id);
    CourseDto getBoughtCourse(Long userId, Long courseId);
    List<ReviewDto> getAllReviews(Long id);
    ReviewDto getReview(Long userId, Long reviewId);

    UserDto updateName(Long id, String newName);
    UserDto updateMail(Long id, String newMail);
    UserDto updatePass(Long id, String newPassHash);
    UserDto updateWorkPosition(Long id, String newWorkPosition);
    UserDto updateCompanyType(Long id, CompanyType newCompanyType);

    UserDto addInterest(Long id, Interests newInterest);
    UserDto addSkill(Long id, Skills newSkill);
    UserDto addSkillGap(Long id, Skills newSkillGap);
    UserDto addFavoriteCourse(Long id, Interests newFavoriteCourseId);
    UserDto addPoints(Long id, Integer newPointsAchieved);
    UserDto addPayment(Long id, Payment newPayment);

    UserDto removeInterest(Long id, Interests removeInterest);
    UserDto removeSkill(Long id, Skills removeSkill);
    UserDto removeSkillGap(Long id, Skills removeSkillGap);
    UserDto removeFavoriteCourse(Long id, Interests removeFavoriteCourseId);
    UserDto removePoints(Long id, Integer removePointsAchieved);
    UserDto removePayment(Long id, Payment removePayment);

}
