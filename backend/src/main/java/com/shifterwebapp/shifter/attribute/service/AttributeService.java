package com.shifterwebapp.shifter.attribute.service;

import com.shifterwebapp.shifter.Validate;
import com.shifterwebapp.shifter.attribute.Attribute;
import com.shifterwebapp.shifter.attribute.AttributeTranslate;
import com.shifterwebapp.shifter.attribute.dto.AttributeReqShort;
import com.shifterwebapp.shifter.attribute.repository.AttributeRepository;
import com.shifterwebapp.shifter.enums.AttributeType;
import com.shifterwebapp.shifter.enums.Language;
import com.shifterwebapp.shifter.exception.ResourceNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class AttributeService implements ImplAttributeService {


    private final AttributeRepository attributeRepository;
    private final Validate validate;

    @Override
    public List<Attribute> processRawAttributes(List<String> rawAttributes, AttributeType type, Language language) {
        return rawAttributes.stream()
                .distinct()
                .map(String::toUpperCase)
                .map(value -> {
                    Optional<Attribute> existingAttrOptional =
                            attributeRepository.findByTypeAndTranslationValueAndLanguage(type, value, language);

                    if (existingAttrOptional.isPresent()) {
                        return existingAttrOptional.get();
                    }

                    // Create new Attribute and its Translation
                    Attribute attribute = Attribute.builder()
                            .type(type)
                            .build();

                    AttributeTranslate attributeTranslate = AttributeTranslate.builder()
                            .language(language)
                            .value(value)
                            .attribute(attribute)
                            .build();

                    attribute.setTranslations(List.of(attributeTranslate));

                    return attributeRepository.save(attribute);
                })
                .toList();
    }

    @Override
    public List<Attribute> processExistingAttributes(List<AttributeReqShort> shortAttributes, AttributeType type, Language language) {
        List<Attribute> attributes = new ArrayList<>();

        for (AttributeReqShort req : shortAttributes) {
            Long id = req.id();
            String value = req.value();

            validate.validateAttributeExists(id);
            Attribute attribute = attributeRepository.findByIdAndType(id, type).orElseThrow(
                    () -> new ResourceNotFoundException("Attribute with id " + id + " is not of type " + type)
            );

            AttributeTranslate attributeTranslate = AttributeTranslate.builder()
                    .value(value)
                    .language(language)
                    .attribute(attribute)
                    .build();

            attribute.getTranslations().add(attributeTranslate);
            attributes.add(attribute);
        }

        return attributeRepository.saveAll(attributes);
    }

    @Override
    public Attribute getById(Long id) {
        validate.validateAttributeExists(id);
        return attributeRepository.findById(id).orElseThrow();
    }

    @Override
    public List<Attribute> getAttributeByIds(List<Long> ids) {
        return attributeRepository.findAllById(ids);
    }

    @Override
    public List<Attribute> getAttributeByIdsAndType(List<Long> ids, AttributeType attributeType) {
        return attributeRepository.findAllByIdAndType(ids, attributeType);
    }

    @Override
    public List<Attribute> getAttributeByCourseIdAndType(Long courseId, AttributeType attributeType) {
        return attributeRepository.findAllByCourseIdAndType(courseId, attributeType);
    }

    @Override
    public List<String> getSkillsByCourseId(Long courseId, Language language) {
        return attributeRepository.getAttributeValueByCourseId(courseId, AttributeType.SKILL, language);
    }

    @Override
    public List<String> getTopicsByCourseId(Long courseId, Language language) {
        return attributeRepository.getAttributeValueByCourseId(courseId, AttributeType.TOPIC, language);
    }

    @Override
    public List<String> getSkillsByUserId(Long userId, Language language) {
        return attributeRepository.getAttributeValueByUserId(userId, AttributeType.SKILL, language);
    }

    @Override
    public List<String> getTopicsByUserId(Long userId, Language language) {
        return attributeRepository.getAttributeValueByUserId(userId, AttributeType.TOPIC, language);
    }


}
