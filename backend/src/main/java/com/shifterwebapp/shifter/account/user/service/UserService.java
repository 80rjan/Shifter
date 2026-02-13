package com.shifterwebapp.shifter.account.user.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.tag.Tag;
import com.shifterwebapp.shifter.tag.service.TagService;
import com.shifterwebapp.shifter.auth.UserPersonalizationDto;
import com.shifterwebapp.shifter.enums.TagType;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.enums.LoginProvider;
import com.shifterwebapp.shifter.account.user.User;
import com.shifterwebapp.shifter.account.user.dto.UserDto;
import com.shifterwebapp.shifter.account.user.dto.PersonalizeUserReq;
import com.shifterwebapp.shifter.account.user.mapper.UserMapper;
import com.shifterwebapp.shifter.account.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class UserService implements ImplUserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;
    private final TagService tagService;
    private final Validate validate;
    private final PasswordEncoder passwordEncoder;

    @Transactional(readOnly = true)
    @Override
    public UserDto getUserById(Long userId, Language language) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        return userMapper.toDto(user, language);
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserEntityById(Long userId) {
        validate.validateUserExists(userId);
        return userRepository.findById(userId).orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public User getUserEntityByEmail(String email) {
        validate.validateUserExists(email);
        return userRepository.findByEmail(email).orElseThrow();
    }

    @Transactional(readOnly = true)
    @Override
    public String getUserEmailById(Long userId) {
        validate.validateUserExists(userId);
        return userRepository.getUserEmailById(userId);
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean getUserHasUsedFreeConsultation(String userEmail) {
        validate.validateUserExists(userEmail);
        User user = userRepository.findByEmail(userEmail).orElseThrow();
        return user.getUsedFreeConsultation();
    }

    @Transactional(readOnly = true)
    @Override
    public Boolean existsUserByEmail(String email) {
        return userRepository.existsUserByEmail(email);
    }

    @Transactional
    @Override
    public User createInitialUser(String email, String password, LoginProvider loginProvider) {
        if (userRepository.existsUserByEmail(email)) {
            throw new RuntimeException("Email already in use");
        }

        User user = User.builder()
                .name(email)
                .email(email)
                .passwordHash(passwordEncoder.encode(password))
                .loginProvider(loginProvider)
                .build();

        return userRepository.save(user);
    }

    @Transactional
    @Override
    public User personalizeUser(UserPersonalizationDto userPersonalizationDto) {
        validate.validateUserExists(userPersonalizationDto.getEmail());
        User user = userRepository.findByEmail(userPersonalizationDto.getEmail()).orElseThrow();

        user.setName(userPersonalizationDto.getName());
        user.setCompanySize(userPersonalizationDto.getCompanySize());
        user.setWorkPosition(userPersonalizationDto.getWorkPosition());
        user.setProfileComplete(true);

        // User only has interests in the beginning, no skills.
        // The filter is to filter out the topics (user interests) only, no skills
        user.setTags(tagService.getTagsByIds(userPersonalizationDto.getTagIdList()).stream().filter(a -> a.getType().equals(TagType.TOPIC)).collect(Collectors.toList()));

        return userRepository.save(user);
    }

    @Transactional
    @Override
    public void deleteUser(Long userId) {
        validate.validateUserExists(userId);
        // Soft delete implementation (no data loss)

        User user = userRepository.findById(userId).orElseThrow();
        user.setDeleted(true);
        user.setEmail("deleted_" + user.getEmail());
        user.getMeetingEmailReminders().clear();
    }

    @Transactional
    @Override
    public UserDto updateUser(Long id, Language language, PersonalizeUserReq personalizeUserReq) {
        validate.validateUserExists(id);
        User user = userRepository.findById(id).orElseThrow();

        if (personalizeUserReq.getName() != null) {
            user.setName(personalizeUserReq.getName());
        }
        if (personalizeUserReq.getCompanySize() != null) {
            user.setCompanySize(personalizeUserReq.getCompanySize());
        }
        if (personalizeUserReq.getWorkPosition() != null) {
            user.setWorkPosition(personalizeUserReq.getWorkPosition());
        }

        userRepository.save(user);
        return userMapper.toDto(user, language);
    }

    @Transactional
    @Override
    public UserDto updateTags(Long id, Language language, List<Long> tagIds) {
        validate.validateUserExists(id);

        User user = userRepository.findById(id).orElseThrow();
        user.setTags(tagService.getTagsByIds(tagIds));

        userRepository.save(user);
        return userMapper.toDto(user, language);
    }

    @Transactional
    @Override
    public void addTags(Long userId, Language language, List<Long> tagIds) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();

        // Get current tag IDs
        Set<Long> existingTagIds = user.getTags()
                .stream()
                .map(Tag::getId)
                .collect(Collectors.toSet());

        // Get new tags and filter out those already present
        List<Tag> newTags = tagService.getTagsByIds(tagIds)
                .stream()
                .filter(tag -> !existingTagIds.contains(tag.getId()))
                .toList();

        // Add only the new ones
        user.getTags().addAll(newTags);

        userRepository.save(user);
    }


    @Transactional
    @Override
    public UserDto toggleFavoriteCourse(Long userId, Language language, Integer newFavoriteCourseId) {
        User user = getUserEntityById(userId);

        if (user.getFavoriteCourseIds().contains(newFavoriteCourseId)) {
            user.getFavoriteCourseIds().remove(newFavoriteCourseId);
        } else {
            user.getFavoriteCourseIds().add(newFavoriteCourseId);
        }
        userRepository.save(user);
        return userMapper.toDto(user, language);
    }

    @Transactional
    @Override
    public void addPoints(Long userId, Integer newPointsAchieved) {
        validate.validateUserExists(userId);
        User user = userRepository.findById(userId).orElseThrow();
        Integer newPoints = user.getPoints() + newPointsAchieved;
        user.setPoints(newPoints);
        userRepository.save(user);
    }


    @Transactional
    @Override
    public void markUserAsUsedFreeConsultation(String email) {
        validate.validateUserExists(email);
        User user = userRepository.findByEmail(email).orElseThrow();
        user.setUsedFreeConsultation(true);
        userRepository.save(user);
    }
}
