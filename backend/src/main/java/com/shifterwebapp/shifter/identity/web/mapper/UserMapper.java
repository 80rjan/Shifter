//package com.shifterwebapp.shifter.account.mappers;
//
//import com.shifterwebapp.shifter.identity.domain.User;
//import com.shifterwebapp.shifter.identity.web.response.UserDto;
//import com.shifterwebapp.shifter.identity.web.response.AuthenticatedUserResponse;
//import com.shifterwebapp.shifter.catalog.application.TagService;
//import org.mapstruct.*;
//import org.springframework.beans.factory.annotation.Autowired;
//
//import java.util.List;
//
//@Mapper(componentModel = "spring")
//public abstract class UserMapper {
//
//    @Autowired
//    protected TagService tagService;
//
//    public abstract UserDto toDto(User user, @Context LanguageCode language);
//
//    public abstract List<UserDto> toDto(List<User> users, @Context LanguageCode language);
//
//    public abstract AuthenticatedUserResponse toDtoAuth(User user);
//
//    public abstract List<AuthenticatedUserResponse> toDtoAuth(List<User> users);
//
//    @AfterMapping
//    protected void applySkillsAndInterests(@MappingTarget UserDto dto,
//                                           User user,
//                                           @Context LanguageCode language) {
//        List<String> skillsGained = tagService.getSkillsByUserId(user.getId(), language);
//        List<String> interests = tagService.getTopicsByUserId(user.getId(), language);
//
//        dto.setSkillsGained(skillsGained);
//        dto.setInterests(interests);
//    }
//}
