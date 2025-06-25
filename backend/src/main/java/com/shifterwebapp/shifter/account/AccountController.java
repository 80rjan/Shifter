package com.shifterwebapp.shifter.account;

import com.shifterwebapp.shifter.enums.CompanyType;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;
import com.shifterwebapp.shifter.account.service.AccountService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequiredArgsConstructor
@RestController
@RequestMapping("${api.base.path}/account")
public class AccountController {

    private final AccountService accountService;

    @GetMapping("/{accountId}")
    public ResponseEntity<AccountDto> getAccount(@PathVariable Long accountId) {
        AccountDto accountDto = accountService.getAccountById(accountId);
        return ResponseEntity.ok(accountDto);
    }

    @DeleteMapping("/{accountId}")
    public ResponseEntity<Void> deleteAccount(@PathVariable Long accountId) {
        accountService.deleteAccount(accountId);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{accountId}/name")
    public ResponseEntity<?> updateName(@PathVariable Long accountId, @RequestParam String newName) {
        AccountDto accountDto = accountService.updateName(accountId, newName);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{accountId}/mail")
    public ResponseEntity<?> updateMail(@PathVariable Long accountId, @RequestParam String newMail) {
        AccountDto accountDto = accountService.updateMail(accountId, newMail);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{accountId}/password")
    public ResponseEntity<?> updatePassword(@PathVariable Long accountId, @RequestParam String newPassword) {
        AccountDto accountDto = accountService.updatePassword(accountId, newPassword);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{accountId}/work-position")
    public ResponseEntity<?> updateWorkPosition(@PathVariable Long accountId, @RequestParam String newWorkPosition) {
        AccountDto accountDto = accountService.updateWorkPosition(accountId, newWorkPosition);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{accountId}/company-type")
    public ResponseEntity<?> updateCompanyType(@PathVariable Long accountId, @RequestParam CompanyType newCompanyType) {
        AccountDto accountDto = accountService.updateCompanyType(accountId, newCompanyType);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{accountId}/add/interest")
    public ResponseEntity<?> addInterest(@PathVariable Long accountId, @RequestParam Interests newInterest) {
        AccountDto accountDto = accountService.addInterest(accountId, newInterest);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{accountId}/add/skill")
    public ResponseEntity<?> addSkill(@PathVariable Long accountId, @RequestParam Skills newSkill) {
        AccountDto accountDto = accountService.addSkill(accountId, newSkill);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{accountId}/add/skill-gap")
    public ResponseEntity<?> addSkillGap(@PathVariable Long accountId, @RequestParam Skills newSkillGap) {
        AccountDto accountDto = accountService.addSkillGap(accountId, newSkillGap);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{accountId}/add/favorite-course")
    public ResponseEntity<?> addFavoriteCourse(@PathVariable Long accountId, @RequestParam Integer newFavoriteCourse) {
        AccountDto accountDto = accountService.addFavoriteCourse(accountId, newFavoriteCourse);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{accountId}/remove/interest")
    public ResponseEntity<?> removeInterest(@PathVariable Long accountId, @RequestParam Interests oldInterest) {
        AccountDto accountDto = accountService.removeInterest(accountId, oldInterest);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{accountId}/remove/skill")
    public ResponseEntity<?> removeSkill(@PathVariable Long accountId, @RequestParam Skills oldSkill) {
        AccountDto accountDto = accountService.removeSkill(accountId, oldSkill);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{accountId}/remove/skill-gap")
    public ResponseEntity<?> removeSkillGap(@PathVariable Long accountId, @RequestParam Skills oldSkillGap) {
        AccountDto accountDto = accountService.removeSkillGap(accountId, oldSkillGap);
        return ResponseEntity.ok(accountDto);
    }

    @PutMapping("/{accountId}/remove/favorite-course")
    public ResponseEntity<?> removeFavoriteCourse(@PathVariable Long accountId, @RequestParam Integer oldFavoriteCourse) {
        AccountDto accountDto = accountService.removeFavoriteCourse(accountId, oldFavoriteCourse);
        return ResponseEntity.ok(accountDto);
    }
}
