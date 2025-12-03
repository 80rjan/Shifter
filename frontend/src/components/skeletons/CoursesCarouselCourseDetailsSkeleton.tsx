import Slider from "react-slick";
import CourseCardSkeleton from "./CourseCardSkeleton.tsx";

function CoursesCarouselCourseDetailsSkeleton() {
    return (
        <section className="flex flex-col gap-12 text-left px-horizontal-lg py-vertical-md">
            {/* Title skeleton */}
            <div className="h-10 bg-gray-300 rounded w-1/3 animate-pulse"></div>

            <div className="relative mx-0 my-auto w-full p-0">
                <Slider {...settings}>
                    {[...Array(4)].map((_, index) => (
                        <div key={index}>
                            <CourseCardSkeleton/>
                        </div>
                    ))}
                </Slider>
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

export default CoursesCarouselCourseDetailsSkeleton;