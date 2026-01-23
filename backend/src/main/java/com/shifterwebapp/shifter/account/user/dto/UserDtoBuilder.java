package com.shifterwebapp.shifter.account.user.dto;

import com.shifterwebapp.shifter.attribute.service.AttributeService;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.account.user.mapper.UserMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
public class UserDtoBuilder {

    private final AttributeService attributeService;
    private final UserMapper userMapper;

    public UserDto getUserDto(User user, Language language) {
        UserDto dto = userMapper.toDto(user);
        List<String> skillsGained = attributeService.getSkillsByUserId(user.getId(), language);
        List<String> interests = attributeService.getTopicsByUserId(user.getId(), language);

        dto.setSkillsGained(skillsGained);
        dto.setInterests(interests);

        dto.setFavoriteCourseIds(user.getFavoriteCourseIds());

        return dto;
    }
}
