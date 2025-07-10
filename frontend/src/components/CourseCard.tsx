import React from "react";
import StarFilled from "../assets/icons/StarFilled.tsx";
import {hexToRgb} from "../utils/hexToRGB.ts";
import {slugify} from "../utils/slug.ts";
import { Link } from "react-router-dom";
import type {CoursePreview} from "../types/CoursePreview.tsx";
import HeartOutline from "../assets/icons/HeartOutline.tsx";
import HeartFill from "../assets/icons/HeartFill.tsx";
import {useGlobalContext} from "../context/GlobalContext.tsx";
import {toggleFavoriteCourseApi} from "../api/user.ts";
import { toast } from "react-toastify";

const showLoginTooltip = () => {
    toast.info("Please log in to save favorite courses.");
};


function CourseCard({ card }: {card: CoursePreview}) {
    const { accessToken, user, setUser } = useGlobalContext()
    const [isHoveredButton, setisHoveredButton] = React.useState<boolean>(false);
    const [isHoveredHeart, setIsHoveredHeart] = React.useState<boolean>(false);
    const bgColor = "bg-[var(--card-color)]";
    const shadowColor = `rgba(${hexToRgb(card.color)}, 0.6)`;

    const handleToggleFavoriteCourse = () => {
        setUser(prevUser => {
            if (!prevUser) {
                // Show a tooltip or toast here
                showLoginTooltip();
                return prevUser; // Exit early
            }

            return {
                ...prevUser,
                favoriteCourses: prevUser.favoriteCourses.includes(card.id)
                    ? prevUser.favoriteCourses.filter((courseId) => courseId !== card.id)
                    : [...prevUser.favoriteCourses, card.id]
            };
        });

        // Only call API if user is logged in
        if (user) {
            toggleFavoriteCourseApi(card.id, accessToken || "")
                .then(() => {
                    console.log("Course favorite status toggled successfully");
                })
                .catch((error) => {
                    // If the user is not logged in, revert the favorite status change
                    setUser(prevUser => {
                        if (!prevUser) return prevUser

                        return {
                            ...prevUser,
                            favoriteCourses: prevUser.favoriteCourses.includes(card.id)
                                ? prevUser.favoriteCourses.filter((courseId) => courseId !== card.id)
                                : [...prevUser.favoriteCourses, card.id]
                        };
                    });

                    console.error("Error toggling course favorite status:", error);
                });
        }
    };


    return (
        <article
            style={{"--card-color": card.color} as React.CSSProperties}
            className="relative border-1 border-black/10 shadow-md shadow-black/10
                flex flex-col w-full rounded-xl gap-4 py-4 px-4 bg-[#FFFFFF]">

            {/*IMAGE*/}
            <div className="overflow-clip rounded-md rounded-br-4xl rounded-tl-4xl">
                <img src={card.imageUrl} alt={card.title}
                     className="w-full h-[180px] object-cover"/>
            </div>


            {/*TITLE AND TOPICS LEARNED*/}
            <div className="flex flex-col gap-2 items-start text-left h-full">
                {/*Title*/}
                <h3 className="font-semibold text-xl">{card.titleShort}</h3>

                {/*What will be learned*/}
                <p className="text-black/60">{
                    card.topicsCovered.map(item =>
                        item
                            .toLowerCase()
                            .replace(/_/g, " ")
                            .replace(/\b\w/g, c => c.toUpperCase())
                    )
                        .join(" • ")
                }</p>
            </div>

            {/*INFO*/}
            <div className="flex flex-wrap gap-2 whitespace-nowrap">
                <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                    <StarFilled className="w-4 h-4 text-gold"/> {card.rating / card.ratingCount}
                </div>
                <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                    {card.ratingCount} reviews
                </div>
                <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                    {(card.durationMinutes / 60).toFixed(1)} hours
                </div>
                <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                    {0} modules
                </div>
                <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                    {card.difficulty.charAt(0) + card.difficulty.slice(1).toLowerCase()}
                </div>
            </div>

            {/*BUTTON AND PRICE*/}
            <div className="flex justify-between items-center mt-0">
                <p className={`font-bold text-black/80 text-lg ${card.price == 0 && "font-normal"}`}>{card.price > 0 ? "$"+card.price : "Free"}</p>

                <div className="flex items-center gap-2">
                    <button
                        className="cursor-pointer"
                        onClick={handleToggleFavoriteCourse}
                        onMouseEnter={() => setIsHoveredHeart(true)}
                        onMouseLeave={() => setIsHoveredHeart(false)}
                    >
                        {
                            user?.favoriteCourses.includes(card.id) ?
                                <HeartFill
                                    className="w-6 h-auto text-red"
                                />
                                :
                                !isHoveredHeart ?
                                    <HeartOutline
                                        strokeWidth={2}
                                        className="w-6 h-auto text-black/60"/> :
                                    <HeartFill
                                        className="w-6 h-auto text-red"
                                    />
                        }
                    </button>
                    <Link
                        to={"/courses/" + `${card.id}/` + slugify(card.titleShort)}
                        style={isHoveredButton ?
                            {boxShadow: `0 4px 6px -1px ${shadowColor},  0 2px 4px -2px ${shadowColor}`} :
                            {}
                        }
                        className={`transition-all duration-200 ease-in-out cursor-pointer
                    px-8 py-1 ${bgColor} text-white rounded-md border-3 border-white/40 }`}
                        onMouseEnter={() => setisHoveredButton(true)}
                        onMouseLeave={() => setisHoveredButton(false)}
                    >
                        Explore
                    </Link>
                </div>
            </div>

        </article>
    );
}

export default CourseCard;