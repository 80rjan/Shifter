//package com.shifterwebapp.shifter.course.mappers;
//
//import com.shifterwebapp.shifter.catalog.web.response.CourseModuleDtoFull;
//import com.shifterwebapp.shifter.catalog.web.response.CourseModuleDtoLearn;
//import com.shifterwebapp.shifter.catalog.web.response.CourseModuleDtoPreview;
//import com.shifterwebapp.shifter.catalog.domain.CourseModule;
//import com.shifterwebapp.shifter.catalog.domain.CourseModuleTranslate;
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
//    public abstract CourseModuleDtoPreview toDtoPreview(CourseModule courseModule, @Context Language lang);
//    public abstract List<CourseModuleDtoPreview> toDtoPreview(List<CourseModule> courseModules, @Context Language lang);
//
//    public abstract CourseModuleDtoLearn toDtoLearn(CourseModule courseModule, @Context Language lang, @Context Long userId);
//    public abstract List<CourseModuleDtoLearn> toDtoLearn(List<CourseModule> courseModules, @Context Language lang, @Context Long userId);
//
//    public abstract CourseModuleDtoFull toDtoFull(CourseModule courseModule, @Context Language lang);
//    public abstract List<CourseModuleDtoFull> toDtoFull(List<CourseModule> courseModules, @Context Language lang);
//
//    @AfterMapping
//    protected void enrichPreview(@MappingTarget CourseModuleDtoPreview dto,
//                                           CourseModule courseModule,
//                                           @Context Language lang) {
//        getTranslation(courseModule, lang).ifPresent(t -> dto.setTitle(t.getTitle()));
//    }
//
//    @AfterMapping
//    protected void enrichLearn(@MappingTarget CourseModuleDtoLearn dto,
//                                         CourseModule courseModule,
//                                         @Context Language lang,
//                                         @Context Long userId) {
//        getTranslation(courseModule, lang).ifPresent(t -> dto.setTitle(t.getTitle()));
//    }
//
//    @AfterMapping
//    protected void enrichFull(@MappingTarget CourseModuleDtoFull dto,
//                                        CourseModule courseModule,
//                                        @Context Language lang) {
//        getTranslation(courseModule, lang).ifPresent(t -> dto.setTitle(t.getTitle()));
//    }
//
//    protected Optional<CourseModuleTranslate> getTranslation(CourseModule courseModule, Language lang) {
//        return courseModule.getTranslations().stream()
//                .filter(t -> t.getLanguage().equals(lang))
//                .findFirst();
//    }
//}
