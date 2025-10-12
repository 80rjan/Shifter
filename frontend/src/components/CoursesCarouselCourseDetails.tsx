import {useCourseStorage} from "../context/CourseStorage.ts";
import Slider from "react-slick";
import CourseCard from "./CourseCard.tsx";
import {useAuthContext} from "../context/AuthContext.tsx";
import {useEffect} from "react";
import {fetchRecommendedCoursesApi} from "../api/courseApi.ts";
import CourseCardSkeleton from "./skeletons/CourseCardSkeleton.tsx";

function CoursesCarouselCourseDetails({courseId}: {
    courseId: number;
}) {
    const {recommendedCourses, setRecommendedCourses} = useCourseStorage();
    const {accessToken} = useAuthContext();

    useEffect(() => {
        const fetchRecommendedCourses = async () => {
            const stored = sessionStorage.getItem("recommendedCourses");
            if (stored) {
                setRecommendedCourses(JSON.parse(stored));
                return;
            }

            fetchRecommendedCoursesApi(accessToken || "")
                .then(data => {
                    setRecommendedCourses(data);
                    sessionStorage.setItem("recommendedCourses", JSON.stringify(data));
                })
                .catch(err => {
                    console.error("Error fetching recommended courses:", err);
                });
        }

        if (!recommendedCourses) {
            fetchRecommendedCourses();
        }
    }, []);

    return (
        <section className="flex flex-col gap-12 text-left px-horizontal-lg py-vertical-md">
            <h2 className="text-5xl font-semibold">People also bought</h2>

            <div className="relative mx-0 my-auto w-full p-0">
                {
                    recommendedCourses && recommendedCourses.filter(course => course.id !== courseId).length > 0 ? (
                        recommendedCourses.filter(course => course.id !== courseId).length <= 3 ? (
                            <div className="flex gap-4 justify-center items-center">
                                {recommendedCourses
                                    .filter(course => course.id !== courseId)
                                    .map((course, index) => (
                                    <div key={index} className="max-w-1/3">
                                        <CourseCard card={course} key={index}/>
                                    </div>
                                ))}
                            </div>
                        ) : (
                            <Slider {...settings}>
                                {recommendedCourses
                                    .filter(course => course.id !== courseId)
                                    .map((course, index) => (
                                    <div key={index}>
                                        <CourseCard card={course}/>
                                    </div>
                                ))}
                            </Slider>
                        )
                    ) : (
                        <Slider {...settings}>
                            {[...Array(4)].map((_, index) => (
                                <div key={index}>
                                    <CourseCardSkeleton/>
                                </div>
                            ))}
                        </Slider>
                    )}
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