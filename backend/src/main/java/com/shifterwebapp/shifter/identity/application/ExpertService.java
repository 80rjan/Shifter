//package com.shifterwebapp.shifter.account.services;
//
//import com.shifterwebapp.shifter.Validate;
//import com.shifterwebapp.shifter.identity.domain.Expert;
//import com.shifterwebapp.shifter.identity.web.response.ExpertDto;
//import com.shifterwebapp.shifter.identity.web.mapper.ExpertMapper;
//import com.shifterwebapp.shifter.identity.infrastructure.ExpertRepository;
//import lombok.RequiredArgsConstructor;
//import org.springframework.stereotype.Service;
//import org.springframework.transaction.annotation.Transactional;
//
//@Service
//@RequiredArgsConstructor
//public class ExpertService implements ImplExpertService {
//
//    private final Validate validate;
//    private final ExpertRepository expertRepository;
//    private final ExpertMapper expertMapper;
//
//    @Transactional(readOnly = true)
//    @Override
//    public ExpertDto getExpertById(Long expertId, Language language) {
//        validate.validateUserExists(expertId);
//        Expert expert = expertRepository.findById(expertId).orElseThrow();
//        return expertMapper.toDto(expert, language);
//    }
//}
