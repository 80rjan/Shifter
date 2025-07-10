package com.shifterwebapp.shifter.user.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.auth.RegisterDto;
import com.shifterwebapp.shifter.enums.CompanyType;
import com.shifterwebapp.shifter.enums.Interests;
import com.shifterwebapp.shifter.enums.Skills;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.user.*;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService implements ImplUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final Validate validate;
    private final PasswordEncoder passwordEncoder;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDto(users);
    }

    @Override
    public UserDto getUserById(Long accountId) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        return userMapper.toDto(user);
    }

    @Override
    public User getUserByEmail(String email) {
        validate.validateUserExists(email);
        User user = userRepository.findByEmail(email).orElseThrow();
        return user;
    }

    @Override
    public Boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Override
    public User createUser(RegisterDto registerDto) {
        if (userRepository.existsUserByEmail(registerDto.getEmail())) {
            throw new RuntimeException("Email already in use");
        }

        User user = User.builder()
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

        return userRepository.save(user);
    }

    @Override
    public void deleteUser(Long accountId) {
        validate.validateUserExists(accountId);
        userRepository.deleteById(accountId);
    }

    @Override
    public UserDto updateName(Long accountId, String newName) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        user.setName(newName);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateMail(Long accountId, String newMail) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        user.setEmail(newMail);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updatePassword(Long accountId, String newPass) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        user.setPasswordHash(passwordEncoder.encode(newPass));
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateWorkPosition(Long accountId, String newWorkPosition) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        user.setWorkPosition(newWorkPosition);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateCompanyType(Long accountId, CompanyType newCompanyType) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        user.setCompanyType(newCompanyType);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto addInterest(Long accountId, Interests newInterest) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        if (!user.getInterests().contains(newInterest)) {
            user.getInterests().add(newInterest);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto addSkill(Long accountId, Skills newSkill) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        if (!user.getSkills().contains(newSkill)) {
            user.getSkills().add(newSkill);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto addSkills(Long accountId, List<Skills> newSkills) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        for (Skills skill : newSkills) {
            if (!user.getSkills().contains(skill)) {
                user.getSkills().add(skill);
            }
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto addSkillGap(Long accountId, Skills newSkillGap) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        if (!user.getSkillGap().contains(newSkillGap)) {
            user.getSkillGap().add(newSkillGap);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto toggleFavoriteCourse(Authentication authentication, Integer newFavoriteCourseId) {
        String email = authentication.getName();
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new RuntimeException("User not found"));

        if (user.getFavoriteCourses().contains(newFavoriteCourseId)) {
            user.getFavoriteCourses().remove(newFavoriteCourseId);
        } else {
            user.getFavoriteCourses().add(newFavoriteCourseId);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto addPoints(Long accountId, Integer newPointsAchieved) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        Integer newPoints = user.getPoints() + newPointsAchieved;
        user.setPoints(newPoints);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto addPayment(Long accountId, Payment newPayment) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        if (!user.getPayments().contains(newPayment)) {
            user.getPayments().add(newPayment);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removeInterest(Long accountId, Interests removeInterest) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        if (!user.getInterests().contains(removeInterest)) {
            user.getInterests().remove(removeInterest);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removeSkill(Long accountId, Skills removeSkill) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        if (!user.getSkills().contains(removeSkill)) {
            user.getSkills().remove(removeSkill);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removeSkillGap(Long accountId, Skills removeSkillGap) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        if (!user.getSkillGap().contains(removeSkillGap)) {
            user.getSkillGap().remove(removeSkillGap);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removeSkillGaps(Long accountId, List<Skills> removeSkillGaps) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        for (Skills skill : removeSkillGaps) {
            if (!user.getSkillGap().contains(skill)) {
                user.getSkillGap().remove(skill);
            }
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removeFavoriteCourse(Long accountId, Integer removeFavoriteCourseId) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        if (!user.getFavoriteCourses().contains(removeFavoriteCourseId)) {
            user.getFavoriteCourses().remove(removeFavoriteCourseId);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removePoints(Long accountId, Integer removePointsAchieved) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        Integer newPoints = user.getPoints() - removePointsAchieved;
        user.setPoints(newPoints);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removePayment(Long accountId, Payment removePayment) {
        validate.validateUserExists(accountId);
        User user = userRepository.findById(accountId).orElseThrow();
        if (!user.getPayments().contains(removePayment)) {
            user.getPayments().remove(removePayment);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
