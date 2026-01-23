package com.shifterwebapp.shifter.account.user.service;

import com.shifterwebapp.shifter.auth.UserPersonalizationDto;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.enums.LoginProvider;
import com.shifterwebapp.shifter.account.user.dto.UserInfoDto;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.account.user.dto.UserDto;

import java.util.List;

public interface ImplUserService {
    List<UserDto> getAllUsers();
    UserDto getUserById(Long id, Language language);
    User getUserEntityById(Long userId);
    User getUserEntityByEmail(String email);
    String getUserEmailById(Long userId);
    Boolean getUserHasUsedFreeConsultation(String userEmail);

    Boolean existsUserByEmail(String email);

    User createInitialUser(String email, String password, LoginProvider loginProvider);
    User personalizeUser(UserPersonalizationDto userPersonalizationDto);
    void deleteUser(Long id);

    UserDto updateAttribute(Long id, List<Long> attributeIds);
    UserDto updateUser(Long id, UserInfoDto userInfoDto);
    UserDto updateMail(Long id, String newMail);
    UserDto updatePassword(Long id, String newPassHash);

    UserDto addAttributes(Long userId, List<Long> attributeIds);
    UserDto toggleFavoriteCourse(Long userId, Integer newFavoriteCourseId);
    UserDto addPoints(Long id, Integer newPointsAchieved);

    UserDto removeAttribute(Long userId, Long attributeId);
    UserDto removeAttributes(Long userId, List<Long> attributeIds);
    UserDto removePoints(Long id, Integer removePointsAchieved);

    void markUserAsUsedFreeConsultation(String userEmail);
}
