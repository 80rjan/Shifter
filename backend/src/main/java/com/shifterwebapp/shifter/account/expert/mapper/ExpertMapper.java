package com.shifterwebapp.shifter.account.expert.mapper;

import com.shifterwebapp.shifter.account.expert.Expert;
import com.shifterwebapp.shifter.account.expert.dto.ExpertDto;
import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.account.user.dto.UserDto;
import com.shifterwebapp.shifter.account.user.dto.UserDtoAuth;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.tag.service.TagService;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

@Mapper(componentModel = "spring")
public abstract class ExpertMapper {

    public abstract ExpertDto toDto(Expert expert, @Context Language language);

    public abstract List<ExpertDto> toDto(List<Expert> experts, @Context Language language);
}
