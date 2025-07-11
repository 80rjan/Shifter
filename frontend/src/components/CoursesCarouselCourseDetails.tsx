import {useCourseStorage} from "../context/CourseStorage.ts";
import Slider from "react-slick";
import CourseCard from "./CourseCard.tsx";
import {useGlobalContext} from "../context/GlobalContext.tsx";
import {useEffect} from "react";
import {fetchRecommendedCoursesApi} from "../api/courseApi.ts";

function CoursesCarouselCourseDetails() {
    const {recommendedCourses, setRecommendedCourses} = useCourseStorage();
    const {accessToken} = useGlobalContext();

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
            <h2 className="text-5xl">People also bought</h2>

            <div className="relative mx-0 my-auto w-full p-0">
                {
                    recommendedCourses ?
                        <Slider {...settings}>
                            {
                                recommendedCourses.map((course, index) => (
                                    <CourseCard card={course} key={index}/>
                                    // <div className="h-full flex flex-col justify-center bg-red-500" key={index}>
                                    //     <CourseCard card={course}/>
                                    // </div>
                                ))
                            }
                        </Slider>
                        :
                        <div className="flex flex-col gap-12 justify-center items-center">
                            <div className="w-20 loader"></div>
                            <span className="text-xl font-semibold text-black/40">Loading...</span>
                        </div>
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