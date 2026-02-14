import React from "react";
import StarFilled from "../assets/icons/StarFilled.tsx";
import {hexToRgb} from "../utils/hexToRGB.ts";
import {useNavigate} from "react-router-dom";
import type {CoursePreview} from "../models/javaObjects/course/CoursePreview.tsx";
import HeartOutline from "../assets/icons/HeartOutline.tsx";
import HeartFill from "../assets/icons/HeartFill.tsx";
import {useAuthContext} from "../context/AuthContext.tsx";
import {toggleFavoriteCourseApi} from "../api/userApi.ts";
import {Sparkle} from "lucide-react";
import {fromEnumFormat} from "../utils/toEnumFormat.ts";
import {useTranslation} from "react-i18next";
import {useUserContext} from "../context/UserContext.tsx";
import {LocalizedLink} from "./links/LocalizedLink.tsx";

function CourseCard({card}: { card: CoursePreview }) {
    const {accessToken} = useAuthContext();
    const {user, setUser} = useUserContext();
    const [isHoveredButton, setIsHoveredButton] = React.useState<boolean>(false);
    const [isHoveredHeart, setIsHoveredHeart] = React.useState<boolean>(false);
    const bgColor = "bg-[var(--card-color)]";
    const shadowColor = `rgba(${hexToRgb(card.color)}, 0.6)`;
    const navigate = useNavigate();
    const {t} = useTranslation("courseCard");

    const handleToggleFavoriteCourse = () => {
        if (!user) {
            navigate("/login");
            return;
        }
        setUser(prevUser => {
            if (!prevUser)
                return prevUser;

            return {
                ...prevUser,
                favoriteCourseIds: prevUser.favoriteCourseIds.includes(card.id)
                    ? prevUser.favoriteCourseIds.filter((courseId) => courseId !== card.id)
                    : [...prevUser.favoriteCourseIds, card.id]
            };
        });

        if (user) {
            toggleFavoriteCourseApi(card.id, accessToken || "")
                .then(() => {
                    // console.log("Course favorite status toggled successfully");
                })
                .catch((error) => {
                    setUser(prevUser => {
                        if (!prevUser) return prevUser;
                        return {
                            ...prevUser,
                            favoriteCourseIds: prevUser.favoriteCourseIds.includes(card.id)
                                ? prevUser.favoriteCourseIds.filter((courseId) => courseId !== card.id)
                                : [...prevUser.favoriteCourseIds, card.id]
                        };
                    });

                    console.error("Error toggling favorite status:", error);
                });
        }
    };

    return (
        <article
            style={{"--card-color": card.color} as React.CSSProperties}
            className="relative border-1 border-black/10 shadow-md shadow-black/10
                flex flex-col w-full rounded-xl gap-4 py-4 px-4 bg-[#FFFFFF]">

            <div className="absolute top-5 right-5 text-xs font-semibold p-2 border-1
                bg-white/60 border-black/20 rounded-sm text-black-text shadow-xs">
                {
                    card.translatedLanguages
                        // .map(lang => lang.toLowerCase())
                        .join(" / ")
                }
            </div>

            {/* IMAGE */}
            <div className="overflow-clip rounded-md rounded-br-4xl rounded-tl-4xl">
                <img src={card.imageUrl} alt={card.title}
                     className="w-full h-[180px] object-cover"/>
            </div>

            {/* TITLE & TOPICS */}
            <div className="flex flex-col gap-2 items-start text-left justify-between h-full">
                <h3 className="font-semibold text-xl">{card.titleShort}</h3>

                <p className="text-black/60">{(() => {
                    const MAX_CHARS = 80;
                    const fullStr = (card.topicsCovered ?? [])
                        .map(item =>
                            fromEnumFormat(item.toLowerCase())
                                .replace(/\b\w/g, c => c.toUpperCase())
                        )
                        .join(" • ");
                    if (fullStr.length > MAX_CHARS)
                        return fullStr.slice(0, 80).trim() + "...";
                    return fullStr;
                })()}</p>
            </div>

            {/* INFO */}
            <div className="flex flex-wrap gap-2 whitespace-nowrap">
                {
                    card.averageRating > 0 ? (
                        <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                            <StarFilled className="w-4 h-4 text-gold"/> {card.averageRating}
                        </div>
                    ) : (
                        <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                            <Sparkle className="w-4 h-4 text-gold"/> {t("new")}
                        </div>
                    )
                }
                <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                    {(card.durationMinutes / 60).toFixed(1)} {t("hours")}
                </div>
                <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                    {card.courseContentCount} {t("modules")}
                </div>
                <div className="flex items-center gap-1 px-2 border-1 border-black/20 rounded-sm text-black/60">
                    {t(`difficulty.${card.difficulty.toLowerCase()}`)}
                </div>
            </div>

            {/* BUTTON & PRICE */}
            <div className="flex justify-between items-center mt-0">
                <p className={`font-bold text-black/80 text-lg ${card.price == 0 && "font-normal"}`}>
                    {card.price > 0 ? `$${card.price}` : t("free")}
                </p>

                <div className="flex items-center gap-2">
                    <button
                        className="cursor-pointer"
                        onClick={handleToggleFavoriteCourse}
                        onMouseEnter={() => setIsHoveredHeart(true)}
                        onMouseLeave={() => setIsHoveredHeart(false)}
                    >
                        {(user?.favoriteCourseIds ?? []).includes(card.id)
                            ? <HeartFill className="w-6 h-auto text-red"/>
                            : !isHoveredHeart
                                ? <HeartOutline strokeWidth={2} className="w-6 h-auto text-black/60"/>
                                : <HeartFill className="w-6 h-auto text-red"/>}
                    </button>
                    <LocalizedLink
                        to={"/courses/" + `${card.id}/` + card.urlSlug}
                        style={isHoveredButton ?
                            {boxShadow: `0 4px 6px -1px ${shadowColor},  0 2px 4px -2px ${shadowColor}`} :
                            {}
                        }
                        className={`transition-all duration-200 ease-in-out cursor-pointer
                        px-8 py-1 ${bgColor} text-white rounded-md border-3 border-white/40`}
                        onMouseEnter={() => setIsHoveredButton(true)}
                        onMouseLeave={() => setIsHoveredButton(false)}
                    >
                        {t("explore")}
                    </LocalizedLink>
                </div>
            </div>
        </article>
    );
}

export default CourseCard;
