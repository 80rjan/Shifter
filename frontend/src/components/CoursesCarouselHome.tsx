import Slider from 'react-slick';
import CourseCard from "./CourseCard.tsx";
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import 'slick-carousel/slick/slick.css';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import 'slick-carousel/slick/slick-theme.css';
// eslint-disable-next-line @typescript-eslint/ban-ts-comment
// @ts-expect-error
import "../slick-custom.css";

import { useCourseStorage } from "../context/CourseStorage.ts";
import CourseCardSkeleton from "./skeletons/CourseCardSkeleton.tsx";
import { useTranslation } from "react-i18next";

function CoursesCarouselHome() {
    const { recommendedCourses } = useCourseStorage();
    const { t } = useTranslation("home");

    return (
        <section
            className="relative flex flex-col items-center gap-between-md py-vertical-lg px-horizontal-lg overflow-clip">

            {/* TEXT */}
            <div className="text-center flex flex-col gap-4 text-black-text">
                <h2 className="text-5xl font-semibold whitespace-nowrap" dangerouslySetInnerHTML={{ __html: t("coursesCarousel.heading") }} />
                <p className="text-xl font-light opacity-80">{t("coursesCarousel.description")}</p>
            </div>

            {/* SLIDER / CARDS */}
            <div className="relative mx-0 my-auto w-full p-0">
                {recommendedCourses && recommendedCourses.length > 0 ? (
                    recommendedCourses.length <= 3 ? (
                        <div className="flex gap-12 justify-evenly items-center">
                            {recommendedCourses.map((course, index) => (
                                <div key={index} className="max-w-1/3">
                                    <CourseCard card={course} />
                                </div>
                            ))}
                        </div>
                    ) : (
                        <Slider {...settings}>
                            {recommendedCourses.map((course, index) => (
                                <div key={index}>
                                    <CourseCard card={course} />
                                </div>
                            ))}
                        </Slider>
                    )
                ) : (
                    <Slider {...settings}>
                        {[...Array(4)].map((_, index) => (
                            <div key={index}>
                                <CourseCardSkeleton />
                            </div>
                        ))}
                    </Slider>
                )}
            </div>

        </section>
    );
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

export default CoursesCarouselHome;
