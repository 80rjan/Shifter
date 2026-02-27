package com.shifterwebapp.shifter.account.user.service;

import com.shifterwebapp.shifter.auth.UserPersonalizationDto;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.enums.LoginProvider;
import com.shifterwebapp.shifter.account.user.dto.PersonalizeUserReq;
import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.account.user.dto.UserDto;

import java.util.List;

public interface ImplUserService {
    UserDto getById(Long id, Language language);
    User getEntityById(Long userId);
    User getUserEntityByEmail(String email);
    String getUserEmailById(Long userId);
    Boolean getUserHasUsedFreeConsultation(String userEmail);

    Boolean existsUserByEmail(String email);

    User createInitialUser(String email, String password, LoginProvider loginProvider);
    User personalizeUser(UserPersonalizationDto userPersonalizationDto);
    void deleteUser(Long id);

    UserDto updateTags(Long id, Language language, List<Long> tagIds);
    UserDto updateUser(Long id, Language language, PersonalizeUserReq personalizeUserReq);

    void addTags(Long userId, Language language, List<Long> tagIds);
    UserDto toggleFavoriteCourse(Long userId, Language language, Integer newFavoriteCourseId);
    void addPoints(Long id, Integer newPointsAchieved);

    void markUserAsUsedFreeConsultation(String userEmail);
}
