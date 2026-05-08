package com.shifterwebapp.shifter.catalog.infrastructure;

import com.shifterwebapp.shifter.catalog.domain.CourseModuleTranslation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CourseModuleTranslationRepository extends JpaRepository<CourseModuleTranslation, Long> {

}
