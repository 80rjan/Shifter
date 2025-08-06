package com.shifterwebapp.shifter.user;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.auth.CustomAuthDetails;
import com.shifterwebapp.shifter.exception.ErrorResponse;
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

    @PutMapping("/favorite-course/{courseId}")
    public ResponseEntity<?> toggleFavoriteCourse(@PathVariable Integer courseId, Authentication authentication) {
        validate.validateUserIsAuthenticated(authentication);
        Object detailsObj = authentication.getDetails();
        if (!(detailsObj instanceof CustomAuthDetails details)) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid authentication details"));
        }
        Long userId = details.getUserId();

        UserDto userDto = userService.toggleFavoriteCourse(userId, courseId);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/update/info")
    public ResponseEntity<?> updateUser(Authentication authentication, @RequestBody UserInfoDto userInfoDto) {
        validate.validateUserIsAuthenticated(authentication);
        Object detailsObj = authentication.getDetails();
        if (!(detailsObj instanceof CustomAuthDetails details)) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid authentication details"));
        }
        Long userId = details.getUserId();

        UserDto userDto = userService.updateUser(userId, userInfoDto);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/update/interests")
    public ResponseEntity<?> updateInterests(Authentication authentication, @RequestBody List<String> interests) {
        validate.validateUserIsAuthenticated(authentication);
        Object detailsObj = authentication.getDetails();
        if (!(detailsObj instanceof CustomAuthDetails details)) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid authentication details"));
        }
        Long userId = details.getUserId();

        UserDto userDto = userService.updateInterests(userId, interests);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/update/desired-skills")
    public ResponseEntity<?> updateDesiredSkills(Authentication authentication, @RequestBody List<String> desiredSkills) {
        validate.validateUserIsAuthenticated(authentication);
        Object detailsObj = authentication.getDetails();
        if (!(detailsObj instanceof CustomAuthDetails details)) {
            return ResponseEntity.badRequest().body(new ErrorResponse("Invalid authentication details"));
        }
        Long userId = details.getUserId();

        UserDto userDto = userService.updateDesiredSkills(userId, desiredSkills);
        return ResponseEntity.ok(userDto);
    }
}
