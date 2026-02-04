package com.shifterwebapp.shifter.course.course.projection;

import com.shifterwebapp.shifter.enums.TagType;

public interface CourseTagProjection {
    Long getCourseId();
    String getTagValue();
    TagType getTagType();
}
