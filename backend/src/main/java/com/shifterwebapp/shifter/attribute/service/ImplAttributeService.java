package com.shifterwebapp.shifter.attribute.service;

import com.shifterwebapp.shifter.attribute.Attribute;
import com.shifterwebapp.shifter.attribute.dto.AttributeReqShort;
import com.shifterwebapp.shifter.enums.AttributeType;
import com.shifterwebapp.shifter.enums.Language;
import org.apache.commons.codec.language.bm.Lang;

import java.util.List;

public interface ImplAttributeService {
    List<Attribute> processRawAttributes(List<String> rawAttributes, AttributeType type, Language language);
    List<Attribute> processExistingAttributes(List<AttributeReqShort> shortAttributes, AttributeType type, Language language);

    Attribute getById(Long id);

    List<Attribute> getAttributeByIds(List<Long> ids);
    List<Attribute> getAttributeByIdsAndType(List<Long> ids, AttributeType attributeType);
    List<Attribute> getAttributeByCourseIdAndType(Long courseId, AttributeType attributeType);

    List<String> getSkillsByCourseId(Long courseId, Language language);
    List<String> getTopicsByCourseId(Long courseId, Language language);

    List<String> getSkillsByUserId(Long userId, Language language);
    List<String> getTopicsByUserId(Long userId, Language language);
}
