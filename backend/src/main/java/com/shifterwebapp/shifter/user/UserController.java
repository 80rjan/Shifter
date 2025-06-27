package com.shifterwebapp.shifter.user;

import com.shifterwebapp.shifter.enums.CompanyType;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;
import com.shifterwebapp.shifter.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/account")
public class UserController {

    private final UserService userService;

    @GetMapping("/{accountId}")
    public ResponseEntity<UserDto> getUser(@PathVariable Long accountId) {
        UserDto userDto = userService.getUserById(accountId);
        return ResponseEntity.ok(userDto);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long accountId) {
        userService.deleteUser(accountId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{accountId}/name")
    public ResponseEntity<?> updateName(@PathVariable Long accountId, @RequestParam String newName) {
        UserDto userDto = userService.updateName(accountId, newName);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{accountId}/mail")
    public ResponseEntity<?> updateMail(@PathVariable Long accountId, @RequestParam String newMail) {
        UserDto userDto = userService.updateMail(accountId, newMail);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{accountId}/password")
    public ResponseEntity<?> updatePassword(@PathVariable Long accountId, @RequestParam String newPassword) {
        UserDto userDto = userService.updatePassword(accountId, newPassword);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{accountId}/work-position")
    public ResponseEntity<?> updateWorkPosition(@PathVariable Long accountId, @RequestParam String newWorkPosition) {
        UserDto userDto = userService.updateWorkPosition(accountId, newWorkPosition);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{accountId}/company-type")
    public ResponseEntity<?> updateCompanyType(@PathVariable Long accountId, @RequestParam CompanyType newCompanyType) {
        UserDto userDto = userService.updateCompanyType(accountId, newCompanyType);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{accountId}/add/interest")
    public ResponseEntity<?> addInterest(@PathVariable Long accountId, @RequestParam Interests newInterest) {
        UserDto userDto = userService.addInterest(accountId, newInterest);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{accountId}/add/skill")
    public ResponseEntity<?> addSkill(@PathVariable Long accountId, @RequestParam Skills newSkill) {
        UserDto userDto = userService.addSkill(accountId, newSkill);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{accountId}/add/skill-gap")
    public ResponseEntity<?> addSkillGap(@PathVariable Long accountId, @RequestParam Skills newSkillGap) {
        UserDto userDto = userService.addSkillGap(accountId, newSkillGap);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{accountId}/add/favorite-course")
    public ResponseEntity<?> addFavoriteCourse(@PathVariable Long accountId, @RequestParam Integer newFavoriteCourse) {
        UserDto userDto = userService.addFavoriteCourse(accountId, newFavoriteCourse);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{accountId}/remove/interest")
    public ResponseEntity<?> removeInterest(@PathVariable Long accountId, @RequestParam Interests oldInterest) {
        UserDto userDto = userService.removeInterest(accountId, oldInterest);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{accountId}/remove/skill")
    public ResponseEntity<?> removeSkill(@PathVariable Long accountId, @RequestParam Skills oldSkill) {
        UserDto userDto = userService.removeSkill(accountId, oldSkill);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{accountId}/remove/skill-gap")
    public ResponseEntity<?> removeSkillGap(@PathVariable Long accountId, @RequestParam Skills oldSkillGap) {
        UserDto userDto = userService.removeSkillGap(accountId, oldSkillGap);
        return ResponseEntity.ok(userDto);
    }

    @PutMapping("/{accountId}/remove/favorite-course")
    public ResponseEntity<?> removeFavoriteCourse(@PathVariable Long accountId, @RequestParam Integer oldFavoriteCourse) {
        UserDto userDto = userService.removeFavoriteCourse(accountId, oldFavoriteCourse);
        return ResponseEntity.ok(userDto);
    }
}
