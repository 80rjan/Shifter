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
    public UserDto getUserById(Long userId) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        return userMapper.toDto(user);
    }

    @Override
    public User getUserEntityById(Long userId) {
        validate.validateUserExists(userId);
        return userRepository.findById(userId).orElseThrow();
    }

    @Override
    public User getUserEntityByEmail(String email) {
        validate.validateUserExists(email);
        return userRepository.findByEmail(email).orElseThrow();
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
    public void deleteUser(Long userId) {
        validate.validateUserExists(userId);
        userRepository.deleteById(userId);
    }

    @Override
    public UserDto updateName(Long userId, String newName) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        user.setName(newName);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateMail(Long userId, String newMail) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        user.setEmail(newMail);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updatePassword(Long userId, String newPass) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        user.setPasswordHash(passwordEncoder.encode(newPass));
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateWorkPosition(Long userId, String newWorkPosition) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        user.setWorkPosition(newWorkPosition);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateCompanyType(Long userId, CompanyType newCompanyType) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        user.setCompanyType(newCompanyType);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto addInterest(Long userId, Interests newInterest) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        if (!user.getInterests().contains(newInterest)) {
            user.getInterests().add(newInterest);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto addSkill(Long userId, Skills newSkill) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        if (!user.getSkills().contains(newSkill)) {
            user.getSkills().add(newSkill);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto addSkills(Long userId, List<Skills> newSkills) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        for (Skills skill : newSkills) {
            if (!user.getSkills().contains(skill)) {
                user.getSkills().add(skill);
            }
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto addSkillGap(Long userId, Skills newSkillGap) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        if (!user.getSkillGap().contains(newSkillGap)) {
            user.getSkillGap().add(newSkillGap);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto toggleFavoriteCourse(Long userId, Integer newFavoriteCourseId) {
        User user = getUserEntityById(userId);

        if (user.getFavoriteCourses().contains(newFavoriteCourseId)) {
            user.getFavoriteCourses().remove(newFavoriteCourseId);
        } else {
            user.getFavoriteCourses().add(newFavoriteCourseId);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto addPoints(Long userId, Integer newPointsAchieved) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        Integer newPoints = user.getPoints() + newPointsAchieved;
        user.setPoints(newPoints);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto addPayment(Long userId, Payment newPayment) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        if (!user.getPayments().contains(newPayment)) {
            user.getPayments().add(newPayment);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removeInterest(Long userId, Interests removeInterest) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        if (!user.getInterests().contains(removeInterest)) {
            user.getInterests().remove(removeInterest);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removeSkill(Long userId, Skills removeSkill) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        if (!user.getSkills().contains(removeSkill)) {
            user.getSkills().remove(removeSkill);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removeSkillGap(Long userId, Skills removeSkillGap) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        if (!user.getSkillGap().contains(removeSkillGap)) {
            user.getSkillGap().remove(removeSkillGap);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removeSkillGaps(Long userId, List<Skills> removeSkillGaps) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        for (Skills skill : removeSkillGaps) {
            if (!user.getSkillGap().contains(skill)) {
                user.getSkillGap().remove(skill);
            }
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removeFavoriteCourse(Long userId, Integer removeFavoriteCourseId) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        if (!user.getFavoriteCourses().contains(removeFavoriteCourseId)) {
            user.getFavoriteCourses().remove(removeFavoriteCourseId);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removePoints(Long userId, Integer removePointsAchieved) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        Integer newPoints = user.getPoints() - removePointsAchieved;
        user.setPoints(newPoints);
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removePayment(Long userId, Payment removePayment) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        if (!user.getPayments().contains(removePayment)) {
            user.getPayments().remove(removePayment);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }
}
