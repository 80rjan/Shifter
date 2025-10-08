package com.shifterwebapp.shifter.user.service;

import com.shifterwebapp.shifter.auth.UserPersonalizationDto;
import com.shifterwebapp.shifter.user.UserInfoDto;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.user.User;
import com.shifterwebapp.shifter.user.UserDto;

import java.util.List;

public interface ImplUserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    User getUserEntityById(Long userId);
    User getUserEntityByEmail(String email);
    String getUserEmailById(Long userId);
    Boolean getUserHasUsedFreeConsultation(String userEmail);

    Boolean existsUserByEmail(String email);

    User createInitialUser(String email, String password);
    User createUser(UserPersonalizationDto userPersonalizationDto);
    void deleteUser(Long id);

    UserDto updateUser(Long id, UserInfoDto userInfoDto);
    UserDto updateInterests(Long id, List<String> interests);
    UserDto updateDesiredSkills(Long id, List<String> desiredSkills);
    UserDto updateMail(Long id, String newMail);
    UserDto updatePassword(Long id, String newPassHash);

    UserDto addSkill(Long id, String newSkill);
    UserDto addSkills(Long id, List<String> newSkills);
    UserDto toggleFavoriteCourse(Long userId, Integer newFavoriteCourseId);
    UserDto addPoints(Long id, Integer newPointsAchieved);
    UserDto addPayment(Long id, Payment newPayment);

    UserDto removeDesiredSkill(Long id, String removeDesiredSkill);
    UserDto removeDesiredSkills(Long id, List<String> removeDesiredSkills);
    UserDto removePoints(Long id, Integer removePointsAchieved);
    UserDto removePayment(Long id, Payment removePayment);

    void markUserAsUsedFreeConsultation(String userEmail);
}
