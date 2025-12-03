package com.shifterwebapp.shifter.user.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.attribute.Attribute;
import com.shifterwebapp.shifter.attribute.service.AttributeService;
import com.shifterwebapp.shifter.auth.UserPersonalizationDto;
import com.shifterwebapp.shifter.enums.AttributeType;
import com.shifterwebapp.shifter.enums.CompanySize;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.enums.LoginProvider;
import com.shifterwebapp.shifter.payment.Payment;
import com.shifterwebapp.shifter.user.User;
import com.shifterwebapp.shifter.user.dto.UserDto;
import com.shifterwebapp.shifter.user.dto.UserDtoBuilder;
import com.shifterwebapp.shifter.user.dto.UserInfoDto;
import com.shifterwebapp.shifter.user.mapper.UserMapper;
import com.shifterwebapp.shifter.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
@RequiredArgsConstructor
public class UserService implements ImplUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final AttributeService attributeService;
    private final Validate validate;
    private final PasswordEncoder passwordEncoder;
    private final UserDtoBuilder userDtoBuilder;

    @Override
    public List<UserDto> getAllUsers() {
        List<User> users = userRepository.findAll();
        return userMapper.toDto(users);
    }

    @Override
    public UserDto getUserById(Long userId, Language language) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        return userDtoBuilder.getUserDto(user, language);
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
    public User createInitialUser(String email, String password, LoginProvider loginProvider) {
        if (userRepository.existsUserByEmail(email)) {
            throw new RuntimeException("Email already in use");
        }

        User user = User.builder()
                .name(email)
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .loginProvider(loginProvider).isAdmin(false)
                .isProfileComplete(false)
                .hasUsedFreeConsultation(false)
                .points(0)
                .favoriteCourseList(new ArrayList<>())
                .workPosition("")
                .companySize(CompanySize.FREELANCE)
                .isEnabled(false)
                .build();

        return userRepository.save(user);
    }

    @Override
    public User personalizeUser(UserPersonalizationDto userPersonalizationDto) {
        validate.validateUserExists(userPersonalizationDto.getEmail());
        User user = userRepository.findByEmail(userPersonalizationDto.getEmail()).orElseThrow();

        user.setName(userPersonalizationDto.getName());
        user.setCompanySize(userPersonalizationDto.getCompanySize());
        user.setWorkPosition(userPersonalizationDto.getWorkPosition());
        user.setIsProfileComplete(true);

        // User only has interests in the beginning, no skills.
        // The filter is to filter out the topics (user interests) only, no skills
        user.setAttributes(attributeService.getAttributeByIds(userPersonalizationDto.getAttributeIdList()).stream().filter(a -> a.getType().equals(AttributeType.TOPIC)).collect(Collectors.toList()));

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
        if (userInfoDto.getCompanySize() != null) {
            user.setCompanySize(userInfoDto.getCompanySize());
        }
        if (userInfoDto.getWorkPosition() != null) {
            user.setWorkPosition(userInfoDto.getWorkPosition());
        }

        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto updateAttribute(Long id, List<Long> attributeIds) {
        validate.validateUserExists(id);

        User user = userRepository.findById(id).orElseThrow();
        user.setAttributes(attributeService.getAttributeByIds(attributeIds));

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
    public UserDto addAttributes(Long userId, List<Long> attributeIds) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();

        // Get current attribute IDs
        Set<Long> existingAttributeIds = user.getAttributes()
                .stream()
                .map(Attribute::getId)
                .collect(Collectors.toSet());

        // Get new attributes and filter out those already present
        List<Attribute> newAttributes = attributeService.getAttributeByIds(attributeIds)
                .stream()
                .filter(attr -> !existingAttributeIds.contains(attr.getId()))
                .toList();

        // Add only the new ones
        user.getAttributes().addAll(newAttributes);

        userRepository.save(user);

        return userMapper.toDto(user);
    }


    @Override
    public UserDto toggleFavoriteCourse(Long userId, Integer newFavoriteCourseId) {
        User user = getUserEntityById(userId);

        if (user.getFavoriteCourseList().contains(newFavoriteCourseId)) {
            user.getFavoriteCourseList().remove(newFavoriteCourseId);
        } else {
            user.getFavoriteCourseList().add(newFavoriteCourseId);
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
        if (!user.getPaymentList().contains(newPayment)) {
            user.getPaymentList().add(newPayment);
        }
        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removeAttribute(Long userId, Long attributeId) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        user.setAttributes(user.getAttributes().stream().filter(a -> !a.getId().equals(attributeId)).toList());

        userRepository.save(user);
        return userMapper.toDto(user);
    }

    @Override
    public UserDto removeAttributes(Long userId, List<Long> attributeIds) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        user.setAttributes(user.getAttributes().stream().filter(a -> !attributeIds.contains(a.getId())).toList());

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
        if (!user.getPaymentList().contains(removePayment)) {
            user.getPaymentList().remove(removePayment);
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
