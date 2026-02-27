package com.shifterwebapp.shifter.account.expert.service;

import com.shifterwebapp.shifter.account.expert.dto.ExpertDto;
import com.shifterwebapp.shifter.enums.Language;

public interface ImplExpertService {
    ExpertDto getExpertById(Long expertId, Language language);
}
