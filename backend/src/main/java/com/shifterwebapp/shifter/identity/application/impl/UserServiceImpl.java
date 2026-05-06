//package com.shifterwebapp.shifter.account.services;
//
//import com.shifterwebapp.shifter.identity.web.response.UserPersonalizationDto;
//import com.shifterwebapp.shifter.identity.domain.enums.LoginProvider;
//import com.shifterwebapp.shifter.identity.web.response.PersonalizeUserReq;
//import com.shifterwebapp.shifter.identity.domain.User;
//import com.shifterwebapp.shifter.identity.web.response.UserDto;
//
//import java.util.List;
//
//public interface ImplUserService {
//    UserDto getById(Long id, LanguageCode language);
//    User getEntityById(Long userId);
//    User getUserEntityByEmail(String email);
//    String getUserEmailById(Long userId);
//    Boolean getUserHasUsedFreeConsultation(String userEmail);
//
//    Boolean existsUserByEmail(String email);
//
//    User createInitialUser(String email, String password, LoginProvider loginProvider);
//    User personalizeUser(UserPersonalizationDto userPersonalizationDto);
//    void deleteUser(Long id);
//
//    UserDto updateTags(Long id, LanguageCode language, List<Long> tagIds);
//    UserDto updateUser(Long id, Language language, PersonalizeUserReq personalizeUserReq);
//
//    void addTags(Long userId, Language language, List<Long> tagIds);
//    UserDto toggleFavoriteCourse(Long userId, Language language, Integer newFavoriteCourseId);
//    void addPoints(Long id, Integer newPointsAchieved);
//
//    void markUserAsUsedFreeConsultation(String userEmail);
//}
