//package com.shifterwebapp.shifter.account.controllers;
//
//import com.shifterwebapp.shifter.Validate;
//import com.shifterwebapp.shifter.identity.web.response.UserDto;
//import com.shifterwebapp.shifter.identity.web.response.PersonalizeUserReq;
//import com.shifterwebapp.shifter.identity.application.UserService;
//import lombok.RequiredArgsConstructor;
//import org.springframework.http.ResponseEntity;
//import org.springframework.security.core.Authentication;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RequiredArgsConstructor
//@RestController
//@RequestMapping("${api.base.path}/users")
//public class UserController {
//
//    private final UserService userService;
//    private final Validate validate;
//
//    @GetMapping("/me")
//    public ResponseEntity<?> getUser(Authentication authentication, @RequestParam(defaultValue = "EN") LanguageCode language) {
//        Long userId = validate.extractUserId(authentication);
//
//        return ResponseEntity.ok(userService.getById(userId, language));
//    }
//
//    @PutMapping("/favorite-course/{courseId}")
//    public ResponseEntity<?> toggleFavoriteCourse(@PathVariable Integer courseId, Authentication authentication, @RequestParam(defaultValue = "EN") LanguageCode language) {
//        Long userId = validate.extractUserId(authentication);
//
//        UserDto userDto = userService.toggleFavoriteCourse(userId, language, courseId);
//        return ResponseEntity.ok(userDto);
//    }
//
//    @PutMapping("/update/info")
//    public ResponseEntity<?> updateUser(Authentication authentication, @RequestParam(defaultValue = "EN") LanguageCode language, @RequestBody PersonalizeUserReq personalizeUserReq) {
//        Long userId = validate.extractUserId(authentication);
//
//        UserDto userDto = userService.updateUser(userId, language, personalizeUserReq);
//        return ResponseEntity.ok(userDto);
//    }
//
//    @PutMapping("/update/tags")
//    public ResponseEntity<?> updateTags(Authentication authentication, @RequestParam(defaultValue = "EN") LanguageCode language, @RequestBody List<Long> tagIdList) {
//        Long userId = validate.extractUserId(authentication);
//
//        UserDto userDto = userService.updateTags(userId, language, tagIdList);
//        return ResponseEntity.ok(userDto);
//    }
//}
