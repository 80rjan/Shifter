package com.shifterwebapp.shifter.user;

import com.shifterwebapp.shifter.enums.CompanyType;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;
import com.shifterwebapp.shifter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/user")
public class UserController {

    private final UserService userService;

    @GetMapping("/{userId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long userId) {
        UserDto userDto = userService.getUserById(userId);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{userId}/name")
    public ResponseEntity<?> updateName(@PathVariable Long userId, @RequestParam String newName) {
        UserDto userDto = userService.updateName(userId, newName);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}/mail")
    public ResponseEntity<?> updateMail(@PathVariable Long userId, @RequestParam String newMail) {
        UserDto userDto = userService.updateMail(userId, newMail);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}/password")
    public ResponseEntity<?> updatePassword(@PathVariable Long userId, @RequestParam String newPassword) {
        UserDto userDto = userService.updatePassword(userId, newPassword);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}/work-position")
    public ResponseEntity<?> updateWorkPosition(@PathVariable Long userId, @RequestParam String newWorkPosition) {
        UserDto userDto = userService.updateWorkPosition(userId, newWorkPosition);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}/company-type")
    public ResponseEntity<?> updateCompanyType(@PathVariable Long userId, @RequestParam CompanyType newCompanyType) {
        UserDto userDto = userService.updateCompanyType(userId, newCompanyType);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}/add/interest")
    public ResponseEntity<?> addInterest(@PathVariable Long userId, @RequestParam Interests newInterest) {
        UserDto userDto = userService.addInterest(userId, newInterest);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}/add/skill")
    public ResponseEntity<?> addSkill(@PathVariable Long userId, @RequestParam Skills newSkill) {
        UserDto userDto = userService.addSkill(userId, newSkill);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}/add/skill-gap")
    public ResponseEntity<?> addSkillGap(@PathVariable Long userId, @RequestParam Skills newSkillGap) {
        UserDto userDto = userService.addSkillGap(userId, newSkillGap);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/favorite-course/{courseId}")
    public ResponseEntity<?> toggleFavoriteCourse(@PathVariable Integer courseId, Authentication authentication) {
        System.out.println("im here");
        UserDto userDto = userService.toggleFavoriteCourse(authentication, courseId);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}/remove/interest")
    public ResponseEntity<?> removeInterest(@PathVariable Long userId, @RequestParam Interests oldInterest) {
        UserDto userDto = userService.removeInterest(userId, oldInterest);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}/remove/skill")
    public ResponseEntity<?> removeSkill(@PathVariable Long userId, @RequestParam Skills oldSkill) {
        UserDto userDto = userService.removeSkill(userId, oldSkill);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}/remove/skill-gap")
    public ResponseEntity<?> removeSkillGap(@PathVariable Long userId, @RequestParam Skills oldSkillGap) {
        UserDto userDto = userService.removeSkillGap(userId, oldSkillGap);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{userId}/remove/favorite-course")
    public ResponseEntity<?> removeFavoriteCourse(@PathVariable Long userId, @RequestParam Integer oldFavoriteCourse) {
        UserDto userDto = userService.removeFavoriteCourse(userId, oldFavoriteCourse);
        return ResponseEntity.ok(userDto);
    }
}
