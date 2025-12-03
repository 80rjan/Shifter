import {useCourseStorage} from "../context/CourseStorage.ts";
import Slider from "react-slick";
import CourseCard from "./CourseCard.tsx";
import {useTranslation} from "react-i18next";

function CoursesCarouselCourseDetails({courseId}: {
    courseId: number;
}) {
    const {recommendedCourses} = useCourseStorage();
    const { t } = useTranslation("courses");

    if (recommendedCourses!.length <= 1) // 1 because the course the user is curr on is in the recommended courses
        return null;

    return (
        <section className="flex flex-col gap-12 text-left px-horizontal-lg py-vertical-md">
            <h2 className="text-5xl font-semibold">{t("courseDetails.peopleAlsoBought")}</h2>

            <div className="relative mx-0 my-auto w-full p-0">
                {
                    recommendedCourses!.length <= 4 ? (
                        <div className="flex gap-4 justify-center items-center">
                            {recommendedCourses!
                                .map((course, index) => {
                                    if (course.id === courseId)
                                        return null
                                    return (
                                        <div key={index} className="max-w-1/3">
                                            <CourseCard card={course} key={index}/>
                                        </div>
                                    )
                                })}
                        </div>
                    ) : (
                        <Slider {...settings}>
                            {recommendedCourses!
                                .map((course, index) => {
                                    if (course.id === courseId)
                                        return null
                                    return (
                                        <div key={index}>
                                            <CourseCard card={course}/>
                                        </div>
                                    )
                                })}
                        </Slider>
                    )
                }
            </div>
        </section>
    )
}


const settings = {
    dots: false,
    infinite: true,
    speed: 500,
    slidesToShow: 3,
    slidesToScroll: 1,
    centerMode: true,
    centerPadding: '0',
    cssEase: 'cubic-bezier(0.4, 0, 0.2, 1)',
    responsive: [
        {
            breakpoint: 1024,
            settings: {
                slidesToShow: 1,
                slidesToScroll: 1,
                infinite: true,
                dots: false
            }
        },
        {
            breakpoint: 600,
            settings: {
                slidesToShow: 1,
                slidesToScroll: 1,
                initialSlide: 1
            }
        }
    ]
};

export default CoursesCarouselCourseDetails;