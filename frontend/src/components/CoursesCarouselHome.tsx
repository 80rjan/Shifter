import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import CourseCard from "./CourseCard.tsx";
import "../slick-custom.css";

import ShifterArrow from "../../public/Shifter-Arrow.png";
import {useCourseStorage} from "../context/CourseStorage.ts";

function CoursesCarouselHome() {
    const {recommendedCourses} = useCourseStorage();

    return (
        <section
            className="relative flex flex-col gap-10 items-center bg-dark-blue/10 py-vertical-md px-4 overflow-clip">
            <img src={ShifterArrow} alt="Shifter Arrow"
                 className="absolute opacity-30 h-150 w-120 -rotate-130 -top-30 right-0"/>
            <img src={ShifterArrow} alt="Shifter Arrow"
                 className="absolute opacity-30 h-150 w-120 rotate-50 -bottom-30 left-0"/>

            <div className="text-center flex flex-col gap-4">
                <h2 className="text-5xl whitespace-nowrap">
                    Unlock Your Growth With <strong className="text-shifter">E-Learning</strong>
                </h2>
                <p className="text-2xl font-light text-black/80">
                    Access expert-led courses designed to help you master business, strategy, and success - anytime,
                    anywhere.
                </p>
            </div>

            <div className="relative max-w-[85%] mx-0 my-auto w-full p-0">
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
                        <div className="flex flex-col gap-12 justify-center items-center" >
                            <div className="w-20 loader"></div>
                            <span className="text-xl font-semibold text-black/40">Loading...</span>
                        </div>
                }
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
