package com.shifterwebapp.shifter.account.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.auth.RegisterDto;
import com.shifterwebapp.shifter.enums.CompanyType;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.account.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AccountService implements ImplAccountService {

    private final AccountRepository accountRepository;
    private final AccountMapper accountMapper;
    private final Validate validate;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<AccountDto> getAllAccounts() {
        List<Account> accounts = accountRepository.findAll();
        return accountMapper.toDto(accounts);
    }

    @Override
    public AccountDto getAccountById(Long accountId) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        return accountMapper.toDto(account);
    }

    @Override
    public void createAccount(RegisterDto registerDto) {
        if (accountRepository.existsAccountByEmail(registerDto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        Account account = Account.builder()
                .name(registerDto.getName())
                .email(registerDto.getEmail())
                .passwordHash(passwordEncoder.encode(registerDto.getPassword()))
                .isAdmin(false)
                .companyType(registerDto.getCompanyType())
                .workPosition(registerDto.getWorkPosition())
                .interests(registerDto.getInterests())
                .skills(registerDto.getSkills())
                .skillGap(registerDto.getSkillGap())
                .points(0)
                .favoriteCourses(List.of())
                .build();

        accountRepository.save(account);
    }

    @Override
    public void deleteAccount(Long accountId) {
        validate.validateAccountExists(accountId);
        accountRepository.deleteById(accountId);
    }

    @Override
    public AccountDto updateName(Long accountId, String newName) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        account.setName(newName);
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto updateMail(Long accountId, String newMail) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        account.setEmail(newMail);
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto updatePassword(Long accountId, String newPass) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        account.setPasswordHash(passwordEncoder.encode(newPass));
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto updateWorkPosition(Long accountId, String newWorkPosition) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        account.setWorkPosition(newWorkPosition);
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto updateCompanyType(Long accountId, CompanyType newCompanyType) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        account.setCompanyType(newCompanyType);
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto addInterest(Long accountId, Interests newInterest) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        if (!account.getInterests().contains(newInterest)) {
            account.getInterests().add(newInterest);
        }
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto addSkill(Long accountId, Skills newSkill) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        if (!account.getSkills().contains(newSkill)) {
            account.getSkills().add(newSkill);
        }
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto addSkills(Long accountId, List<Skills> newSkills) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        for (Skills skill : newSkills) {
            if (!account.getSkills().contains(skill)) {
                account.getSkills().add(skill);
            }
        }
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto addSkillGap(Long accountId, Skills newSkillGap) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        if (!account.getSkillGap().contains(newSkillGap)) {
            account.getSkillGap().add(newSkillGap);
        }
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto addFavoriteCourse(Long accountId, Integer newFavoriteCourseId) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        if (!account.getFavoriteCourses().contains(newFavoriteCourseId)) {
            account.getFavoriteCourses().add(newFavoriteCourseId);
        }
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto addPoints(Long accountId, Integer newPointsAchieved) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        Integer newPoints = account.getPoints() + newPointsAchieved;
        account.setPoints(newPoints);
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto addPayment(Long accountId, Payment newPayment) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        if (!account.getPayments().contains(newPayment)) {
            account.getPayments().add(newPayment);
        }
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto removeInterest(Long accountId, Interests removeInterest) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        if (!account.getInterests().contains(removeInterest)) {
            account.getInterests().remove(removeInterest);
        }
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto removeSkill(Long accountId, Skills removeSkill) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        if (!account.getSkills().contains(removeSkill)) {
            account.getSkills().remove(removeSkill);
        }
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto removeSkillGap(Long accountId, Skills removeSkillGap) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        if (!account.getSkillGap().contains(removeSkillGap)) {
            account.getSkillGap().remove(removeSkillGap);
        }
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto removeSkillGaps(Long accountId, List<Skills> removeSkillGaps) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        for (Skills skill : removeSkillGaps) {
            if (!account.getSkillGap().contains(skill)) {
                account.getSkillGap().remove(skill);
            }
        }
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto removeFavoriteCourse(Long accountId, Integer removeFavoriteCourseId) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        if (!account.getFavoriteCourses().contains(removeFavoriteCourseId)) {
            account.getFavoriteCourses().remove(removeFavoriteCourseId);
        }
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto removePoints(Long accountId, Integer removePointsAchieved) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        Integer newPoints = account.getPoints() - removePointsAchieved;
        account.setPoints(newPoints);
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }

    @Override
    public AccountDto removePayment(Long accountId, Payment removePayment) {
        validate.validateAccountExists(accountId);
        Account account = accountRepository.findById(accountId).orElseThrow();
        if (!account.getPayments().contains(removePayment)) {
            account.getPayments().remove(removePayment);
        }
        accountRepository.save(account);
        return accountMapper.toDto(account);
    }
}
