//package com.shifterwebapp.shifter.account.controllers;
//
//import com.shifterwebapp.shifter.Validate;
//import com.shifterwebapp.shifter.identity.web.response.ExpertDto;
//import com.shifterwebapp.shifter.identity.application.ExpertService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.GetMapping;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestParam;
//import org.springframework.web.bind.annotation.RestController;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("${api.base.path}/experts")
//public class ExpertController {
//
//    private final Validate validate;
//    private final ExpertService expertService;
//
//    @GetMapping("/me")
//    public ResponseEntity<ExpertDto> getExpert(Authentication authentication, @RequestParam(defaultValue = "EN") Language language) {
//        Long expertId = validate.extractExpertId(authentication);
//
//        return ResponseEntity.ok(expertService.getExpertById(expertId, language));
//    }
//}
