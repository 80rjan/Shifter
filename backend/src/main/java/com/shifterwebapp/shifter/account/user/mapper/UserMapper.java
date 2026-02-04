package com.shifterwebapp.shifter.account.user.mapper;

import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.account.user.dto.UserDto;
import com.shifterwebapp.shifter.account.user.dto.UserDtoAuth;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.tag.service.TagService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class UserMapper {

    @Autowired
    protected TagService tagService;

    public abstract UserDto toDto(User user, @Context Language language);

    public abstract List<UserDto> toDto(List<User> users, @Context Language language);

    public abstract UserDtoAuth toDtoAuth(User user);

    public abstract List<UserDtoAuth> toDtoAuth(List<User> users);

    @AfterMapping
    protected void applySkillsAndInterests(@MappingTarget UserDto dto,
                                           User user,
                                           @Context Language language) {
        List<String> skillsGained = tagService.getSkillsByUserId(user.getId(), language);
        List<String> interests = tagService.getTopicsByUserId(user.getId(), language);

        dto.setSkillsGained(skillsGained);
        dto.setInterests(interests);
    }
}
