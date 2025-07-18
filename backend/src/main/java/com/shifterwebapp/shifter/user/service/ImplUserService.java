package com.shifterwebapp.shifter.user.service;

import com.shifterwebapp.shifter.auth.RegisterDto;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.user.User;
import com.shifterwebapp.shifter.user.UserDto;
import com.shifterwebapp.shifter.enums.CompanyType;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;
import org.springframework.security.core.Authentication;

import java.util.List;

public interface ImplUserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    User getUserEntityById(Long userId);
    User getUserEntityByEmail(String email);
    Boolean existsUserByEmail(String email);

    User createUser(RegisterDto registerDto);
    void deleteUser(Long id);

    UserDto updateName(Long id, String newName);
    UserDto updateMail(Long id, String newMail);
    UserDto updatePassword(Long id, String newPassHash);
    UserDto updateWorkPosition(Long id, String newWorkPosition);
    UserDto updateCompanyType(Long id, CompanyType newCompanyType);

    UserDto addInterest(Long id, Interests newInterest);
    UserDto addSkill(Long id, Skills newSkill);
    UserDto addSkills(Long id, List<Skills> newSkills);
    UserDto addSkillGap(Long id, Skills newSkillGap);
    UserDto toggleFavoriteCourse(Long userId, Integer newFavoriteCourseId);
    UserDto addPoints(Long id, Integer newPointsAchieved);
    UserDto addPayment(Long id, Payment newPayment);

    UserDto removeInterest(Long id, Interests removeInterest);
    UserDto removeSkill(Long id, Skills removeSkill);
    UserDto removeSkillGap(Long id, Skills removeSkillGap);
    UserDto removeSkillGaps(Long id, List<Skills> removeSkillGaps);
    UserDto removeFavoriteCourse(Long id, Integer removeFavoriteCourseId);
    UserDto removePoints(Long id, Integer removePointsAchieved);
    UserDto removePayment(Long id, Payment removePayment);

}
