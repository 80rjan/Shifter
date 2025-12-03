package com.shifterwebapp.shifter.user;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.auth.CustomAuthDetails;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.exception.ErrorResponse;
import com.shifterwebapp.shifter.user.dto.UserDto;
import com.shifterwebapp.shifter.user.dto.UserInfoDto;
import com.shifterwebapp.shifter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/users")
public class UserController {

    private final UserService userService;
    private final Validate validate;

    @GetMapping("/me")
    public ResponseEntity<?> getUser(Authentication authentication, @RequestParam(defaultValue = "EN") Language language) {
        Long userId = validate.extractUserId(authentication);

        return ResponseEntity.ok(userService.getUserById(userId, language));
    }

    @PutMapping("/favorite-course/{courseId}")
    public ResponseEntity<?> toggleFavoriteCourse(@PathVariable Integer courseId, Authentication authentication) {
        Long userId = validate.extractUserId(authentication);

        UserDto userDto = userService.toggleFavoriteCourse(userId, courseId);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/update/info")
    public ResponseEntity<?> updateUser(Authentication authentication, @RequestBody UserInfoDto userInfoDto) {
        Long userId = validate.extractUserId(authentication);

        UserDto userDto = userService.updateUser(userId, userInfoDto);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/update/attributes")
    public ResponseEntity<?> updateAttributes(Authentication authentication, @RequestBody List<Long> attributeIdList) {
        Long userId = validate.extractUserId(authentication);

        UserDto userDto = userService.updateAttribute(userId, attributeIdList);
        return ResponseEntity.ok(userDto);
    }
}
