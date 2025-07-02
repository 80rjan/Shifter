import React from 'react';
import Slider from 'react-slick';
import 'slick-carousel/slick/slick.css';
import 'slick-carousel/slick/slick-theme.css';
import Marketing from "../assets/courseImg/marketing.png"
import Sales from "../assets/courseImg/sales.webp"
import Onboarding from "../assets/courseImg/onboarding.png"
import Leadership from "../assets/courseImg/leadership-2.webp"
import BusinessFoundation from "../assets/courseImg/business-foundation.jpg"
import CourseCard from "./CourseCard.tsx";
import "../slick-custom.css";

import ShifterArrow from "../assets/shifterImg/Shifter-Arrow.png";

function CoursesCarousel() {
    const courses = [
        {
            title: "The Go-To Marketing Strategy",
            skills: ["Marketing", "Strategy", "Growth"],
            price: 15,
            duration_hours: 2,
            number_modules: 5,
            image: Marketing,
            imageAlt: "Marketing Course Banner",
            color: "red"
        },
        {
            title: "Personalized Onboarding Process",
            skills: ["Onboarding", "Management"],
            price: 20,
            duration_hours: 3,
            number_modules: 7,
            image: Onboarding,
            imageAlt: "Onboarding Course Banner",
            color: "shifter"
        },
        {
            title: "Strategic Leadership & Management",
            skills: ["Management", "Leadership", "Strategy"],
            price: 25,
            duration_hours: 3,
            number_modules: 8,
            image: Leadership,
            imageAlt: "Leadership Course Banner",
            color: "teal"
        },
        {
            title: "Business Foundations Roadmap",
            skills: ["Leadership", "Strategy", "Business"],
            price: 30,
            duration_hours: 4,
            number_modules: 11,
            image: BusinessFoundation,
            imageAlt: "Business Foundations Course Banner",
            color: "dark-blue"
        },
        {
            title: "Sales & Marketing for SME's",
            skills: ["Marketing", "Sales", "Tactics"],
            price: 18,
            duration_hours: 2.5,
            number_modules: 6,
            image: Sales,
            imageAlt: "Sales Course Banner",
            color: "deep-green"
        },
    ];

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

    return (
        <section className="relative flex flex-col gap-10 items-center bg-dark-blue/10 py-vertical-md px-4 overflow-clip">
            <img src={ShifterArrow} alt="Shifter Arrow" className="absolute opacity-30 h-150 w-120 -rotate-130 -top-30 right-0" />
            <img src={ShifterArrow} alt="Shifter Arrow" className="absolute opacity-30 h-150 w-120 rotate-50 -bottom-30 left-0" />

            <div className="text-center flex flex-col gap-2">
                <h2 className="text-4xl font-regular whitespace-nowrap">
                    Unlock Your Growth With <span className="text-shifter font-semibold">E-Learning</span>
                </h2>
                <p className="text-lg font-light text-gray-600">
                    Access expert-led courses designed to help you master business, strategy, and success - anytime, anywhere.
                </p>
            </div>

            <div className="relative max-w-[80%] mx-0 my-auto w-full p-0">
                <Slider {...settings}>
                    {courses.map((course, index) => (
                        <div className="h-full flex flex-col">
                            {/*<CourseCard card={course} />*/}
                        </div>
                    ))}
                </Slider>
            </div>
        </section>
    );
}

export default CoursesCarousel;
