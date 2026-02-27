package com.shifterwebapp.shifter.account.expert.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.account.expert.Expert;
import com.shifterwebapp.shifter.account.expert.dto.ExpertDto;
import com.shifterwebapp.shifter.account.expert.mapper.ExpertMapper;
import com.shifterwebapp.shifter.account.expert.repository.ExpertRepository;
import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.account.user.dto.UserDto;
import com.shifterwebapp.shifter.enums.Language;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ExpertService implements ImplExpertService {

    private final Validate validate;
    private final ExpertRepository expertRepository;
    private final ExpertMapper expertMapper;

    @Transactional(readOnly = true)
    @Override
    public ExpertDto getExpertById(Long expertId, Language language) {
        validate.validateUserExists(expertId);
        Expert expert = expertRepository.findById(expertId).orElseThrow();
        return expertMapper.toDto(expert, language);
    }
}
