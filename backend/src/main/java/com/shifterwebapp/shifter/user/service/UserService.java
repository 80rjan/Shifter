package com.shifterwebapp.shifter.user.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.auth.RegisterDto;
import com.shifterwebapp.shifter.user.UserInfoDto;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.user.*;
import lombok.RequiredArgsConstructor;
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
    public String getUserEmailById(Long userId) {
        validate.validateUserExists(userId);
        return userRepository.getUserEmailById(userId);
    }

    @Override
    public Boolean getUserHasUsedFreeConsultation(String userEmail) {
        validate.validateUserExists(userEmail);
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        return user.getHasUsedFreeConsultation();
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
                .hasUsedFreeConsultation(false)
                .companyType(registerDto.getCompanyType())
                .workPosition(registerDto.getWorkPosition())
                .interests(registerDto.getInterests())
                .skills(List.of())
                .desiredSkills(registerDto.getDesiredSkills())
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
    public UserDto updateUser(Long id, UserInfoDto userInfoDto) {
        validate.validateUserExists(id);
        User user = userRepository.findById(id).orElseThrow();

        if (userInfoDto.getName() != null) {
            user.setName(userInfoDto.getName());
        }
        if (userInfoDto.getCompanyType() != null) {
            user.setCompanyType(userInfoDto.getCompanyType());
        }
        if (userInfoDto.getWorkPosition() != null) {
            user.setWorkPosition(userInfoDto.getWorkPosition());
        }

        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateInterests(Long id, List<String> interests) {
        validate.validateUserExists(id);
        User user = userRepository.findById(id).orElseThrow();

        user.setInterests(interests);

        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateDesiredSkills(Long id, List<String> desiredSkills) {
        validate.validateUserExists(id);
        User user = userRepository.findById(id).orElseThrow();

        user.setDesiredSkills(desiredSkills);

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
    public UserDto addSkill(Long userId, String newSkill) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        if (!user.getSkills().contains(newSkill)) {
            user.getSkills().add(newSkill);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto addSkills(Long userId, List<String> newSkills) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        for (String skill : newSkills) {
            if (!user.getSkills().contains(skill)) {
                user.getSkills().add(skill);
            }
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
    public UserDto removeDesiredSkill(Long userId, String removeDesiredSkill) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        if (!user.getDesiredSkills().contains(removeDesiredSkill)) {
            user.getDesiredSkills().remove(removeDesiredSkill);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removeDesiredSkills(Long userId, List<String> removeDesiredSkills) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        for (String skill : removeDesiredSkills) {
            if (!user.getDesiredSkills().contains(skill)) {
                user.getDesiredSkills().remove(skill);
            }
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

    @Override
    public void markUserAsUsedFreeConsultation(String email) {
        validate.validateUserExists(email);
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setHasUsedFreeConsultation(true);
        userRepository.save(user);
    }
}
