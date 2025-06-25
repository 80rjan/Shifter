package com.shifterwebapp.shifter.account.service;

import com.shifterwebapp.shifter.auth.RegisterDto;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.account.AccountDto;
import com.shifterwebapp.shifter.enums.CompanyType;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;

import java.util.List;

public interface ImplAccountService {
    List<AccountDto> getAllAccounts();
    AccountDto getAccountById(Long id);

    void createAccount(RegisterDto registerDto);
    void deleteAccount(Long id);

    AccountDto updateName(Long id, String newName);
    AccountDto updateMail(Long id, String newMail);
    AccountDto updatePassword(Long id, String newPassHash);
    AccountDto updateWorkPosition(Long id, String newWorkPosition);
    AccountDto updateCompanyType(Long id, CompanyType newCompanyType);

    AccountDto addInterest(Long id, Interests newInterest);
    AccountDto addSkill(Long id, Skills newSkill);
    AccountDto addSkills(Long id, List<Skills> newSkills);
    AccountDto addSkillGap(Long id, Skills newSkillGap);
    AccountDto addFavoriteCourse(Long id, Integer newFavoriteCourseId);
    AccountDto addPoints(Long id, Integer newPointsAchieved);
    AccountDto addPayment(Long id, Payment newPayment);

    AccountDto removeInterest(Long id, Interests removeInterest);
    AccountDto removeSkill(Long id, Skills removeSkill);
    AccountDto removeSkillGap(Long id, Skills removeSkillGap);
    AccountDto removeSkillGaps(Long id, List<Skills> removeSkillGaps);
    AccountDto removeFavoriteCourse(Long id, Integer removeFavoriteCourseId);
    AccountDto removePoints(Long id, Integer removePointsAchieved);
    AccountDto removePayment(Long id, Payment removePayment);

}
