//package com.shifterwebapp.shifter.course.mappers;
//
//import com.shifterwebapp.shifter.catalog.web.response.CourseModuleFullResponse;
//import com.shifterwebapp.shifter.catalog.web.response.CourseModuleLearningResponse;
//import com.shifterwebapp.shifter.catalog.web.response.CourseModulePreviewResponse;
//import com.shifterwebapp.shifter.catalog.domain.CourseModule;
//import com.shifterwebapp.shifter.catalog.domain.CourseModuleTranslation;
//import org.mapstruct.AfterMapping;
//import org.mapstruct.Context;
//import org.mapstruct.Mapper;
//import org.mapstruct.MappingTarget;
//
//import java.util.List;
//import java.util.Optional;
//
//@Mapper(componentModel = "spring", uses = {CourseLectureMapper.class})
//public abstract class CourseModuleMapper {
//
//    public abstract CourseModulePreviewResponse toDtoPreview(CourseModule courseModule, @Context Language lang);
//    public abstract List<CourseModulePreviewResponse> toDtoPreview(List<CourseModule> courseModules, @Context Language lang);
//
//    public abstract CourseModuleLearningResponse toDtoLearn(CourseModule courseModule, @Context Language lang, @Context Long userId);
//    public abstract List<CourseModuleLearningResponse> toDtoLearn(List<CourseModule> courseModules, @Context Language lang, @Context Long userId);
//
//    public abstract CourseModuleFullResponse toDtoFull(CourseModule courseModule, @Context Language lang);
//    public abstract List<CourseModuleFullResponse> toDtoFull(List<CourseModule> courseModules, @Context Language lang);
//
//    @AfterMapping
//    protected void enrichPreview(@MappingTarget CourseModulePreviewResponse dto,
//                                           CourseModule courseModule,
//                                           @Context Language lang) {
//        getTranslation(courseModule, lang).ifPresent(t -> dto.setTitle(t.getTitle()));
//    }
//
//    @AfterMapping
//    protected void enrichLearn(@MappingTarget CourseModuleLearningResponse dto,
//                                         CourseModule courseModule,
//                                         @Context Language lang,
//                                         @Context Long userId) {
//        getTranslation(courseModule, lang).ifPresent(t -> dto.setTitle(t.getTitle()));
//    }
//
//    @AfterMapping
//    protected void enrichFull(@MappingTarget CourseModuleFullResponse dto,
//                                        CourseModule courseModule,
//                                        @Context Language lang) {
//        getTranslation(courseModule, lang).ifPresent(t -> dto.setTitle(t.getTitle()));
//    }
//
//    protected Optional<CourseModuleTranslation> getTranslation(CourseModule courseModule, Language lang) {
//        return courseModule.getTranslations().stream()
//                .filter(t -> t.getLanguage().equals(lang))
//                .findFirst();
//    }
//}
