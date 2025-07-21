package com.shifterwebapp.shifter.user.service;

import com.shifterwebapp.shifter.auth.RegisterDto;
import com.shifterwebapp.shifter.user.UserInfoDto;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.user.User;
import com.shifterwebapp.shifter.user.UserDto;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;

import java.util.List;

public interface ImplUserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id);
    User getUserEntityById(Long userId);
    User getUserEntityByEmail(String email);
    Boolean existsUserByEmail(String email);

    User createUser(RegisterDto registerDto);
    void deleteUser(Long id);

    UserDto updateUser(Long id, UserInfoDto userInfoDto);
    UserDto updateInterests(Long id, List<Interests> interests);
    UserDto updateDesiredSkills(Long id, List<Skills> desiredSkills);
    UserDto updateMail(Long id, String newMail);
    UserDto updatePassword(Long id, String newPassHash);

    UserDto addSkill(Long id, Skills newSkill);
    UserDto addSkills(Long id, List<Skills> newSkills);
    UserDto toggleFavoriteCourse(Long userId, Integer newFavoriteCourseId);
    UserDto addPoints(Long id, Integer newPointsAchieved);
    UserDto addPayment(Long id, Payment newPayment);

    UserDto removeDesiredSkill(Long id, Skills removeDesiredSkill);
    UserDto removeDesiredSkills(Long id, List<Skills> removeDesiredSkills);
    UserDto removePoints(Long id, Integer removePointsAchieved);
    UserDto removePayment(Long id, Payment removePayment);

}
